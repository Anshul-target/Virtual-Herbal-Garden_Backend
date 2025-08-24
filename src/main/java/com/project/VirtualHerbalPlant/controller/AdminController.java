package com.project.VirtualHerbalPlant.controller;
import com.project.VirtualHerbalPlant.dto.MedicinalPlantdto;
import com.project.VirtualHerbalPlant.service.AdminService;
import com.project.VirtualHerbalPlant.service.MedicinalPlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API", description = "Endpoints for admin operations on medicinal plants")
@SecurityRequirement(name = "bearerAuth")
@AllArgsConstructor
public class AdminController {
    private  MedicinalPlantService plantService;
    @Operation(
            summary = "Save a single medicinal plant",
            description = "Adds a new medicinal plant to the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plant created successfully",
                            content = @Content(schema = @Schema(implementation = MedicinalPlantdto.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping("/save")
    public ResponseEntity<MedicinalPlantdto> savePlant(
            @RequestBody MedicinalPlantdto plantDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(plantService.savePlant(plantDTO));
    }

    @Operation(
            summary = "Upload multiple plants from CSV",
            description = "Accepts a CSV file containing medicinal plant data and saves them into the database.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Plants uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid CSV format or data"),
                    @ApiResponse(responseCode = "415", description = "Unsupported file type")
            }
    )
    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<MedicinalPlantdto>> uploadPlants(
            @Parameter(description = "CSV file containing plant data", required = true)
            @RequestParam("file") MultipartFile file) {
        List<MedicinalPlantdto> medicinalPlantdtos = plantService.savePlantsFromCSV(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicinalPlantdtos);
    }

    @Operation(
            summary = "Update a plant",
            description = "Updates details of an existing medicinal plant using its MongoDB ID.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plant updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Plant not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<MedicinalPlantdto> updatePlant(
            @Parameter(description = "MongoDB ObjectId of the plant to update") @PathVariable String id,
            @RequestBody MedicinalPlantdto plantDTO) {
        return plantService.updatePlant(id, plantDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Delete a plant",
            description = "Deletes a medicinal plant from the database by its MongoDB ID.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Plant deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Plant not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(
            @Parameter(description = "MongoDB ObjectId of the plant to delete") @PathVariable String id) {
        boolean deleted = plantService.deletePlant(id);
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllPlant()
    {
        boolean deleted = plantService.deleteAllPlant();
        return deleted ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}