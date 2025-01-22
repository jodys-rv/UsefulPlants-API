package usefulplants.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import usefulplants.entity.Plant;

public interface PlantDao extends JpaRepository<Plant, Long>{

}
