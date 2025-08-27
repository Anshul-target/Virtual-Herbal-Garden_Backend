package com.project.VirtualHerbalPlant.controller;
import com.project.VirtualHerbalPlant.dto.NoteReplydto;
import com.project.VirtualHerbalPlant.dto.Notedto;
import com.project.VirtualHerbalPlant.entity.Note;
import com.project.VirtualHerbalPlant.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/notes")
@Tag(name = "Note Api", description = "Endpoints for making Note")
@SecurityRequirement(name = "bearerAuth")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService)
    {
        this.noteService = noteService;
    }
    @Operation(summary = "To create the note")
    @PostMapping(value = "{plantId}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteReplydto> createNote(
            @PathVariable("plantId")String plantId,
            @RequestBody Notedto note
    ) {
        NoteReplydto note1 = noteService.createNote(note,plantId);
        return ResponseEntity.ok(note1);
    }
    @Operation(summary = "Get  note by id")
    @GetMapping("/{id}")
    public ResponseEntity<NoteReplydto> getNote(@PathVariable("id") String id) {
        Optional<NoteReplydto> noteById = noteService.getNoteById(new ObjectId(id));
        return ResponseEntity.ok(noteById.get());
    }
    @Operation(summary = "Get note by userId")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getNotesByUser(@PathVariable("userId") String userId) {
        List<NoteReplydto> notesByUserId = noteService.getNotesByUserId(new ObjectId(userId));
   return ResponseEntity.ok(notesByUserId);
    }
    @Operation(summary = "To update the note")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateNote(@PathVariable("id") String id, @RequestBody Notedto content) {
      if (content.getContent().isEmpty())
          return ResponseEntity.badRequest().body("Provide the content");
        NoteReplydto updated = noteService.updateNote(new ObjectId(id), content.getContent());
        if (updated != null) return ResponseEntity.ok(updated);
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "To delete the note")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNote(new ObjectId(id));
        return ResponseEntity.noContent().build();
    }
}

