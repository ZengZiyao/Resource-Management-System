package com.zzy.resourcemanagementsystem.service;

import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.exception.ResourceStorageException;
import com.zzy.resourcemanagementsystem.model.File;
import com.zzy.resourcemanagementsystem.repository.FileRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public boolean hasFile(Long fileId) {
        return fileRepository.existsById(fileId);
    }

    public boolean isDuplicateFileName(Long parentId, String fileName) {
        return fileRepository.exists(Example.of(new File(fileName, parentId)));
    }


    public File getFile(Long fileId) {
        return fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId));
    }

    public List<File> getFilesByParentId(Long parentId) {
        return fileRepository.findByParentId(parentId);
    }

    public File storeFile(MultipartFile multipartFile, Long parentId) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        try {
            File file = new File(fileName, LocalDateTime.now(), LocalDateTime.now(), false, parentId, multipartFile.getContentType(), multipartFile.getBytes());
            return fileRepository.save(file);

        } catch (IOException e) {
            throw new ResourceStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public boolean updateFileName(Long fileId, String filename) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId));
        file.setName(filename);
        file.setUpdateTime(LocalDateTime.now());
        fileRepository.save(file);
        return true;
    }

    public boolean starFile(Long fileId, boolean star) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId));
        file.setStarred(star);
        file.setUpdateTime(LocalDateTime.now());
        fileRepository.save(file);
        return true;
    }

    public boolean moveFile(Long fileId, Long newParentId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new ResourceNotFoundException("File not found with id " + fileId));
        file.setParentId(newParentId);
        file.setUpdateTime(LocalDateTime.now());
        fileRepository.save(file);
        return true;
    }

    public boolean deleteFile(Long fileId) {
        fileRepository.deleteById(fileId);
        return true;
    }

    public List<File> getStarredFiles() {
        return fileRepository.findAll(Example.of(new File(true
        )));
    }

}
