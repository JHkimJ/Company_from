package com.mysite.sbb.bookmark;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkService {
	
	private final BookmarkRepository bookmarkRepository;
	
	@Autowired
	public BookmarkService(BookmarkRepository bookmarkRepository) {
		this.bookmarkRepository = bookmarkRepository;
	}
	
	public void addBookmark(Bookmark bookmarkDto) {
		Bookmark bookmark = new Bookmark();
		
		bookmark.setName(bookmarkDto.getName());
		bookmark.setUrl(bookmarkDto.getUrl());
		
		bookmarkRepository.save(bookmark);
	}
	
	public List<Bookmark> getAllbookmarks(){
		List<Bookmark> bookmarks = bookmarkRepository.findAll();
		
		List<Bookmark> dtos = new ArrayList<>();
		
        for (Bookmark bookmark : bookmarks) {
            Bookmark book = new Bookmark();
            book.setName(bookmark.getName());
            book.setUrl(bookmark.getUrl());
            dtos.add(book);
        }
        
        return dtos;
    }
}
