package com.zzy.resourcemanagementsystem.service;

import com.zzy.resourcemanagementsystem.model.Resource;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {

    private final FolderService folderService;
    private final FileService fileService;
    private final LinkService linkService;

    public ResourceService(FolderService folderService, FileService fileService, LinkService linkService) {
        this.folderService = folderService;
        this.fileService = fileService;
        this.linkService = linkService;
    }

    public Resource getResourceByFolderId(Long folderId) {
        return new Resource(folderService.getFoldersByParentId(folderId), fileService.getFilesByParentId(folderId), linkService.getLinksByParentId(folderId));
    }

    public Resource getStarredResource() {
        return new Resource(folderService.getStarredFolder(), fileService.getStarredFiles(), linkService.getStarredLinks());
    }
}
