package com.zzy.resourcemanagementsystem.service;

import com.zzy.resourcemanagementsystem.exception.ResourceNotFoundException;
import com.zzy.resourcemanagementsystem.model.Link;
import com.zzy.resourcemanagementsystem.repository.LinkRepository;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LinkService {

    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public boolean hasLink(Long linkId) {
        return linkRepository.existsById(linkId);
    }

    public boolean isDuplicateLinkName(Long parentId, String newLinkName) {
        return linkRepository.exists(Example.of(new Link(newLinkName, parentId)));
    }

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
        link.setUpdateTime(LocalDateTime.now());
        linkRepository.save(link);
        return true;
    }

    public boolean starLink(Long linkId, boolean star) {
        Link link = linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));
        link.setStarred(star);
        link.setUpdateTime(LocalDateTime.now());
        linkRepository.save(link);
        return true;
    }

    public boolean moveLink(Long linkId, Long newParentId) {
        Link link = linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));
        link.setParentId(newParentId);
        link.setUpdateTime(LocalDateTime.now());
        linkRepository.save(link);
        return true;
    }

    public boolean updateUrl(Long linkId, String newUrl) {
        Link link = linkRepository.findById(linkId).orElseThrow(() -> new ResourceNotFoundException("Link cannot find with id " + linkId));
        link.setUrl(newUrl);
        link.setUpdateTime(LocalDateTime.now());
        linkRepository.save(link);
        return true;
    }

    public boolean deleteLink(Long linkId) {
        linkRepository.deleteById(linkId);
        return true;
    }

    public List<Link> getStarredLinks() {
        return linkRepository.findAll(Example.of(new Link(true)));
    }
}
