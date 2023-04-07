package ch.qiminfo.skills.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.*;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;


@QuarkusTest
@Tag("integration")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SkillControllerTestIT {

    @Test
    @Order(1)
    public void fetchSkills() {
        given()
                .when()
                .get("/api/skills")
                .then()
                .body("size()", equalTo(6))
                .body("id", hasItems(1, 2, 3, 4, 5, 6))
                .body("name", hasItems("java", "angular"))
                .body("version", hasItems("17", "12"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void getById_OK() {
        given()
                .when()
                .get("/api/skills/1")
                .then()
                .body("id", equalTo(1))
                .body("name", equalTo("java"))
                .body("version", equalTo("8"))
                .statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    @Order(1)
    void getById_KO() {
        given().when().get("/api/movies/777").then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(2)
    void getByName_OK() {
        given()
                .when()
                .get("/api/skills/name/java")
                .then()
                .body("size()", equalTo(4))
                .body("id", hasItems(1, 2, 3, 4))
                .body("name", hasItems("java"))
                .body("version", hasItems("8", "11", "14", "17"))
                .statusCode(Response.Status.OK.getStatusCode());
    }
    @Test
    @Order(2)
    void getByName_KO() {
        given()
                .when()
                .get("/api/skills/name/notFound")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(3)
    void getByVersion_OK() {
        given()
                .when()
                .get("/api/skills/version/17")
                .then()
                .body("id", hasItems(4))
                .body("name", hasItems("java"))
                .body("version", hasItems("17"))
                .statusCode(Response.Status.OK.getStatusCode());
    }
    @Test
    @Order(3)
    void getByVersion_KO() {
        given()
                .when()
                .get("/api/skills/version/notFound")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
    @Test
    @Order(4)
    void create() {
        JsonObject jsonObject =
                Json.createObjectBuilder()
                        .add("id", 7L)
                        .add("name", "sql")
                        .add("version", "1.5")
                        .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .post("/api/skills")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Order(5)
    void updateById_OK() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "skillUpdated")
                .add("version", "V1")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/skills/2")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());
    }

    @Test
    @Order(5)
    void updateById_KO() {
        JsonObject jsonObject = Json.createObjectBuilder()
                .add("name", "skillUpdated")
                .add("version", "V1")
                .build();

        given()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonObject.toString())
                .when()
                .put("/api/skills/2000")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @Order(6)
    void deleteById_OK() {
        given()
                .when()
                .delete("/api/skills/2")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        given().when().get("/api/skills/2").then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @Order(6)
    void deleteById_KO() {
        given()
                .when()
                .delete("/api/skills/2000")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }
}
