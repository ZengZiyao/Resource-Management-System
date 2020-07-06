package com.zzy.resourcemanagementsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime createTime;
    @Column(nullable = false)
    private LocalDateTime updateTime;
    @Column()
    private boolean starred;
    @Column()
    private Long parentId;

    @Column(nullable = false)
    private String url;

    public Link(boolean starred) {
        this.starred = starred;
    }

    public Link(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;

    }

    public Link(String name, LocalDateTime createTime, LocalDateTime updateTime, boolean starred, Long parentId, String url) {
        this.name = name;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.starred = starred;
        this.parentId = parentId;
        this.url = url;
    }
}
