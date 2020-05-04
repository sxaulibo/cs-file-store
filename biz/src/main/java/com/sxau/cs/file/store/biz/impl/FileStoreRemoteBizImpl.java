package com.sxau.cs.file.store.biz.impl;

import com.sxau.cs.file.store.api.common.model.request.FileDownloadRequest;
import com.sxau.cs.file.store.api.common.model.request.FileUploadRequest;
import com.sxau.cs.file.store.api.common.model.response.FileDownloadResponse;
import com.sxau.cs.file.store.api.common.model.response.FileUploadResponse;
import com.sxau.cs.file.store.biz.FileStoreRemoteBiz;
import com.sxau.cs.file.store.service.FileService;
import com.sxau.cs.file.store.service.beans.FileInfo;
import com.sxau.cs.file.store.service.impl.FileServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStoreRemoteBizImpl implements FileStoreRemoteBiz {
    //文件存储的总路径
    private final static String BENEATH = "C:\\uploaded\\";

    @Override
    public FileUploadResponse upload(FileUploadRequest fileUploadRequest) {
        FileUploadResponse fileUploadResponse = new FileUploadResponse() ();
        //保存到目标位置
        MultipartFile file = fileUploadRequest.getFile();
        String path = BENEATH + file.getOriginalFilename();
        System.out.println(path);

        File file1 = new File(path);
        if (!file1.getParentFile().exists()) {
            if (!file1.mkdirs()) {
                System.out.println("文件夹已存在");
            }
            System.out.println("文件夹创建");
        }
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(file1);
            fileOutputStream.write(fileUploadRequest.getFileByteArray());
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            file.transferTo(file1);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //存文件对象 to 数据库
        FileInfo fileInfo = new FileInfo();
        String fileCode = String.valueOf(System.currentTimeMillis()) + '_' + file.getOriginalFilename();
        System.out.println(fileCode);
        fileInfo.setFileCode(fileCode);
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setPath(path);
        FileService fileService = new FileServiceImpl();
        fileService.insert(fileInfo);
        if (fileService.insert(fileInfo)) {
            throw new RuntimeException();
        }
        fileUploadResponse.setFileCode(fileCode);
        return fileUploadResponse;
    }

    @Override
    public FileDownloadResponse download(FileDownloadRequest fileDownloadRequest) {
        return null;
    }
}
