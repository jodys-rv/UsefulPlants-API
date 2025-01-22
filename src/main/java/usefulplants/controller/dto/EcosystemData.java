package usefulplants.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import usefulplants.entity.Ecosystem;

@Data
@NoArgsConstructor
public class EcosystemData {
  private Long ecosystemId;
  private String ecosystemName;
  private String altitude;
  private String water;


  public EcosystemData(Ecosystem ecosystem) {
    this.ecosystemName = ecosystem.getEcosystemName();
    this.ecosystemId = ecosystem.getEcosystemId();
    this.altitude = ecosystem.getAltitude();
    this.water = ecosystem.getWater();

  }

  public Ecosystem toEcosystem() {
    Ecosystem ecosystem = new Ecosystem();
    ecosystem.setEcosystemName(ecosystemName);
    ecosystem.setEcosystemId(ecosystemId);
    ecosystem.setAltitude(altitude);
    ecosystem.setWater(water);

    return ecosystem;
  }
}
