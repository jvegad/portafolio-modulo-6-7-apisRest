package com.mediplus.api;

import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import io.restassured.http.ContentType;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacienteApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/pacientes";
    }

    @Test
    @Order(1)
    public void testGetPacientesInitiallyEmpty() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    @Order(2)
    public void testCrearPaciente() {
        String json = """
            {
                "nombre": "Matias",
                "apellido": "Carrión",
                "edad": 30
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("nombre", equalTo("Matias"))
                .body("apellido", equalTo("Carrión"));
    }

    @Test
    @Order(3)
    public void testActualizarPaciente() {
        String json = """
            {
                "nombre": "Matias",
                "apellido": "Carrión",
                "edad": 31
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put("/1")
                .then()
                .statusCode(200)
                .body("edad", equalTo(31));
    }

    @Test
    @Order(5)
    public void testEliminarPaciente() {
        when()
                .delete("/1")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(4)
    public void testGetPacienteNoExistente() {
        when()
                .get("/9999")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    public void testCrearPacienteInvalido() {
        String json = """
            {
                "nombre": "",
                "apellido": "",
                "edad": -1
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .statusCode(400);
    }
}
