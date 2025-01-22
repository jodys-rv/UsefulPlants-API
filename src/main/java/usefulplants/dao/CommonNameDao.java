package usefulplants.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import usefulplants.entity.CommonName;

public interface CommonNameDao extends JpaRepository<CommonName, Long> {
  Optional<CommonName> findByName(String name);
}
