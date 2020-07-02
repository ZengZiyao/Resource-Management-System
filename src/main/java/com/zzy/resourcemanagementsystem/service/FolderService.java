package com.zzy.resourcemanagementsystem.service;

import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.model.Folder;
import com.zzy.resourcemanagementsystem.repository.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class FolderService {
    @Autowired
    private FolderRepository folderRepository;

    public List<Folder> getFoldersByParentId(Long parentId) {
        return folderRepository.findByParentId(parentId);
    }

    public Folder createFolder(Long parentId, String folderName) {
        Folder folder = new Folder(folderName, LocalDateTime.now(), LocalDateTime.now(), false, parentId);
        folderRepository.save(folder);
        return folder;
    }

    public boolean updateFolderName(Long folderId, String newFolderName) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ResourceNotFoundException("Folder not found with id " + folderId));
        folder.setName(newFolderName);
        folder.setUpdateTime();
        folderRepository.save(folder);
        return true;
    }

    public boolean moveFolder(Long folderId, Long newParentId) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ResourceNotFoundException("Folder not found with id " + folderId));
        folder.setParentId(newParentId);
        folder.setUpdateTime();
        folderRepository.save(folder);
        return true;
    }

    public boolean starFolder(Long folderId, boolean star) {
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> new ResourceNotFoundException("Folder not found with id " + folderId));
        folder.setStarred(star);
        folder.setUpdateTime();
        folderRepository.save(folder);
        return true;
    }

    public boolean deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
        return true;
    }
}
