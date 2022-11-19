package dio.api.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dio.api.exception.ParkingNotFoundException;
import dio.api.model.Parking;
import dio.api.repository.ParkingRepository;

@Service
public class ParkingService {

  private final ParkingRepository parkingRepository;

  public ParkingService(ParkingRepository parkingRepository) {
    this.parkingRepository = parkingRepository;
  }

  @Transactional(readOnly = true)
  public List<Parking> findAll() {
    return parkingRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Parking findById(String id) {
    return parkingRepository.findById(id).orElseThrow(() -> new ParkingNotFoundException(id));
  }

  @Transactional
  public Parking create(Parking parkingCreate) {
    String uuid = getUUID();
    parkingCreate.setId(uuid);
    parkingCreate.setEntryDate(LocalDateTime.now());
    parkingRepository.save(parkingCreate);
    return parkingCreate;
  }

  @Transactional
  public Parking updateById(String id, Parking parkingCreate) {
    Parking parking = findById(id);
    parking.setColor(parkingCreate.getColor());
    parking.setState(parkingCreate.getState());
    parking.setModel(parkingCreate.getModel());
    parking.setLicense(parkingCreate.getLicense());
    parkingRepository.save(parking);
    return parking;
  }

  @Transactional
  public void delete(String id) {
    findById(id);
    parkingRepository.deleteById(id);
  }

  @Transactional
  public Parking exit(String id) {
    Parking parking = findById(id);
    parking.setExitDate(LocalDateTime.now());
    parking.setBill(20.00);
    return parkingRepository.save(parking);
  }

  private static String getUUID() {
    return UUID.randomUUID().toString().replace("-", "");
  }

}
