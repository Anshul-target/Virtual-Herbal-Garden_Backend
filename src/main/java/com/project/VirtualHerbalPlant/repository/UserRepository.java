package com.project.VirtualHerbalPlant.repository;

//import com.project.VirtualHerbalPlant.entity.User;
import com.project.VirtualHerbalPlant.entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, ObjectId> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);
}