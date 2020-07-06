package com.zzy.resourcemanagementsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Resource {
    private List<Folder> folders;
    private List<File> files;
    private List<Link> links;

    public Resource(List<Folder> folders, List<File> files, List<Link> links) {
        this.folders = folders;
        this.files = files;
        this.links = links;
    }

}
