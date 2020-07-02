package com.zzy.resourcemanagementsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@Entity
public class File extends Resource {
    @Column(nullable = false)
    private String fileType;
    @Column(nullable = false)
    @Lob
    private byte[] content;

    public File() {
    }

    public File(String name, LocalDateTime createTime, LocalDateTime updateTime, boolean starred, Long parentId, String fileType, byte[] content) {
        super(name, createTime, updateTime, starred, parentId);
        this.fileType = fileType;
        this.content = content;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
