package com.project.VirtualHerbalPlant.dto;

import com.project.VirtualHerbalPlant.entity.Bookmark;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
@Builder
@Data
public class Bookmarkdto {
    private String id;
    private String userId;  // Reference to User
    private String plantId; // Reference to MedicinalPlant

    public static Bookmarkdto fromEntity(Bookmark bookmark){
  return      Bookmarkdto.builder().id(bookmark.getId().toHexString())
               .userId(bookmark.getUserId().toHexString()).
        plantId(bookmark.getPlantId().toHexString())
               .build();
    }
}

