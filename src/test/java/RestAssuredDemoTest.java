import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.AuthReq;
import model.MessageReq;
import org.junit.Test;


public class RestAssuredDemoTest {
    RequestSpecification requestSpec = new RequestSpecBuilder()
            .log(LogDetail.ALL)
            .addFilter(new AllureRestAssured())
            .setBaseUri("http://localhost")
            .build();

    @Test
    public void apiTest() {
        //Получение токена
        Response authenticate = RestAssured
                .given().spec(requestSpec)
                .body(new AuthReq("admin", "admin"))
                .when()
                .header("Content-type", "application/json;charset=UTF-8")
                .post("/api/v1/authenticate")
                .then().log().all()
                .statusCode(200).extract().response();
        JsonPath jp = authenticate.jsonPath();
        String token = jp.get("token");

        //Получение списка пользователей
        Response usersList = RestAssured
                .given().spec(requestSpec)
                .when()
                .header("Content-type", "application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/user")
                .then().log().all()
                .statusCode(200).extract().response();
        JsonPath users = usersList.jsonPath();
        Integer recipientId = users.get("[0].id");

        //Добавление сообщения
        Response message = RestAssured
                .given().spec(requestSpec)
                .when()
                .body(new MessageReq("Hello RestAssured", recipientId))
                .header("Content-type", "application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .post("/api/v1/message")
                .then().log().all()
                .statusCode(200).extract().response();
        JsonPath messageResp = message.jsonPath();
        Integer messageId = messageResp.get("id");

        //Получение сообщения по Id
        Response messageOne = RestAssured
                .given().spec(requestSpec)
                .when()
                .header("Content-type", "application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .get("/api/v1/message/" + messageId)
                .then().log().all()
                .statusCode(200).extract().response();
        JsonPath jpMessOne = messageOne.jsonPath();

        //Удаление сообщения
        RestAssured
                .given().spec(requestSpec)
                .when()
                .header("Content-type", "application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .delete("/api/v1/message/" + messageId)
                .then().log().all()
                .statusCode(200).extract().response();

        //Удаление сообщения NotFound
        RestAssured
                .given().spec(requestSpec)
                .when()
                .header("Content-type", "application/json;charset=UTF-8")
                .header("Authorization", "Bearer " + token)
                .delete("/api/v1/message/" + messageId)
                .then().log().all()
                .statusCode(404).extract().response();
    }

}
