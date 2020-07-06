package com.zzy.resourcemanagementsystem.controller;

import com.zzy.resourcemanagementsystem.exception.DuplicateNameException;
import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.model.Folder;
import com.zzy.resourcemanagementsystem.model.Resource;
import com.zzy.resourcemanagementsystem.service.FolderService;
import com.zzy.resourcemanagementsystem.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/resources/folders")
public class FolderController {

    private final FolderService folderService;
    private final ResourceService resourceService;

    public FolderController(FolderService folderService, ResourceService resourceService) {
        this.folderService = folderService;
        this.resourceService = resourceService;
    }


    @PostMapping("")
    public ResponseEntity<Folder> createFolder(@RequestParam(value = "folderName") String folderName, @RequestParam(value = "parentId") Long parentId) {
        if (!folderService.hasFolder(parentId)) {
            if (parentId == Long.valueOf(1)) {
                folderService.createFolder(null, "root");
            } else {
                throw new ResourceNotFoundException("Parent folder does not exist");

            }
        }

        Folder folder = folderService.createFolder(parentId, folderName);

        return new ResponseEntity(folder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<String> deleteFolder(@PathVariable Long folderId) {
        if (folderService.hasFolder(folderId)) {
            folderService.deleteFolder(folderId);
            return ResponseEntity.ok("Folder deleted");
        }

        throw new ResourceNotFoundException("Folder not found with id " + folderId);
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<Resource> getFolderContent(@PathVariable Long folderId) {
        if (folderService.hasFolder(folderId)) {
            return ResponseEntity.ok().body(resourceService.getResourceByFolderId(folderId));
        }
        throw new ResourceNotFoundException("Folder not found with id " + folderId);
    }

    @PatchMapping("/{folderId}/rename")
    public ResponseEntity<String> renameFolder(@PathVariable Long folderId, @RequestParam(value = "folderName") String folderName) {
        if (folderService.isDuplicateFolderName(folderId, folderName)) {
            throw new DuplicateNameException("Duplicate folder name");
        }
        folderService.updateFolderName(folderId, folderName);

        return ResponseEntity.ok("folder renamed");
    }

    @PatchMapping("/{folderId}/star")
    public ResponseEntity<String> starFolder(@PathVariable Long folderId, @RequestParam(value = "star") boolean star) {
        if (folderService.hasFolder(folderId)) {
            folderService.starFolder(folderId, star);
            return ResponseEntity.ok(star ? "folder starred" : "folder unstarred");
        }
        throw new ResourceNotFoundException("Folder not found with id " + folderId);

    }

    @PatchMapping("/{folderId}/move")
    public ResponseEntity<String> moveFolder(@PathVariable Long folderId, @RequestParam(value = "newParentId") Long newParentId) {
        if (!folderService.hasFolder(folderId)) {
            throw new ResourceNotFoundException("Folder to be moved does not exist");
        }

        if (!folderService.hasFolder(newParentId)) {
            throw new ResourceNotFoundException("The new location does not exist");
        }

        if (folderService.isDuplicateFolderName(newParentId, folderService.getFolderById(folderId).getName())) {
            throw new DuplicateNameException("Duplicate folder name");
        }

        folderService.moveFolder(folderId, newParentId);
        return ResponseEntity.ok("Folder moved");

    }


}
