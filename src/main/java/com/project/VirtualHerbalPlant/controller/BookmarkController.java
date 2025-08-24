package com.project.VirtualHerbalPlant.controller;
import com.project.VirtualHerbalPlant.dto.Bookmarkdto;
import com.project.VirtualHerbalPlant.entity.Bookmark;
import com.project.VirtualHerbalPlant.service.BookmarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@PreAuthorize("hasRole('USER')")
@RestController
@Tag(name = "Bookmark Api", description = "Endpoints for Bookmarks")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/bookmarks")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

  @Operation(summary = "To add the bookmark")
    @PostMapping
    public ResponseEntity<Bookmarkdto> addBookmark(@PathVariable("plantId")String pId,@PathVariable("userId") String uId) {
       ObjectId plantId=new ObjectId(pId);
        ObjectId userId=new ObjectId(uId);
        Bookmark bookmark=new Bookmark();
        bookmark.setUserId(userId);
        bookmark.setPlantId(plantId);
        Bookmarkdto bookmarkdto = bookmarkService.addBookmark(bookmark);

        return ResponseEntity.ok(bookmarkdto);
    }

    @Operation(summary = "To get the bookmark")
    @GetMapping("/{id}")
    public ResponseEntity<Bookmarkdto> getBookmark(@PathVariable String id) {
        return bookmarkService.getBookmarkById(new ObjectId(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @Operation(summary = "To get the bookmark by user")
    @GetMapping("/user/{userId}")
    public List<Bookmarkdto> getBookmarksByUser(@PathVariable String userId) {
        return bookmarkService.getBookmarksByUserId(new ObjectId(userId));
    }
    @Operation(summary = "To delete the bookmark")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable String id) {
        bookmarkService.deleteBookmark(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
}

