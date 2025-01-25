package usefulplants.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import usefulplants.controller.dto.BenefitData;
import usefulplants.controller.dto.CommonNameData;
import usefulplants.controller.dto.EcosystemData;
import usefulplants.controller.dto.PlantData;
import usefulplants.service.PlantService;

@RestController
@RequestMapping("/usefulplants")
@Slf4j
public class UsefulplantsController {
  private PlantService service;

  public UsefulplantsController(PlantService service) {
    this.service = service;
  }

  /**
   * Accepts a PlantData from the front end, places it in the database.
   * 
   * @param plantData
   * @return plantData
   */
  @PostMapping("/plant")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PlantData addPlant(@RequestBody PlantData plantData) {
    log.info("adding plant {} to database.", plantData);
    return service.savePlant(plantData);
  }

  /**
   * Deletes a plant from the database.
   * 
   * @param plantId
   * @return a Map message confirming success.
   */
  @DeleteMapping("/plant/{plantId}")
  public Map<String, String> deletePlant(@PathVariable Long plantId) {
    log.info("deleting plant {}", plantId);
    service.deletePlant(plantId);
    return Map.of("message", "plant " + plantId + " was deleted successfully.");
  }

  /**
   * Finds a plant by its ID.
   * 
   * @param plantId
   * @return plantData
   */
  @GetMapping("/plant/{plantId}")
  public PlantData findPlantById(@PathVariable Long plantId) {
    log.info("Getting plant {} from the database.", plantId);
    return service.findPlantById(plantId);
  }

  /**
   * Adds a CommonName to a Plant in the database.
   * 
   * @param plantId
   * @param name
   * @return PlantData
   */
  @PutMapping("/commonName/{plantId}")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PlantData addCommonNameToPlant(@PathVariable Long plantId,
      @RequestBody CommonNameData name) {
    PlantData plantData = service.findPlantById(plantId);
    log.info(name.toString());
    log.info("Got it. {} is a common name for {}, which is plant {} in the database.",
        name.getName(), plantData.getLatinName(), plantData.getPlantId());
    return service.addCommonNameToPlant(plantId, name);
  }

  /**
   * Searches for a Plant by its common name.
   * 
   * @param name
   * @return
   */
  @GetMapping("/{name}")
  public PlantData findPlantByCommonName(@PathVariable String name) {
    log.info("looking for a plant called {}", name);
    return service.findPlantByCommonName(name);
  }

  /**
   * Adds a benefit to a plant, and vice versa. Many to many relationship. Can connect the plant
   * with a benefit that already exists, or add a new benefit to the database.
   * 
   * @param plantId
   * @param benefit
   * @return PlantData
   */
  @PutMapping("/plant/benefit/{plantId}")
  @ResponseStatus(code = HttpStatus.CREATED)
  public PlantData addBenefitToPlant(@PathVariable Long plantId, @RequestBody BenefitData benefit) {
    PlantData plantData = service.findPlantById(plantId);
    log.info("You can use {} as a {}.", plantData.getLatinName(), benefit.getBenefitName());
    return service.addBenefitToPlant(plantId, benefit);
  }

  /**
   * Retrieves all the Ecosystems in the database.
   * 
   * @return List of Ecosystems
   */
  @GetMapping("/ecosystem")
  public List<EcosystemData> retrieveAllEcosystems() {
    log.info(
        "Getting a list of what I *should* have called biomes, but by the time I realized it I had written too much code to change it.");
    return service.getAllEcosystems();
  }

  /**
   * Retrieves an Ecosystem by its ID.
   * 
   * @param ecosystemId
   * @return EcosystemData
   */
  @GetMapping("/ecosystem/{ecosystemId}")
  public EcosystemData findEcosystemById(@PathVariable Long ecosystemId) {
    return service.findEcosystemById(ecosystemId);
  }

  /**
   * Retrieves all the plants in a given ecosystem.
   * 
   * @param ecosystemId
   * @return List of PlantDatas
   */
  @GetMapping("/ecosystem/plants/{ecosystemId}")
  public List<PlantData> retrieveAllPlantsInEcosystem(@PathVariable Long ecosystemId) {
    EcosystemData ecosystemData = service.findEcosystemById(ecosystemId);
    log.info("Getting all plants from the {} ecosystem", ecosystemData.getEcosystemName());
    return service.retrieveAllPlantsInEcosystem(ecosystemId);
  }

  /**
   * Adds a Plant to an Ecosystem and vice versa. Many to many relationship.
   * 
   * @param ecosystemId
   * @param plantId
   * @return EcosystemData I don't think that's best, though, it's pretty uninformative. Think I'll
   *         change it to map like the delete endpoint.
   */
  @PutMapping("/ecosystem/{ecosystemId}/{plantId}")
  public EcosystemData addPlantToEcosystem(@PathVariable Long ecosystemId,
      @PathVariable Long plantId) {
    return service.addPlantToEcosystem(ecosystemId, plantId);
  }

  /**
   * Retrieves all plants that have a given benefit.
   * 
   * @param benefitId
   * @return List of plantDatas.
   */
  @GetMapping("/benefit/{benefitId}")
  public List<PlantData> getAllPlantsWithBenefit(@PathVariable Long benefitId) {
    return service.GetAllPlantsWithBenefit(benefitId);
  }

  @PutMapping("/plant/{plantId}/picture")
  public Map<String,String> uploadPicture(@PathVariable Long plantId, @RequestParam(name = "file") MultipartFile file) {
    Map<String, String> response = new HashMap<>();
    byte[] picture;
    try {
      picture = service.addPictureToPlant(plantId, file.getBytes(), file.getContentType());
      if(picture != null && picture.length > 0) {
        response.put("size:", Long.toString(picture.length));
        response.put("file type:", file.getContentType());
        return response;
      }
      response.put("error!", "failed to upload your picture.");
    } catch (IOException e) {
      response.put("error!", e.getMessage());
    }
    return response;
  }
}
