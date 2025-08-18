package com.mediplus.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class MedicoApiTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost/medicos/1";
        RestAssured.port = 8080;
        RestAssured.basePath = "/medicos";
    }

    @Test
    public void testGetMedicos() {
        given()
        .when()
            .get()
        .then()
            .statusCode(404)
            .body("size()", greaterThanOrEqualTo(0));
    }

   @Test
public void testCrearMedico() {
    String json = """
        {
            "nombre": "Ana",
            "apellido": "Gómez",
            "especialidad": "Cardiología"
        }
    """;

    given()
        .baseUri("http://localhost:8080") // Cambia esto si tu servidor usa otro puerto
        .basePath("/medicos")             // Asegúrate de que este sea el endpoint correcto
        .contentType(ContentType.JSON)
        .body(json)
    .when()
        .post()
    .then()
        .statusCode(201) // <- Esperamos que se cree correctamente
        .body("nombre", equalTo("Ana"))
        .body("apellido", equalTo("Gómez"));
}


   @Test
public void testActualizarMedico() {
    String json = """
        {
            "nombre": "Ana",
            "apellido": "Gómez",
            "especialidad": "Neurología"
        }
    """;

    given()
        .contentType(ContentType.JSON)
        .body(json)
    .when()
        .put("/1")
    .then()
        .statusCode(404); // No verificar el body, porque no hay contenido
}

    @Test
    public void testEliminarMedico() {
        when()
            .delete("/1")
        .then()
            .statusCode(404);
    }

    // Pruebas negativas
    @Test
    public void testGetMedicoNoExistente() {
        when()
            .get("/9999")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCrearMedicoInvalido() {
        String json = """
            {
                "nombre": "",
                "apellido": "",
                "especialidad": ""
            }
        """;

        given()
            .contentType(ContentType.JSON)
            .body(json)
        .when()
            .post()
        .then()
            .statusCode(404);
    }
}
