package com.project.VirtualHerbalPlant.dto;

import com.project.VirtualHerbalPlant.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
@Builder
@Data
public class UserReplydto {
    private String id;


    private String name;

    private String dob;


    private String address;

    private String email;

    private Map<String,String > profile;

    public static UserReplydto fromEntity(UserEntity userEntity){
        return  UserReplydto.builder()
                .id(userEntity.getId().toHexString())
                .name(userEntity.getName())
                .dob(userEntity.getDob())
                .address(userEntity.getAddress())
                .email(userEntity.getEmail())
                .profile(userEntity.getProfile())
                .build();
    }
}