package com.zzy.resourcemanagementsystem.service;

import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.model.Link;
import com.zzy.resourcemanagementsystem.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class LinkService {

    @Autowired
    private LinkRepository linkRepository;

    public Link getLinkById(Long linkId) {
        return linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));

    }

    public List<Link> getLinksByParentId(Long parentId) {
        return linkRepository.findByParentId(parentId);
    }

    public Link uploadLink(Long parentId, String linkName, String url) {
        Link link = new Link(linkName, LocalDateTime.now(), LocalDateTime.now(), false, parentId, url);
        return linkRepository.save(link);
    }

    public boolean updateLinkName(Long linkId, String newLinkName) {
        Link link = linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));
        link.setName(newLinkName);
        link.setUpdateTime();
        linkRepository.save(link);
        return true;
    }

    public boolean starLink(Long linkId, boolean star) {
        Link link = linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));
        link.setStarred(star);
        link.setUpdateTime();
        linkRepository.save(link);
        return true;
    }

    public boolean moveLink(Long linkId, Long newParentId) {
        Link link = linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));
        link.setParentId(newParentId);
        link.setUpdateTime();
        linkRepository.save(link);
        return true;
    }

    public boolean updateUrl(Long linkId, String newUrl) {
        Link link = linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));
        link.setUrl(newUrl);
        link.setUpdateTime();
        linkRepository.save(link);
        return true;
    }

    public boolean deleteLink(Long linkId) {
        linkRepository.deleteById(linkId);
        return true;
    }
}
