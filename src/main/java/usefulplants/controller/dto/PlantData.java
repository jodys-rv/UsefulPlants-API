package usefulplants.controller.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import usefulplants.entity.Benefit;
import usefulplants.entity.CommonName;
import usefulplants.entity.Ecosystem;
import usefulplants.entity.Plant;

@Data
@NoArgsConstructor
public class PlantData {
  private Long plantId;
  private String latinName;
  private String flowerColor;
  private String growthHabit;
  private String notes;
  private Set<CommonNameData> commonNames = new HashSet<>();
  private Set<EcosystemData> ecosystems = new HashSet<>();
  private Set<BenefitData> benefits = new HashSet<>();

  public PlantData(Plant plant) {
    this.plantId = plant.getPlantId();
    this.latinName = plant.getLatinName();
    this.flowerColor = plant.getFlowerColor();
    this.growthHabit = plant.getGrowthHabit();
    this.notes = plant.getNotes();

    for (CommonName commonName : plant.getCommonNames()) {
      this.commonNames.add(new CommonNameData(commonName));
    }
    for (Ecosystem ecosystem : plant.getEcosystems()) {
      this.ecosystems.add(new EcosystemData(ecosystem));
    }
    for (Benefit benefit : plant.getBenefits()) {
      this.benefits.add(new BenefitData(benefit));
    }
  }

  /**
   * copies the data from a plantData to a new Plant object.
   * 
   * @param plantData
   * @return Plant
   */
  public Plant toPlant(PlantData plantData) {
    Plant plant = new Plant();
    plant.setPlantId(plantId);
    plant.setLatinName(latinName);
    plant.setFlowerColor(flowerColor);
    plant.setGrowthHabit(growthHabit);
    plant.setNotes(notes);

    for (CommonNameData commonNameData : commonNames) {
      plant.getCommonNames().add(commonNameData.toCommonName());
    }
    for (EcosystemData ecosystemData : ecosystems) {
      plant.getEcosystems().add(ecosystemData.toEcosystem());
    }
    for (BenefitData benefitData : benefits) {
      plant.getBenefits().add(benefitData.toBenefit());
    }
    return plant;
  }

}
