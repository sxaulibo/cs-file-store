package com.sxau.homework.service.impl;

import com.sxau.homework.service.FileService;
import com.sxau.homework.service.bean.FileInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileServiceImpl implements FileService {
    @Override
    public boolean insert(FileInfo fileInfo) {
        try {
            SqlSession sqlSession = getSqlSession();
            int result = sqlSession.insert("UserMapper.insertFile", fileInfo);
            sqlSession.commit();
            return result != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public FileInfo queryInfoByFileCode(String fileCode1) {

        FileInfo fileInfo1 =new FileInfo();
        SqlSession sqlSession = null;
        try {
            sqlSession = getSqlSession();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
        }
        if (sqlSession != null) {
            List<FileInfo> fileInfo = sqlSession.selectList("UserMapper.queryByFileCode", fileCode1);
            sqlSession.commit();
            fileInfo1=fileInfo.get(0);
            if(fileInfo.size()>1){
                throw new RuntimeException("查询出多条数据");
            }
        }
        return fileInfo1;
    }

    @Override
    public void deleteByFileCode(String fileCode2) {
        try {
            SqlSession sqlSession = getSqlSession();
            sqlSession.update("UserMapper.deleteByFileCode", fileCode2);
            sqlSession.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private SqlSession getSqlSession() throws IOException {
        //指定mybatis全局配置文件
        String resource = "mybatis-config.xml";
        //读取全局配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //构建SqlSessionFactory对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return sqlSessionFactory.openSession(false);
    }
}
