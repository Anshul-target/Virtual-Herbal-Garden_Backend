package com.project.VirtualHerbalPlant.repository;

import com.project.VirtualHerbalPlant.entity.RefreshToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, ObjectId> {
    @Override
    Optional<RefreshToken> findById(ObjectId id);
    void deleteByUserId(Object userId);

    Optional<RefreshToken> findByToken(String refreshToken);


}
