package com.zzy.resourcemanagementsystem.controller;

import com.zzy.resourcemanagementsystem.exception.DuplicateNameException;
import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.model.File;
import com.zzy.resourcemanagementsystem.service.FileService;
import com.zzy.resourcemanagementsystem.service.FolderService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resources/files")
public class FileController {

    private final FileService fileService;
    private final FolderService folderService;

    public FileController(FileService fileService, FolderService folderService) {
        this.fileService = fileService;
        this.folderService = folderService;
    }

    @PostMapping("")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "parentId") Long parentId, @RequestParam(value = "multiPartFile") MultipartFile multipartFile) {

        if (!folderService.hasFolder(parentId)) {
            if (parentId == Long.valueOf(1)) {
                folderService.createFolder(null, "root");
            } else {
                throw new ResourceNotFoundException("Folder not found with id " + parentId);

            }
        }
        File file = fileService.storeFile(multipartFile, parentId);

        return new ResponseEntity("File uploaded with fileId " + file.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable Long fileId) {
        if (!fileService.hasFile(fileId)) {
            throw new ResourceNotFoundException("File not found with id " + fileId);
        }

        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .body(new ByteArrayResource(file.getContent()));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId) {

        if (!fileService.hasFile(fileId)) {
            throw new ResourceNotFoundException("File not found with id " + fileId);
        }

        fileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted");
    }

    @PatchMapping("/{fileId}/rename")
    public ResponseEntity<String> renameFile(@PathVariable Long fileId, @RequestParam(value = "newFileName") String newFileName) {
        if (!fileService.hasFile(fileId)) {
            throw new ResourceNotFoundException("File not found with id " + fileId);
        }

        if (fileService.isDuplicateFileName(fileService.getFile(fileId).getParentId(), newFileName)) {
            throw new DuplicateNameException("File name exists");
        }

        fileService.updateFileName(fileId, newFileName);
        return ResponseEntity.ok("File name updated");
    }

    @PatchMapping("/{fileId}/move")
    public ResponseEntity<String> moveFile(@PathVariable Long fileId, @RequestParam(value = "newParentId") Long newParentId) {
        if (!fileService.hasFile(fileId)) {
            throw new ResourceNotFoundException("File not found with id " + fileId);
        }

        if (!folderService.hasFolder(newParentId)) {
            throw new ResourceNotFoundException("Folder not found with id " + newParentId);
        }

        if (fileService.isDuplicateFileName(newParentId, fileService.getFile(fileId).getName())) {
            throw new DuplicateNameException("File name exists");
        }

        fileService.moveFile(fileId, newParentId);
        return ResponseEntity.ok("File moved");
    }

    @PatchMapping("/{fileId}/star")
    public ResponseEntity<String> starFile(@PathVariable Long fileId, @RequestParam(value = "star") boolean star) {
        if (!fileService.hasFile(fileId)) {
            throw new ResourceNotFoundException("File not found with id " + fileId);
        }

        fileService.starFile(fileId, star);
        return ResponseEntity.ok(star ? "File starred" : "File unstarred");
    }

}
