import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import java.io.IOException;
import static io.restassured.RestAssured.given;
@Getter
@Setter

    public class User extends Setup{
    public User() throws IOException {
        initConfig();
    }
    public JsonPath callLoginAPI(String email, String password) {
        UserLoginModel loginModel=new UserLoginModel(email, password);
        RestAssured.baseURI = prop.getProperty("BaseURL");
        Response res =
                    given()
                        .contentType("application/json")
                        .body(loginModel)
                        .when()
                        .post("/user/login");

                return res.jsonPath();

    }

    public JsonPath getUserList() {
        RestAssured.baseURI = prop.getProperty("BaseURL");
        Response res =
                    given()
                            .contentType("application/json")
                            .header("Authorization", prop.getProperty("token"))
                            .when().get("/user/list");

                    return res.jsonPath();

    }
    public JsonPath getUserListForBlankAuthorizationToken() {
        RestAssured.baseURI = prop.getProperty("BaseURL");
        Response res = given()
                            .contentType("application/json")
                            .header("Authorization", "")
                            .when().get("/user/list");
                return res.jsonPath();

//        JsonPath response = res.jsonPath();
//        System.out.println(response.get("error.message").toString());
//        Assert.assertTrue(response.get("error.message").toString().contains("Token expired!"));
    }

        public JsonPath createUser() {
        Utils utils=new Utils();

        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String nid = "100200300";
        RestAssured.baseURI = prop.getProperty("BaseURL");
        CreateUserModel createUserModel = new CreateUserModel(name, email, password,utils.generatePhoneNumber(), nid, "Customer");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(createUserModel)
                        .when().post("/user/create");

                 return res.jsonPath();

    }

    public JsonPath alreadyCreatedUser() {
        RestAssured.baseURI = prop.getProperty("BaseURL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(" {\n" +
                        "        \"name\": \"Susan Stoltenberg\",\n" +
                        "        \"email\": \"Madonna0@yahoo.com\",\n" +
                        "        \"password\": \"fTTTI_KdnKnjv5R\",\n" +
                        "        \"phone_number\": \"01713648066\",\n" +
                        "        \"nid\": \"6413648066\",\n" +
                        "        \"role\": \"Customer\"\n" +
                        "}")
                         .when().post("/user/create");

                return res.jsonPath();

    }

    public JsonPath createAgent() {
        Utils utils=new Utils();
        Faker faker = new Faker();
        String name = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String nid = "200200300";

        RestAssured.baseURI = prop.getProperty("BaseURL");
        CreateUserModel createUserModel = new CreateUserModel(name, email, password,utils.generatePhoneNumber(), nid, "Agent");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(createUserModel)
                        .when().post("/user/create");

                return res.jsonPath();

    }

    public JsonPath searchUserByPhoneNumber(String customerPhoneNumber) {
        RestAssured.baseURI = prop.getProperty("BaseURL");

        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when().get("user/search/Phonenumber/" + customerPhoneNumber);

                return res.jsonPath();
    }

 }
