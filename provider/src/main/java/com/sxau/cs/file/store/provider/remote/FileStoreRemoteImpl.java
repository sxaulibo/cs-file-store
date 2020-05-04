package com.sxau.cs.file.store.provider.remote;

import com.sxau.cs.file.store.api.common.model.request.FileDownloadRequest;
import com.sxau.cs.file.store.api.common.model.request.FileUploadRequest;
import com.sxau.cs.file.store.api.common.model.response.FileDownloadResponse;
import com.sxau.cs.file.store.api.common.model.response.FileUploadResponse;
import com.sxau.cs.file.store.api.remote.FileStoreRemote;
import com.sxau.cs.file.store.biz.FileStoreBiz;

import javax.annotation.Resource;

public class FileStoreRemoteImpl implements FileStoreRemote {

    @Resource
    private FileStoreBiz fileStoreBiz;

    @Override
    public FileUploadResponse upload(FileUploadRequest fileUploadRequest) {
        return fileStoreBiz.upload(fileUploadRequest);
    }

    @Override
    public FileDownloadResponse download(FileDownloadRequest fileDownloadRequest) {
        return fileStoreBiz.download(fileDownloadRequest);
    }
}
