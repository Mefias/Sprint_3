package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class Courier {
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String login;
    private String password;
    private String firstName;
    public Courier() {
    }
    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
    public CourierId getId() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
        CourierId cId = given()
                .header("Content-type", "application/json")
                .and()
                .body(this)
                .post("/api/v1/courier/login").as(CourierId.class);
        return cId;
    }
    public void delete() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
        CourierId cId = getId();
        if (cId.id == null)
            return;
        Response res = given()
                .header("Content-type", "application/json")
                .and()
                .body(cId)
                .delete("/api/v1/courier/" + cId.id);
   }
    public void create() {
        RestAssured.baseURI= "https://qa-scooter.praktikum-services.ru";
        Response res = given()
                .header("Content-type", "application/json")
                .and()
                .body(this)
                .post("/api/v1/courier/");
    }
    public Courier clone() {
        return new Courier(login, password, firstName);
    }
}
