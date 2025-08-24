package com.project.VirtualHerbalPlant.service;

import com.project.VirtualHerbalPlant.dto.Bookmarkdto;
import com.project.VirtualHerbalPlant.entity.Bookmark;
import com.project.VirtualHerbalPlant.repository.BookmarkRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public Bookmarkdto addBookmark(Bookmark bookmark) {

        Bookmark save = bookmarkRepository.save(bookmark);
        return Bookmarkdto.fromEntity(save);
    }
@Operation(summary = "To get bookmark by id")
    public Optional<Bookmarkdto> getBookmarkById(ObjectId id) {
        return bookmarkRepository.findById(id).map(Bookmarkdto::fromEntity);
    }
    @Operation(summary = "")
    public List<Bookmarkdto> getBookmarksByUserId(ObjectId userId) {
        return bookmarkRepository.findByUserId(userId).stream().map(Bookmarkdto::fromEntity).toList();
    }
    @Operation(summary = "To delete the bookmark")
    public void deleteBookmark(ObjectId id) {
        bookmarkRepository.deleteById(id);
    }
}

