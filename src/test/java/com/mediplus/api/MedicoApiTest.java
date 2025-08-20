package com.mediplus.api;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
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
public class MedicoApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/medicos";
    }

    @Test
    @Order(1)
    public void testGetMedicosInitiallyEmpty() {
        given()
                .when()
                .get()
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    @Order(2)
    public void testCrearMedico() {
        String json = """
            {
                "nombre": "Ana",
                "apellido": "Gómez",
                "especialidad": "Cardiología"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post()
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nombre", equalTo("Ana"))
                .body("apellido", equalTo("Gómez"));
    }

    @Test
    @Order(3)
    public void testActualizarMedico() {
        String json = """
            {
                "especialidad": "Neurología"
            }
        """;

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .put("/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("especialidad", equalTo("Neurología"));
    }

    @Test
    @Order(5)
    public void testGetMedicoNoExistente() {
        when()
                .get("/999")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    public void testCrearMedicoInvalido() {
        String json = """
            {
                "nombre": "",
                "especialidad": "Cirugía"
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

    @Test
    @Order(4)
    public void testEliminarMedico() {
        when()
                .delete("/1")
                .then()
                .statusCode(204);
    }
}
