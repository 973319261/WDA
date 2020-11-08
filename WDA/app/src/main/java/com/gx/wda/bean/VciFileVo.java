package com.gx.wda.bean;

public class VciFileVo {


    private String fileName;//文件名称
    private int fileNum;//文件数量
    private int fileSize;//文件大小
    public boolean isCheck() {
        return isCheck;
    }
    public void setCheck(boolean check) {
        isCheck = check;
    }
    private boolean isCheck;
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public int getFileNum() {
        return fileNum;
    }
    public void setFileNum(int fileNum) {
        this.fileNum = fileNum;
    }
    public int getFileSize() {
        return fileSize;
    }
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }
}
