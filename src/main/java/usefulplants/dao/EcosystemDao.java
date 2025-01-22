package usefulplants.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import usefulplants.entity.Ecosystem;

public interface EcosystemDao extends JpaRepository<Ecosystem, Long>{

}
