package com.zzy.resourcemanagementsystem.service;

import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.model.Folder;
import com.zzy.resourcemanagementsystem.repository.FolderRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderService(FolderRepository folderRepository) {
        this.folderRepository = folderRepository;
    }

    public List<Folder> getFoldersByParentId(Long parentId) {
        return folderRepository.findByParentId(parentId);
    }

    public Folder getFolderById(Long folderId) {
        return folderRepository.findById(folderId).orElseThrow(() -> new ResourceNotFoundException("Folder not found with id " + folderId));
    }

    public boolean hasFolder(Long folderId) {
        return folderRepository.existsById(folderId);
    }

    public boolean isDuplicateFolderName(Long folderId, String folderName) {
        return folderRepository.exists(Example.of(new Folder(folderName, folderRepository.findById(folderId).orElseThrow().getParentId())));
    }

    public Folder createFolder(Long parentId, String folderName) {
        Folder folder = new Folder(folderName, LocalDateTime.now(), LocalDateTime.now(), false, parentId);
        folderRepository.save(folder);
        return folder;
    }

    public boolean updateFolderName(Long folderId, String newFolderName) {
        Folder folder = getFolderById(folderId);
        folder.setName(newFolderName);
        folder.setUpdateTime(LocalDateTime.now());
        folderRepository.save(folder);
        return true;
    }

    public boolean moveFolder(Long folderId, Long newParentId) {
        Folder folder = getFolderById(folderId);
        folder.setParentId(newParentId);
        folder.setUpdateTime(LocalDateTime.now());
        folderRepository.save(folder);
        return true;
    }

    public boolean starFolder(Long folderId, boolean star) {
        Folder folder = getFolderById(folderId);
        folder.setStarred(star);
        folder.setUpdateTime(LocalDateTime.now());
        folderRepository.save(folder);
        return true;
    }

    public boolean deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
        return true;
    }

    public List<Folder> getStarredFolder() {
        return folderRepository.findAll(Example.of(new Folder(true)));
    }
}
