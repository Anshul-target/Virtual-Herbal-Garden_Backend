package com.project.VirtualHerbalPlant.dto;

import com.project.VirtualHerbalPlant.entity.UserEntity;

public class DtoToEntity {
    public UserEntity convertUser(Userdto userdto){
        UserEntity user=new UserEntity();
        user.setName(userdto.getName());

        user.setDob(userdto.getDob());
        user.setEmail(userdto.getEmail());
        user.setAddress(userdto.getAddress());
        user.setPassword(userdto.getPassword());
        return  user;
    }
}