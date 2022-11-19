package dio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dio.api.model.Parking;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, String> {

}
