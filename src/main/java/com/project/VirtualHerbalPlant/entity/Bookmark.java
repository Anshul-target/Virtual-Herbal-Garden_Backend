package com.project.VirtualHerbalPlant.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookmarks")
public class Bookmark {

    @Id
    private ObjectId id;
    private ObjectId userId;  // Reference to User
    private ObjectId plantId; // Reference to MedicinalPlant
}
