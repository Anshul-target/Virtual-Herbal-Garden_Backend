package com.project.VirtualHerbalPlant.entity;
import com.project.VirtualHerbalPlant.dto.NoteReplydto;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "notes")
public class Note {

    @Id
    private ObjectId id;

    private ObjectId userId;   // Reference to User
    private ObjectId plantId;  // Reference to MedicinalPlant

    private String content;    // User's custom notes
    private String createdAt;
    private String updatedAt;


}
