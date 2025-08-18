package com.mediplus.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PacienteApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/pacientes";
    }

    @Test
    public void testGetPacientes() {
        given()
        .when()
            .get()
        .then()
            .statusCode(200)
            .body("size()", greaterThanOrEqualTo(0));
    }

    @Test
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
            .body("nombre", equalTo("Matias"))
            .body("apellido", equalTo("Carrión"));
    }

    @Test
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
    public void testEliminarPaciente() {
        when()
            .delete("/1")
        .then()
            .statusCode(204);
    }

    // Pruebas negativas
    @Test
    public void testGetPacienteNoExistente() {
        when()
            .get("/9999")
        .then()
            .statusCode(404);
    }

    @Test
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
            .statusCode(201);
    }
}
