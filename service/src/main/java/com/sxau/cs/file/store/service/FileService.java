package com.sxau.cs.file.store.service;

import com.sxau.cs.file.store.service.beans.FileInfo;

/**
 * 文件信息服务
 */
public interface FileService {

    /**
     * 增
     *
     * @param fileInfo 文件信息
     * @return true-插入成功
     */
    boolean insert(FileInfo fileInfo);

    /**
     * 查
     *
     * @param fileCode 文件标识Code
     * @return 文件信息对象（查询场景为查出的FileInfo List（该场景下为1条）中的第一个对象，出现多条视为脏数据，在别的地方进行定期处理）
     */
    FileInfo queryInfoByFileCode(String fileCode);

    /**
     * 删
     *
     * @param fileCode 文件标识Code
     */
    void deleteByFileCode(String fileCode);

}


