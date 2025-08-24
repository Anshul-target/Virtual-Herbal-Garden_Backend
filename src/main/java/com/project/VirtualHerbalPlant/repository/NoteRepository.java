package com.project.VirtualHerbalPlant.repository;

import com.project.VirtualHerbalPlant.entity.Note;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, ObjectId> {
    List<Note> findByUserId(ObjectId userId);
}
