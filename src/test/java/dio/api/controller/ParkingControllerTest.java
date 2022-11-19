package dio.api.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import dio.api.controller.dto.ParkingCreateDTO;
import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerTest {

  @LocalServerPort
  private int randomPort;

  @BeforeEach
  public void setupTest() {
    RestAssured.port = randomPort;
  }

  @Test
  void whenFindAllThenCheckResult() {
    RestAssured.given()
        .when()
        .get("/parking")
        .then()
        .statusCode(200);
  }

  @Test
  void whenCreateThenCheckIsCreated() {

    ParkingCreateDTO createDTO = new ParkingCreateDTO();
    createDTO.setColor("Amarelo");
    createDTO.setLicense("WRT-5555");
    createDTO.setModel("Brasilia");
    createDTO.setState("SP");

    RestAssured.given()
        .when()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .body(createDTO)
        .post("/parking")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("license", Matchers.equalTo("WRT-5555"))
        .body("color", Matchers.equalTo("Amarelo"));
  }
}
