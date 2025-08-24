package com.project.VirtualHerbalPlant.service;

import com.project.VirtualHerbalPlant.config.UserPrincipal;
import com.project.VirtualHerbalPlant.dto.NoteReplydto;
import com.project.VirtualHerbalPlant.dto.Notedto;
import com.project.VirtualHerbalPlant.entity.Note;
import com.project.VirtualHerbalPlant.repository.NoteRepository;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;


    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public NoteReplydto createNote(Notedto note,String Id) {
        ObjectId plantId=new ObjectId(Id);
        UserPrincipal principal =(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String Id1=principal.getId();
        ObjectId userId=new ObjectId(Id1);
        Note notes = Note.builder().content(note.getContent())
                .createdAt(LocalDateTime.now().toString())
                .updatedAt(LocalDateTime.now().toString())
                .plantId(plantId)
                .userId(userId)
                .build();

        Note save = noteRepository.save(notes);
       return NoteReplydto.fromEntity(save);
    }

    public Optional<NoteReplydto> getNoteById(ObjectId id) {
        Optional<Note> byId = noteRepository.findById(id);
        return byId.map(NoteReplydto::fromEntity);
    }

    public List<NoteReplydto> getNotesByUserId(ObjectId userId) {
        List<Note> byUserId = noteRepository.findByUserId(userId);
        List<NoteReplydto> noteReplydtos = byUserId.stream().map(NoteReplydto::fromEntity).toList();
 return noteReplydtos;
    }

    public NoteReplydto updateNote(ObjectId id, String content) {
        Note note1 = noteRepository.findById(id)
                .map(note -> {
                    note.setContent(content);
                    note.setUpdatedAt(LocalDateTime.now().toString());
                    return noteRepository.save(note);
                }).orElse(null);
       return NoteReplydto.fromEntity(note1);

    }

    public void deleteNote(ObjectId id) {
        noteRepository.deleteById(id);
    }
}

