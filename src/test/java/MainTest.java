import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

public class MainTest {


    @Test
    public void test1(){
        RestAssured.given()
                .filter(new AllureRestAssured())
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then().statusCode(200);
//                .extract().response();
    }

    @Test
    public void planetsCheck() {
//        Response response =
                RestAssured.given().log().all()
                .filter(new AllureRestAssured())
                .when()
                .get("https://reqres.in/api/users?page=2").then().log().all()
                .statusCode(200);
//                .extract().response();
//        Assert.assertEquals(response.statusCode(), 200);
    }
}
