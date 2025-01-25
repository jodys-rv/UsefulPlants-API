package usefulplants.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
public class Plant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long plantId;

  private String latinName;
  private String flowerColor;
  private String growthHabit;
  private String notes;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(columnDefinition = "LONGBLOB")
  private byte[] picture;


  @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CommonName> commonNames = new HashSet<>();

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "plant_ecosystem", joinColumns = @JoinColumn(name = "plant_id"),
      inverseJoinColumns = @JoinColumn(name = "ecosystem_id"))
  private Set<Ecosystem> ecosystems = new HashSet<>();

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "plant_benefit", joinColumns = @JoinColumn(name = "plant_id"),
      inverseJoinColumns = @JoinColumn(name = "benefit_id"))
  private Set<Benefit> benefits = new HashSet<>();

}


