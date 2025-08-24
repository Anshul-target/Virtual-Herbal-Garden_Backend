package com.project.VirtualHerbalPlant.dto;

import com.project.VirtualHerbalPlant.entity.MedicinalPlant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicinalPlantdto {
    private String id;

    private String commonName;
    private String scientificName;
    private String family;
    private String partUsed;
    private String primaryUses;
    private String activeCompounds;
    private String preparationMethod;
    private String dosage;
    private String sideEffects;
    private String contraindications;
    private String growingRegion;
    private String harvestingSeason;
    private String storageMethod;
    private String traditionalUse;
    private String modernResearchStatus;
    private String embedded_link;

    // Convert Entity -> DTO
    public static MedicinalPlantdto toDto(MedicinalPlant plant) {
        if (plant == null) return null;

        return MedicinalPlantdto.builder()
                .id(plant.getId().toHexString()) // ObjectId → String
                .commonName(plant.getCommonName())
                .scientificName(plant.getScientificName())
                .family(plant.getFamily())
                .partUsed(plant.getPartUsed())
                .primaryUses(plant.getPrimaryUses())
                .activeCompounds(plant.getActiveCompounds())
                .preparationMethod(plant.getPreparationMethod())
                .dosage(plant.getDosage())
                .sideEffects(plant.getSideEffects())
                .contraindications(plant.getContraindications())
                .growingRegion(plant.getGrowingRegion())
                .harvestingSeason(plant.getHarvestingSeason())
                .storageMethod(plant.getStorageMethod())
                .traditionalUse(plant.getTraditionalUse())
                .modernResearchStatus(plant.getModernResearchStatus())
                .embedded_link(plant.getEmbedded_link())
                .build();
    }
}
