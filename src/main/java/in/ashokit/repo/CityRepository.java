package in.ashokit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entity.City;

public interface CityRepository extends JpaRepository<City,Integer>{
	
	public List<City> findByStateId(Integer stateId);

}
