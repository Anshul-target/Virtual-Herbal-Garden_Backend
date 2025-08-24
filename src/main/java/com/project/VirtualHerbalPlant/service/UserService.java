package com.project.VirtualHerbalPlant.service;


import com.project.VirtualHerbalPlant.config.UserPrincipal;
import com.project.VirtualHerbalPlant.dto.CloudinaryResponse;
import com.project.VirtualHerbalPlant.dto.DtoToEntity;
import com.project.VirtualHerbalPlant.dto.UserReplydto;
import com.project.VirtualHerbalPlant.dto.Userdto;
import com.project.VirtualHerbalPlant.entity.UserEntity;
import com.project.VirtualHerbalPlant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
   private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserReplydto save(Userdto user, CloudinaryResponse response){


        try{

            Map<String, String> profile=null;
            if(response!=null)
                profile     = Map.of("id", response.getId(), "url", response.getUrl());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            DtoToEntity util=new DtoToEntity();
            UserEntity entityuser = util.convertUser(user);
            if(profile!=null)
                entityuser.setProfile(profile);
            entityuser.setRole("ROLE_USER");
            entityuser.setCreatedAt(LocalDateTime.now().toString());
            UserEntity user1 = Objects.requireNonNull(repository.save(entityuser));

            return  UserReplydto.fromEntity(user1);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = repository.findByEmail(username);
        return UserPrincipal.create(user);
    }

    public UserEntity findUserById(ObjectId id){
        UserEntity user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    public boolean findemailexist(String email) {
        UserEntity byEmail = repository.findByEmail(email);
        if (byEmail != null)
            return true;
        else
            return false;
    }

    public UserEntity getUser(String email) {
        UserEntity user = repository.findByEmail(email);
        if (user!=null)
            return user;
        throw new RuntimeException("User not found");

    }
}
