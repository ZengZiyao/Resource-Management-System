package com.zzy.resourcemanagementsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime createTime;
    @Column(nullable = false)
    private LocalDateTime updateTime;
    @Column(nullable = false)
    private boolean starred;
    @Column()
    private Long parentId;

    public Resource() {
    }

    public Resource(String name, LocalDateTime createTime, LocalDateTime updateTime, boolean starred, Long parentId) {
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.starred = starred;
        this.parentId = parentId;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime() {
        this.updateTime = LocalDateTime.now();
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return String.format("Resource[id=%d, name=%s, createTime=%s, updateTime=%s, starred=%b, parentId=%d]", id, name, createTime.toString(), updateTime.toString(), starred, parentId);
    }
}
