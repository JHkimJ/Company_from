package com.mysite.sbb.bookmark;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
	List<Bookmark> findByTitle(String title);
}
