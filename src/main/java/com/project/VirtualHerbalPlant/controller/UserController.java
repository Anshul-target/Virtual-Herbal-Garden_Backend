package com.project.VirtualHerbalPlant.controller;


import com.project.VirtualHerbalPlant.dto.MedicinalPlantdto;
import com.project.VirtualHerbalPlant.entity.MedicinalPlant;
import com.project.VirtualHerbalPlant.service.MedicinalPlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/plants")
@Tag(name = "User Api", description = "Endpoints for user related operation")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {
    private static final Set<String> ALLOWED_KEYS = Set.of(
            "use", "commonName", "scientificName", "family", "partUsed",
            "compound", "preparationMethod", "dosage", "sideEffects",
            "contraindications", "region", "season", "storageMethod",
            "traditionalUse", "modernResearchStatus"
    );
    private final MedicinalPlantService plantService;
    @Operation(
            summary = "Get all medicinal plants with optional filters"

    )
   @GetMapping("/all")
    public ResponseEntity<?> getAllPlants(@RequestParam("pageNo")int pageNo,@RequestParam("size")int size) {

        return ResponseEntity.ok().body(plantService.getAllPlants(pageNo,size).getContent());
    }
    @Operation(
            summary = "Get all medicinal plants with optional filters",
            description = "Retrieves all medicinal plants from the database. " +
                    "You can provide query parameters to filter results dynamically. " +
                    "Example filters: use, family, commonName, partUsed, region, season, etc."
    )
    @GetMapping
    public ResponseEntity<?> getPlants(@RequestParam Map<String, String> params) {
        List<String> invalidKeys = params.keySet().stream().filter(key -> !ALLOWED_KEYS.contains(key)).toList();
 if (invalidKeys.isEmpty()){
  return ResponseEntity.badRequest().body("Invalid query parametes:"+invalidKeys);
 }
        return ResponseEntity.ok().body(plantService.getPlantsByFilters(params));
    }

    @Operation(
            summary = "Get a medicinal plant by ID",
            description = "Fetches a single medicinal plant by its unique MongoDB ID."
    )
    @GetMapping("/{id}")
    public MedicinalPlantdto getPlantById(@PathVariable String id) {
        Optional<MedicinalPlantdto> plantById = plantService.getPlantById(id);
        return plantById.get();
    }
}
