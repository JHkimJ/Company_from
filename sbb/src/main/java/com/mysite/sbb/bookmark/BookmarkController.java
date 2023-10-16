package com.mysite.sbb.bookmark;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @Autowired
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @PostMapping
    public void addBookmark(@RequestBody Bookmark bookmarkDto) {
        bookmarkService.addBookmark(bookmarkDto);
    }

    @GetMapping
    public List<Bookmark> getAllBookmarks() {
        return bookmarkService.getAllbookmarks();
    }
}
