package com.sxau.homework.provider.model;

import com.sxau.homework.service.FileService;
import com.sxau.homework.service.bean.FileInfo;
import com.sxau.homework.service.impl.FileServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class FileController {
    //文件存储的总路径
    private final static String BENEATH = "C:\\uploaded\\";

    /**
     * 上传数据及保存文件
     *
     * @param file 上传文件
     * @throws IOException 可能出现指针异常
     */
    @RequestMapping("/Upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        //保存到目标位置
        String path = BENEATH + file.getOriginalFilename();
        System.out.println(path);

        File file1 = new File(path);
        if (!file1.getParentFile().exists()) {
            if(!file1.mkdirs()){
                System.out.println("文件夹已存在");
            }
            System.out.println("文件夹创建");
        }
        file.transferTo(file1);
        //存文件对象 to 数据库
        FileInfo fileInfo = new FileInfo();
        String fileCode = String.valueOf(System.currentTimeMillis()) + '_' + file.getOriginalFilename();
        System.out.println(fileCode);
        fileInfo.setFileCode(fileCode);
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setPath(path);
        FileService fileService = new FileServiceImpl();
//        if(fileService.insert(fileInfo)){
//            throw new RuntimeException()
//        }
        return fileService.insert(fileInfo) ? "上传成功" : "上传失败";
    }

    /**
     * 下载文件
     *
     * @param fileCode 文件code
     * @throws IOException 可能出现指针异常
     */
    @RequestMapping("/Download")
    public void download(@RequestParam("fileCode") String fileCode) throws IOException {
        FileService fileService = new FileServiceImpl();
        FileInfo fileInfoBean = fileService.queryInfoByFileCode(fileCode);
        System.out.println(fileInfoBean.getPath());
        if (fileInfoBean.getPath().equals("")) {
            throw new RuntimeException("文件找不到");
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = requestAttributes != null ? requestAttributes.getResponse() : null;
        Objects.requireNonNull(response).setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileInfoBean.getFileName().getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
        //把目录下存在的文件转换成流
        Writer writer = response.getWriter();
        MultipartFile multipartFile=null;
        try (InputStreamReader inputStreamReader = new FileReader(new File(fileInfoBean.getPath()))) {

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                writer.append(temp);
                writer.append('\n');
                writer.flush();
            }
            writer.close();
        }
    }

    /**
     * 删除文件
     *
     * @param fileCode 文件code
     * @return true-删除成功
     */
    @RequestMapping("/Delete")
    public String delete(@RequestParam("fileCode") String fileCode) {
        FileService fileService = new FileServiceImpl();
        FileInfo fileInfoBean = fileService.queryInfoByFileCode(fileCode);
        if ("".equals(fileInfoBean.getPath())) {
            throw new RuntimeException("文件删除成功");
        }
        File file = new File(fileInfoBean.getPath());
        if(file.delete()){
            System.out.println("删除成功");
        }
        fileService.deleteByFileCode(fileCode);
        return "删除成功";
    }

}