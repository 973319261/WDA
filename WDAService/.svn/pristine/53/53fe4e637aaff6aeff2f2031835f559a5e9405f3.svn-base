package com.gx.po;

import java.io.Serializable;

/**
 * file_type
 * @author 
 */
public class FileType implements Serializable {
    /**
     * 文件类型ID
     */
    private Integer fileTypeId;

    /**
     * 文件类型名称
     */
    private String fileTypeName;
    

    private static final long serialVersionUID = 1L;

    public Integer getFileTypeId() {
        return fileTypeId;
    }

    public void setFileTypeId(Integer fileTypeId) {
        this.fileTypeId = fileTypeId;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        FileType other = (FileType) that;
        return (this.getFileTypeId() == null ? other.getFileTypeId() == null : this.getFileTypeId().equals(other.getFileTypeId()))
            && (this.getFileTypeName() == null ? other.getFileTypeName() == null : this.getFileTypeName().equals(other.getFileTypeName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFileTypeId() == null) ? 0 : getFileTypeId().hashCode());
        result = prime * result + ((getFileTypeName() == null) ? 0 : getFileTypeName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fileTypeId=").append(fileTypeId);
        sb.append(", fileTypeName=").append(fileTypeName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}