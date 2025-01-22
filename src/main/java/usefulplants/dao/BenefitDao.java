package usefulplants.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import usefulplants.entity.Benefit;

public interface BenefitDao extends JpaRepository<Benefit, Long> {
  Optional<Benefit> findByBenefitName(String benefitName);
}
