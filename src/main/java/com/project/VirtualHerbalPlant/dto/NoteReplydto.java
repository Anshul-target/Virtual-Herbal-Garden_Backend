package com.project.VirtualHerbalPlant.dto;

import com.project.VirtualHerbalPlant.entity.Note;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
@Builder
@Data
public class NoteReplydto {
    private String id;

    private String  userId;   // Reference to User
    private String plantId;  // Reference to MedicinalPlant

    private String content;    // User's custom notes
    private String createdAt;
    private String updatedAt;
    public  static NoteReplydto fromEntity(Note note){
        NoteReplydto build = NoteReplydto.builder().id(note.getId().toHexString()).plantId(note.getPlantId().toHexString()).userId(note.getUserId().toHexString()).content(note.getContent()).createdAt(note.getCreatedAt()).updatedAt(note.getUpdatedAt()).build();
return build;
    }
}
