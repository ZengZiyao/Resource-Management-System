package com.zzy.resourcemanagementsystem.model;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Folder extends Resource {

    public Folder() {
    }

    public Folder(String name, LocalDateTime createTime, LocalDateTime updateTime, boolean starred, Long parentId) {
        super(name, createTime, updateTime, starred, parentId);
    }
}
