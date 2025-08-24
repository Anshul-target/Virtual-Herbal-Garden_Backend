package com.project.VirtualHerbalPlant.entity;
import com.project.VirtualHerbalPlant.dto.MedicinalPlantdto;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "medicinal_plants")
public class MedicinalPlant {


    @Id
    private ObjectId id;



    @Field("common_name")
    private String commonName;

    @Field("scientific_name")
    private String scientificName;

    private String family;

    @Field("part_used")
    private String partUsed;

    @Field("primary_uses")
    private String primaryUses;

    @Field("active_compounds")
    private String activeCompounds;

    @Field("preparation_method")
    private String preparationMethod;

    private String dosage;

    @Field("side_effects")
    private String sideEffects;

    private String contraindications;

    @Field("growing_region")
    private String growingRegion;

    @Field("harvesting_season")
    private String harvestingSeason;

    @Field("storage_method")
    private String storageMethod;

    @Field("traditional_use")
    private String traditionalUse;

    @Field("modern_research_status")
    private String modernResearchStatus;
    private String embedded_link;

    // Convert DTO -> Entity
    public static MedicinalPlant fromDto(MedicinalPlantdto dto) {
        if (dto == null) return null;

        return MedicinalPlant.builder()
                .commonName(dto.getCommonName())
                .scientificName(dto.getScientificName())
                .family(dto.getFamily())
                .partUsed(dto.getPartUsed())
                .primaryUses(dto.getPrimaryUses())
                .activeCompounds(dto.getActiveCompounds())
                .preparationMethod(dto.getPreparationMethod())
                .dosage(dto.getDosage())
                .sideEffects(dto.getSideEffects())
                .contraindications(dto.getContraindications())
                .growingRegion(dto.getGrowingRegion())
                .harvestingSeason(dto.getHarvestingSeason())
                .storageMethod(dto.getStorageMethod())
                .traditionalUse(dto.getTraditionalUse())
                .modernResearchStatus(dto.getModernResearchStatus())
                .embedded_link(dto.getEmbedded_link())
                .build();
    }
}
