package usefulplants.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import usefulplants.entity.CommonName;

@Data
@NoArgsConstructor
public class CommonNameData {

  private Long nameId;
  private String name;


  public CommonNameData(CommonName commonName) {
    this.nameId = commonName.getNameId();
    this.name = commonName.getName();

  }

  public CommonName toCommonName() {
    CommonName commonName = new CommonName();
    commonName.setNameId(nameId);
    commonName.setName(name);

    return commonName;
  }

}
