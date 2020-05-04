package com.sxau.cs.file.store.service.beans;

public class FileInfo implements java.io.Serializable {

    /**
     * fileCode 文件标识Code （System.currentTimeMillis()+‘_’+上传文件名称)
     */
    private int id;

    private String fileCode;

    private String fileName;

    private String path;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
