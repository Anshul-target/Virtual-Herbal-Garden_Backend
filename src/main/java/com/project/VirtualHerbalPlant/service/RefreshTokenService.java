package com.project.VirtualHerbalPlant.service;

import com.project.VirtualHerbalPlant.entity.RefreshToken;
import com.project.VirtualHerbalPlant.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    public boolean save(ObjectId userId, String refreshToken){
        try {
            RefreshToken refreshtoken = RefreshToken.builder().token(refreshToken).userId(userId)
                    .expiryDate(Instant.now().plus(7, ChronoUnit.DAYS)).build();
            refreshTokenRepository.save(refreshtoken);
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }


    }

    public void invalidateToken(ObjectId userid) {
        refreshTokenRepository.deleteByUserId(userid);
    }
}

