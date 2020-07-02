package com.zzy.resourcemanagementsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Link extends Resource {
    @Column(nullable = false)
    private String url;

    public Link() {
    }

    public Link(String name, LocalDateTime createTime, LocalDateTime updateTime, boolean starred, Long parentId, String url) {
        super(name, createTime, updateTime, starred, parentId);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
