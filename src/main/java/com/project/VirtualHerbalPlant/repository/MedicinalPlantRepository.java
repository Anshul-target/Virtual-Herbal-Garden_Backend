package com.project.VirtualHerbalPlant.repository;

import com.project.VirtualHerbalPlant.entity.MedicinalPlant;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MedicinalPlantRepository extends MongoRepository<MedicinalPlant, ObjectId> {

    // Find by common name (case insensitive)
//  MongoDB doesn’t have a native “equals ignore case” operator.
//So Spring Data uses regular expressions to simulate it:
//    { "common_name": { "$regex": "^value$", "$options": "i" } }
//    ^ → Start of string anchor
//
//This tells the regex engine: “the match must begin at the very start of the string.”
//
//Without it, "MyNeem" would also match, because "Neem" is inside the string.
//
//With it, "MyNeem" ❌ does not match.
//
//2. Neem → Literal text
//
//This part is just the characters you want to match exactly: N, e, e, m.
//
//Since $options: "i" is set in MongoDB, case doesn’t matter:
//
//"Neem", "neem", "NEEM", "NeEm" ✅ match
//
//3. $ → End of string anchor
//
//This says: “the match must end at the end of the string.”
//
//Without it, "NeemTree" would match, because "Neem" is inside.
//
//With it, "NeemTree" ❌ does not match.
//    Since there’s no Containing, Spring Data treats this as an exact match.
//    if containig is present then the quesry look like
//    { "common_name": { "$regex": "value", "$options": "i" } }
    Optional<MedicinalPlant> findByCommonNameIgnoreCase(String commonName);

    // Find by scientific name
    Optional<MedicinalPlant> findByScientificNameIgnoreCase(String scientificName);

    // Find by plant family
    List<MedicinalPlant> findByFamilyIgnoreCase(String family);

    // Search by primary uses (contains text)
//    @Query defaults to find (read) operations
    @Query("{ 'primary_uses': { $regex: ?0, $options: 'i' } }")
    List<MedicinalPlant> findByPrimaryUsesContaining(String use);

    // Find by growing region
//   Your query JSON inside @Query is just the filter criteria.
//Whether it’s a find, delete, or update depends on:
//
//The method name / return type
//
//Extra annotations (@DeleteQuery, @Modifying)
//    If you wanted this to delete or update, you would need to mark it with additional annotations:
//    Delete example:
//
//@Query("{ 'primary_uses': { $regex: ?0, $options: 'i' } }")
//@DeleteQuery
//    Update example:
//Updates require using @Modifying along with @Query, like:
//
//@Modifying
//@Query("{ '_id': ?0 }")
//void updatePrimaryUses(ObjectId id, String newUse);
//Since you wrote findBy... and return a List, it’s a read query.
    @Query("{ 'growing_region': { $regex: ?0, $options: 'i' } }")
    List<MedicinalPlant> findByGrowingRegionContaining(String region);

    // Find by plant ID

    // Check if plant exists by common name
    boolean existsByCommonNameIgnoreCase(String commonName);
}

//### 🔎 Your entity field
//

//@Field("common_name")
//private String commonName;
//```
//
//* In **MongoDB collection**, the key will be stored as:
//

//  {
//    "common_name": "Neem",
//    ...
//  }
//  ```
//* In your **Java entity**, you access it as:
//
//  ```java
//  plant.getCommonName();
//  ```
//
//---
//
//### 🔎 The repository method
//
//```java
//Optional<MedicinalPlant> findByCommonNameIgnoreCase(String commonName);
//```
//
//Spring Data does **name translation**:
//
//1. It looks at your method name → `findByCommonNameIgnoreCase`.
//2. It matches `commonName` to the **Java property name** (`private String commonName`).
//3. It sees `@Field("common_name")`, so it knows that in MongoDB the actual field name is `"common_name"`.
//4. When executing the query, it translates automatically into a MongoDB query like:
//
//```json
//{
//  "common_name": { "$regex": "^Neem$", "$options": "i" }
//}
//```
//
//So even though your repository uses the Java field name (`commonName`), Spring Data automatically maps it to the MongoDB field (`common_name`) using the `@Field` annotation.
//
//---
//
//### ✅ Example in action
//
//Suppose you save this document in MongoDB:
//
//```json
//{
//  "_id": ObjectId("..."),
//  "plant_id": 101,
//  "common_name": "Neem",
//  "scientific_name": "Azadirachta indica",
//  "family": "Meliaceae"
//}
//```
//
//If you call in service/controller:
//
//```java
//Optional<MedicinalPlant> neem = repo.findByCommonNameIgnoreCase("neem");
//```
//
//👉 Spring Data will generate:
//
//```json
//db.medicinal_plants.findOne({
//  "common_name": { "$regex": "^neem$", "$options": "i" }
//});
//```
//
//And it will return your `MedicinalPlant` object.
//
//---
//
//⚡ So don’t worry:
//Even though the **MongoDB document key** is `common_name`, you only ever work with `commonName` in Java — Spring Data takes care of translating thanks to the `@Field` annotation.
//
//---
//
//Would you like me to also explain how `findByPrimaryUsesContaining` (with regex) differs from `findByCommonNameIgnoreCase` (exact match ignoring case)?