package com.zzy.resourcemanagementsystem.controller;

import com.zzy.resourcemanagementsystem.exception.DuplicateNameException;
import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.model.Link;
import com.zzy.resourcemanagementsystem.service.FolderService;
import com.zzy.resourcemanagementsystem.service.LinkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources/links")
public class LinkController {

    private final LinkService linkService;
    private final FolderService folderService;

    public LinkController(LinkService linkService, FolderService folderService) {
        this.linkService = linkService;
        this.folderService = folderService;
    }

    @PostMapping("")
    public ResponseEntity<String> postLink(@RequestParam(value = "url") String url, @RequestParam(value = "linkName") String linkName, @RequestParam(value = "parentId") Long parentId) {
        if (!folderService.hasFolder(parentId)) {
            if (parentId == Long.valueOf(1)) {
                folderService.createFolder(null, "root");
            } else {
                throw new ResourceNotFoundException("Folder not found with id " + parentId);
            }
        }

        Link link = linkService.uploadLink(parentId, linkName, url);
        return new ResponseEntity("Link created with id " + link.getId(), HttpStatus.CREATED);
    }

    @DeleteMapping("/{linkId}")
    public ResponseEntity<String> deleteLink(@PathVariable Long linkId) {
        if (!linkService.hasLink(linkId)) {
            throw new ResourceNotFoundException("Link not found with id " + linkId);
        }

        linkService.deleteLink(linkId);
        return ResponseEntity.ok("Link deleted");
    }

    @GetMapping("/{linkId}")
    public ResponseEntity<Link> getLink(@PathVariable Long linkId) {
        if (!linkService.hasLink(linkId)) {
            throw new ResourceNotFoundException("Link not found with id " + linkId);
        }

        Link link = linkService.getLinkById(linkId);
        return ResponseEntity.ok(link);
    }

    @PatchMapping("/{linkId}/rename")
    public ResponseEntity<String> renameLink(@PathVariable Long linkId, @RequestParam(value = "newLinkName") String newLinkName) {
        if (!linkService.hasLink(linkId)) {
            throw new ResourceNotFoundException("Link not found with id " + linkId);
        }

        if (linkService.isDuplicateLinkName(linkService.getLinkById(linkId).getParentId(), newLinkName)) {
            throw new DuplicateNameException("Link name exists");
        }

        linkService.updateLinkName(linkId, newLinkName);

        return ResponseEntity.ok("Link renamed");
    }

    @PatchMapping("/{linkId}/star")
    public ResponseEntity<String> starLink(@PathVariable Long linkId, @RequestParam(value = "star") boolean star) {
        if (!linkService.hasLink(linkId)) {
            throw new ResourceNotFoundException("Link not found with id " + linkId);
        }

        linkService.starLink(linkId, star);

        return ResponseEntity.ok(star ? "Link starred" : "Link unstarred");

    }

    @PatchMapping("/{linkId}/move")
    public ResponseEntity<String> moveLink(@PathVariable Long linkId, @RequestParam(value = "newParentId") Long newParentId) {
        if (!linkService.hasLink(linkId)) {
            throw new ResourceNotFoundException("Link not found with id " + linkId);
        }

        if (!folderService.hasFolder(newParentId)) {
            throw new ResourceNotFoundException("Folder not found with id " + newParentId);
        }

        if (linkService.isDuplicateLinkName(newParentId, linkService.getLinkById(linkId).getName())) {
            throw new DuplicateNameException("Link name exists");

        }

        linkService.moveLink(linkId, newParentId);
        return ResponseEntity.ok("Link moved");
    }

    @PatchMapping("/{linkId}/edit")
    public ResponseEntity<String> editUrl(@PathVariable Long linkId, @RequestParam(value = "newUrl") String newUrl) {
        if (!linkService.hasLink(linkId)) {
            throw new ResourceNotFoundException("Link not found with id " + linkId);
        }

        linkService.updateUrl(linkId, newUrl);
        return ResponseEntity.ok("Link updated");
    }


}
