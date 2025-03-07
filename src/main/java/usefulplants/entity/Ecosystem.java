package usefulplants.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Ecosystem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long ecosystemId;

  private String ecosystemName;
  private String altitude;
  private String water;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "ecosystems")
  private Set<Plant> plants = new HashSet<>();

}
