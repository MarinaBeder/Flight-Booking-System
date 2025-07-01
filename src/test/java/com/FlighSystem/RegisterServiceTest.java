package com.FlighSystem;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
public class RegisterServiceTest {

	
	
/*	
	@Test
    public void testCreateUser() {
        String requestBody = """
            {
              "fullName": "Admin2 Admin2",
              "email": "admin9@example.com",
              "password": "123456",
              "address": {
                "streetNo": "12",
                "neighbourhood": "Garden City",
                "city": "Cairo",
                "country": "Egypt"
              },
              "age": 25,
              "gender": "MALE",
              "username": "admin2",
              "role": "ADMIN"
            }
            """;

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/api/v1/user/register")
        .then()
            .statusCode(201); // Created
    }
*/

    

    @Test
    public void testCreateUserInvalidEmail() {
        String requestBody = """
            {
              "fullName": "Admin2 Admin2",
              "email": "admin@",
              "password": "123456",
              "address": {
                "streetNo": "12",
                "neighbourhood": "Garden City",
                "city": "Cairo",
                "country": "Egypt"
              },
              "age": 25,
              "gender": "MALE",
              "username": "admin2",
              "role": "ADMIN"
            }
            """;

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/api/v1/user/register")
        .then()
           // .log().body()  // ✅ Show the body to debug
            .statusCode(400)
            .body("email", equalTo("Email must be a valid email address")); // ✅ If your error JSON is { "email": ... }
    }


    @Test
    public void testCreateUserInvalidAge() {
        String requestBody = """
            {
              "fullName": "Admin2 Admin2",
              "email": "admin2invalidage@example.com",
              "password": "123456",
              "address": {
                "streetNo": "12",
                "neighbourhood": "Garden City",
                "city": "Cairo",
                "country": "Egypt"
              },
              "age": -5,
              "gender": "MALE",
              "username": "admin2",
              "role": "ADMIN"
            }
            """;

        given()
            .contentType("application/json")
            .body(requestBody)
        .when()
            .post("/api/v1/user/register")
        .then()
            .statusCode(400)
            .body("age", equalTo("Age must be positive"));
    }

 /*
    @Test
	public void testCreateUserDuplicateEmail() {
	    String requestBody = """
	        {
	          "fullName": "Admin2 Admin2",
	          "email": "admin5@example.com",
	          "password": "123456",
	          "address": {
	            "streetNo": "12",
	            "neighbourhood": "Garden City",
	            "city": "Cairo",
	            "country": "Egypt"
	          },
	          "age": 25,
	          "gender": "MALE",
	          "username": "admin2",
	          "role": "ADMIN"
	        }
	        """;

	    given()
	        .contentType("application/json")
	        .body(requestBody)
	    .when()
	        .post("/api/v1/user/register")
	    .then()
	        .statusCode(409)
	        .body("message", equalTo("Email is already Exist"));
	}
    */
}
