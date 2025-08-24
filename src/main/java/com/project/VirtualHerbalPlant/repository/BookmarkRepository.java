package com.project.VirtualHerbalPlant.repository;

import com.project.VirtualHerbalPlant.entity.Bookmark;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookmarkRepository extends MongoRepository<Bookmark, ObjectId> {
    List<Bookmark> findByUserId(ObjectId userId);
    List<Bookmark> findByPlantId(ObjectId plantId);
    Bookmark findByUserIdAndPlantId(ObjectId userId, ObjectId plantId);
}

