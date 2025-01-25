package usefulplants.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import usefulplants.entity.Benefit;

@Data
@NoArgsConstructor
public class BenefitData {
  private Long benefitId;
  private String benefitName;


  public BenefitData(Benefit benefit) {
    this.benefitId = benefit.getBenefitId();
    this.benefitName = benefit.getBenefitName();
  }

  // I tried taking the toBenefit method out, but it broke my benefitDao somehow.
  public Benefit toBenefit() {
    Benefit benefit = new Benefit();
    benefit.setBenefitId(benefitId);
    benefit.setBenefitName(benefitName);

    return benefit;
  }

  public BenefitData(String benefitName) {
    this.benefitName = benefitName;
  }

}
