package usefulplants.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import usefulplants.controller.dto.BenefitData;
import usefulplants.controller.dto.CommonNameData;
import usefulplants.controller.dto.EcosystemData;
import usefulplants.controller.dto.PlantData;
import usefulplants.dao.BenefitDao;
import usefulplants.dao.CommonNameDao;
import usefulplants.dao.EcosystemDao;
import usefulplants.dao.PlantDao;
import usefulplants.entity.Benefit;
import usefulplants.entity.CommonName;
import usefulplants.entity.Ecosystem;
import usefulplants.entity.Plant;

@Service
public class PlantService {

  private PlantDao plantDao;
  private CommonNameDao commonNameDao;
  private BenefitDao benefitDao;
  private EcosystemDao ecosystemDao;

  public PlantService(PlantDao plantDao, CommonNameDao commonNameDao, BenefitDao benefitDao,
      EcosystemDao ecosystemDao) {
    this.plantDao = plantDao;
    this.commonNameDao = commonNameDao;
    this.benefitDao = benefitDao;
    this.ecosystemDao = ecosystemDao;
  }

  /**
   * Saves a plant to the database.
   * 
   * @param PlantData passed in from the client
   * @return PlantData returned from the database.
   */
  @Transactional(readOnly = false)
  public PlantData savePlant(PlantData plantData) {
    Long plantId = plantData.getPlantId();
    Plant plant = findOrCreatePlant(plantId);
    setPlantFields(plant, plantData);
    return new PlantData(plantDao.save(plant));
  }

  /**
   * Copies the fields from a PlantData to a Plant.
   * 
   * @param plant
   * @param plantData
   */
  private void setPlantFields(Plant plant, PlantData plantData) {
    plant.setLatinName(plantData.getLatinName());
    plant.setFlowerColor(plantData.getFlowerColor());
    plant.setGrowthHabit(plantData.getGrowthHabit());
    plant.setNotes(plantData.getNotes());
  }

  /**
   * If the plantId is null or 0, creates a new plant. Otherwise, calls retrievePlantById().
   * 
   * @param plantId
   * @return either a Plant from the database, or a new plant.
   */
  private Plant findOrCreatePlant(Long plantId) {
    Plant plant;
    if (Objects.isNull(plantId) || plantId == 0) {
      plant = new Plant();
    } else {
      plant = retrievePlantById(plantId);
    }
    return plant;
  }

  /**
   * Calls retrievePlantById().
   * 
   * @param plantId
   * @return PlantData
   */
  @Transactional(readOnly = true)
  public PlantData findPlantById(Long plantId) {
    Plant plant = retrievePlantById(plantId);
    return new PlantData(plant);
  }

  /**
   * Searches the database for a plant; if it does not exist throws an exception.
   * 
   * @param plantId
   * @return
   */
  public Plant retrievePlantById(Long plantId) {
    return plantDao.findById(plantId)
        .orElseThrow(() -> new NoSuchElementException("There is no plant number " + plantId
            + ". Select a plant from the database or choose 'add a plant.'"));
  }

  /**
   * Adds a common name to a plant already in the database.
   * 
   * @param plantId
   * @param name
   * @return PlantData
   */
  @Transactional(readOnly = false)
  public PlantData addCommonNameToPlant(Long plantId, CommonNameData name) {
    Plant plant = retrievePlantById(plantId);
    CommonName commonName = createCommonName(name, plant);

    Set<CommonName> names = plant.getCommonNames();
    names.add(commonName);
    plant.setCommonNames(names);

    return new PlantData(plantDao.save(plant));
  }

  /**
   * Adds a common name to the database.
   * 
   * @param name
   * @param plant
   * @return commonName
   */
  private CommonName createCommonName(CommonNameData name, Plant plant) {
    CommonName commonName = findOrCreateName(name.getNameId());
    copyCommonNameFields(name, commonName, plant);
    return commonNameDao.save(commonName);
  }

  /**
   * Sets the fields in a commonName.
   * 
   * @param name
   * @param commonName
   * @param plant
   */
  private void copyCommonNameFields(CommonNameData name, CommonName commonName, Plant plant) {
    commonName.setPlant(plant);
    commonName.setName(name.getName());

  }

  /**
   * If the ID of the given commonName is null or 0, creates a new commonName. Otherwise retrieves
   * the commonName by its ID.
   * 
   * @param commonNameId
   * @return CommonName
   */
  private CommonName findOrCreateName(Long commonNameId) {
    CommonName name;
    if (Objects.isNull(commonNameId) || commonNameId == 0) {
      name = new CommonName();
    } else {
      name = findNameById(commonNameId);
    }
    return name;
  }

  /**
   * Retrieves the commonNmae by its ID if it exists. Otherwise throws exception.
   * 
   * @param commonNameId
   * @return CommonName
   */
  private CommonName findNameById(Long commonNameId) {
    return commonNameDao.findById(commonNameId)
        .orElseThrow(() -> new NoSuchElementException("That name is not in the database."));
  }

  /**
   * Calls lookUpName().
   * 
   * @param name
   * @return PlantData
   */
  @Transactional(readOnly = true)
  public PlantData findPlantByCommonName(String name) {
    CommonName commonName = lookUpName(name);
    return new PlantData(commonName.getPlant());
  }

  /**
   * Finds a common name by name; otherwise throws exception.
   * 
   * @param name
   * @return CommonName
   */
  private CommonName lookUpName(String name) {
    return commonNameDao.findByName(name).orElseThrow(
        () -> new NoSuchElementException("Can't find anything called " + name + ", sorry."));
  }

  /**
   * Creates benefit and adds it to the given Plant
   * 
   * @param plantId
   * @param benefitData
   * @return PlantData
   */
  @Transactional(readOnly = false)
  public PlantData addBenefitToPlant(Long plantId, BenefitData benefitData) {
    Plant plant = retrievePlantById(plantId);

    Benefit benefit = createBenefit(benefitData, plant);

    Set<Benefit> benefits = plant.getBenefits();
    benefits.add(benefit);
    plant.setBenefits(benefits);

    return new PlantData(plantDao.save(plant));
  }

  /**
   * Creates benefit.
   * 
   * @param benefitData
   * @param plant
   * @return Benefit
   */
  private Benefit createBenefit(BenefitData benefitData, Plant plant) {
    Benefit benefit = findOrCreateBenefit(benefitData.getBenefitId());
    setBenefitFields(benefit, benefitData, plant);
    return benefitDao.save(benefit);
  }

  /**
   * Copies the fields from an existing BenefitData to an existing Benefit. Does not create a new
   * benefit object.
   * 
   * @param benefit
   * @param benefitData
   * @param plant
   */
  private void setBenefitFields(Benefit benefit, BenefitData benefitData, Plant plant) {
    benefit.setBenefitId(benefitData.getBenefitId());
    benefit.setBenefitName(benefitData.getBenefitName());
    Set<Plant> plants = benefit.getPlants();
    plants.add(plant);
    benefit.setPlants(plants);
  }

  /**
   * If the ID of a benefit is null or 0, creates a new benefit. otherwise calls findBenefitById().
   * 
   * @param benefitId
   * @return Benefit
   */
  private Benefit findOrCreateBenefit(Long benefitId) {
    Benefit benefit;
    if (Objects.isNull(benefitId) || benefitId == 0) {
      benefit = new Benefit();
    } else {
      benefit = findBenefitById(benefitId);
    }
    return benefit;
  }

  /**
   * Finds a benefit in the database if in exists, otherwise throws exception.
   * 
   * @param benefitId
   * @return Benefit
   */
  private Benefit findBenefitById(Long benefitId) {
    return benefitDao.findById(benefitId)
        .orElseThrow(() -> new NoSuchElementException("That benefit is not in the database."));
  }

  /**
   * Retrieves all ecosystems from the database.
   * 
   * @return List of EcosystemDatas.
   */
  @Transactional(readOnly = true)
  public List<EcosystemData> getAllEcosystems() {
    //@formatter:off
    return ecosystemDao.findAll()
        .stream()
        .map(EcosystemData::new)
        .toList();
    //@formatter:on
  }

  /**
   * Calls retrieveEcosystemByID().
   * 
   * @param ecosystemId
   * @return EcosystemData
   */
  @Transactional(readOnly = true)
  public EcosystemData findEcosystemById(Long ecosystemId) {
    Ecosystem ecosystem = retrieveEcosystemById(ecosystemId);
    return new EcosystemData(ecosystem);
  }

  /**
   * Finds an ecosystem by ID if it exists, otherwise throws exception.
   * 
   * @param ecosystemId
   * @return Ecosystem
   */
  private Ecosystem retrieveEcosystemById(Long ecosystemId) {
    return ecosystemDao.findById(ecosystemId).orElseThrow(() -> new NoSuchElementException(
        "that's not one of the ecosystems I programmed in, and I'm not letting you add any more right now."));
  }

  /**
   * Retrieves a List all plants in a given ecosystem.
   * 
   * @param ecosystemId
   * @return List of Plants
   */
  @Transactional(readOnly = true)
  public List<PlantData> retrieveAllPlantsInEcosystem(Long ecosystemId) {
    Ecosystem ecosystem = retrieveEcosystemById(ecosystemId);
    return ecosystem.getPlants().stream().map(PlantData::new).toList();
  }

  /**
   * Adds an existing plant to an existing ecosystem.
   * 
   * @param ecosystemId
   * @param plantId
   * @return ecosystemData
   */
  @Transactional(readOnly = false)
  public EcosystemData addPlantToEcosystem(Long ecosystemId, Long plantId) {
    Plant plant = retrievePlantById(plantId);
    Ecosystem ecosystem = retrieveEcosystemById(ecosystemId);

    saveEcosystemToPlant(ecosystem, plant);

    Set<Plant> plants = ecosystem.getPlants();
    plants.add(plant);

    return new EcosystemData(ecosystemDao.save(ecosystem));
  }

  /**
   * Adds an existing ecosystem to an existing plant.
   * 
   * @param ecosystem
   * @param plant
   */
  private void saveEcosystemToPlant(Ecosystem ecosystem, Plant plant) {
    Set<Ecosystem> ecosystems = plant.getEcosystems();
    ecosystems.add(ecosystem);
    plant.setEcosystems(ecosystems);
    plantDao.save(plant);
  }

  /**
   * Deletes a Plant from the database.
   * 
   * @param plantId
   */
  @Transactional(readOnly = false)
  public void deletePlant(Long plantId) {
    Plant plant = retrievePlantById(plantId);
    plantDao.delete(plant);
  }

  @Transactional(readOnly = true)
  public List<PlantData> GetAllPlantsWithBenefit(Long benefitId) {
    Benefit benefit = findBenefitById(benefitId);
    return benefit.getPlants().stream().map(PlantData::new).toList();
  }

  public byte[] addPictureToPlant(Long plantId, byte[] bytes, String contentType) {
    Plant plant = retrievePlantById(plantId);
    if(plant != null) {
      plant.setPicture(bytes);
      Plant result = plantDao.save(plant);
      if(result != null) {
        return result.getPicture();
      }
    }
    return null;
  }

}
