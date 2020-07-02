package com.zzy.resourcemanagementsystem.repository;

import com.zzy.resourcemanagementsystem.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findByParentId(Long parentId);

}
