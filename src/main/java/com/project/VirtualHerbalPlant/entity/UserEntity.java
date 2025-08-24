package com.project.VirtualHerbalPlant.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {

    @Id
    private ObjectId id;
    private String name;

    private  String dob;
    private String address;

    private String username;
    private String role;
    private String email;
    private String password;  // (store encrypted, never plain text!)
private Map<String,String> profile;
private String createdAt;
    private List<ObjectId> bookmarkIds; // List of bookmarks (reference)
    private List<ObjectId> noteIds;     // List of notes (reference)
}
