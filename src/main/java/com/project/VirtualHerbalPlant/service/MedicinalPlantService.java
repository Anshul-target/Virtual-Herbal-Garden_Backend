package com.project.VirtualHerbalPlant.service;

import com.opencsv.CSVReader;
import com.project.VirtualHerbalPlant.dto.MedicinalPlantdto;
import com.project.VirtualHerbalPlant.entity.MedicinalPlant;
import com.project.VirtualHerbalPlant.repository.MedicinalPlantRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Builder
@RequiredArgsConstructor
public class MedicinalPlantService {

    private final MedicinalPlantRepository plantRepository;
    private final MongoTemplate mongoTemplate;

    // Save single plant
    public MedicinalPlantdto savePlant(MedicinalPlantdto plantDTO) {
        MedicinalPlant plant = MedicinalPlant.fromDto(plantDTO);
        return MedicinalPlantdto.toDto(plantRepository.save(plant));
    }

    // Save multiple plants from CSV
// Save multiple plants from CSV with sanitization
    public List<MedicinalPlantdto> savePlantsFromCSV(MultipartFile file) {
        List<MedicinalPlant> plants = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = csvReader.readAll();

            // Skip header row
            for (int i = 1; i < records.size(); i++) {
                String[] row = records.get(i);

                // Skip empty / malformed rows
                if (row == null || row.length < 17) {
                    continue;
                }

                // Trim all fields
                for (int j = 0; j < row.length; j++) {
                    row[j] = row[j] != null ? row[j].trim() : "";
                }

                // Validate essential fields
                if (row[1].isEmpty() || row[2].isEmpty()) {
                    // Skip if commonName or scientificName missing
                    continue;
                }
                MedicinalPlantdto dto = MedicinalPlantdto.builder()
                        .commonName(row[1])
                        .scientificName(row[2])
                        .family(row[3])
                        .partUsed(row[4])
                        .primaryUses(row[5])
                        .activeCompounds(row[6])
                        .preparationMethod(row[7])
                        .dosage(row[8])
                        .sideEffects(row[9])
                        .contraindications(row[10])
                        .growingRegion(row[11])
                        .harvestingSeason(row[12])
                        .storageMethod(row[13])
                        .traditionalUse(row[14])
                        .modernResearchStatus(row[15])
                        .embedded_link(row[16])
                        .build();

                plants.add(MedicinalPlant.fromDto(dto));
            }

            return plantRepository.saveAll(plants).stream()
                    .map(MedicinalPlantdto::toDto)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage(), e);
        }
    }


    // Get all plants
    public List<MedicinalPlantdto> getAllPlants() {
        return plantRepository.findAll().stream()
                .map(MedicinalPlantdto::toDto)
                .collect(Collectors.toList());
    }

    // Get plant by ID
    public Optional<MedicinalPlantdto> getPlantById(String id) {
        return plantRepository.findById(new ObjectId(id))
                .map(MedicinalPlantdto::toDto);
    }

    // Search plants by use
    public List<MedicinalPlantdto> searchPlantsByUse(String use) {
        return plantRepository.findByPrimaryUsesContaining(use).stream()
                .map(MedicinalPlantdto::toDto)
                .collect(Collectors.toList());
    }

    // Get plants by family
    public List<MedicinalPlantdto> getPlantsByFamily(String family) {
        return plantRepository.findByFamilyIgnoreCase(family).stream()
                .map(MedicinalPlantdto::toDto)
                .collect(Collectors.toList());
    }

    // Delete plant
    public boolean deletePlant(String id) {
        try {
            plantRepository.deleteById(new ObjectId(id));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Update plant
    public Optional<MedicinalPlantdto> updatePlant(String id, MedicinalPlantdto plantDTO) {
        return plantRepository.findById(new ObjectId(id))
                .map(existingPlant -> {
                    // Replace entity with new data from DTO
                    MedicinalPlant updated = MedicinalPlant.fromDto(plantDTO);
                    updated.setId(existingPlant.getId()); // keep the same MongoDB ID
                    return MedicinalPlantdto.toDto(plantRepository.save(updated));
                });
    }

//    Dynamic filter  Query
// Dynamic filter query
public List<MedicinalPlant> getPlantsByFilters(Map<String, String> filters) {
    Query query=new Query();

    filters.forEach((key, value) -> {
        if (value != null && !value.isEmpty()) {
            switch (key) {
                case "use" -> query.addCriteria(Criteria.where("primary_uses").regex(value, "i"));
                case "commonName" -> query.addCriteria(Criteria.where("common_name").regex(value, "i"));
                case "scientificName" -> query.addCriteria(Criteria.where("scientific_name").regex(value, "i"));
                case "family" -> query.addCriteria(Criteria.where("family").regex(value, "i"));
                case "partUsed" -> query.addCriteria(Criteria.where("part_used").regex(value, "i"));
                case "compound" -> query.addCriteria(Criteria.where("active_compounds").regex(value, "i"));
                case "preparationMethod" -> query.addCriteria(Criteria.where("preparation_method").regex(value, "i"));
                case "dosage" -> query.addCriteria(Criteria.where("dosage").regex(value, "i"));
                case "sideEffects" -> query.addCriteria(Criteria.where("side_effects").regex(value, "i"));
                case "contraindications" -> query.addCriteria(Criteria.where("contraindications").regex(value, "i"));
                case "region" -> query.addCriteria(Criteria.where("growing_region").regex(value, "i"));
                case "season" -> query.addCriteria(Criteria.where("harvesting_season").regex(value, "i"));
                case "storageMethod" -> query.addCriteria(Criteria.where("storage_method").regex(value, "i"));
                case "traditionalUse" -> query.addCriteria(Criteria.where("traditional_use").regex(value, "i"));
                case "modernResearchStatus" -> query.addCriteria(Criteria.where("modern_research_status").regex(value, "i"));
            }
        }
    });

    return mongoTemplate.find(query, MedicinalPlant.class);
}

    public boolean deleteAllPlant() {
     plantRepository.deleteAll();
        return true;
    }

    public Page<MedicinalPlantdto> getAllPlants(int pageNo, int size) {
        PageRequest pageRequest=PageRequest.of(pageNo,size);
        return plantRepository.findAll(pageRequest).map(MedicinalPlantdto::toDto);
    }
}


