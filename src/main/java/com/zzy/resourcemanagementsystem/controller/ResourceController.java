package com.zzy.resourcemanagementsystem.controller;

import com.zzy.resourcemanagementsystem.model.Resource;
import com.zzy.resourcemanagementsystem.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/starred")
    public ResponseEntity<Resource> getStarredResources() {
        return ResponseEntity.ok(resourceService.getStarredResource());
    }
}
