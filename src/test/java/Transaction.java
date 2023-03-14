import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import static io.restassured.RestAssured.given;
@Getter
@Setter

    public class Transaction extends Setup{
    public Transaction() throws IOException {
        initConfig();
    }
    private String message;

    public JsonPath depositToAgent(String from_account, String to_account, int amount) {
        RestAssured.baseURI = prop.getProperty("BaseURL");
        TransactionModel transactionModel=new TransactionModel(from_account, to_account, amount);


        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit");

                 return res.jsonPath();
    }
    public JsonPath depositToCustomer(String from_account, String to_account, int amount) {

        RestAssured.baseURI = prop.getProperty("BaseURL");
        TransactionModel transactionModel=new TransactionModel(from_account, to_account, amount);
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/deposit");

                return res.jsonPath();
    }

    public JsonPath checkBalanceOfCustomer(String customerPhoneNumber){
        RestAssured.baseURI = prop.getProperty("BaseURL");
                Response res =
                        given()
                                .contentType("application/json")
                                .header("Authorization", prop.getProperty("token"))
                                .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                                .when()
                                .get("/transaction/balance/"+customerPhoneNumber);

                return res.jsonPath();
    }
    public JsonPath checkstatementByTrnxId(String TrnxId){
        RestAssured.baseURI = prop.getProperty("BaseURL");
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .when()
                        .get("transaction/statement/"+TrnxId);

                return res.jsonPath();
    }

    public JsonPath withdrawByCustomer(String from_account, String to_account, int amount) {
        RestAssured.baseURI = prop.getProperty("BaseURL");
        TransactionModel transactionModel=new TransactionModel(from_account, to_account, amount);
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/withdraw");

                return res.jsonPath();
    }

    public JsonPath sendMoney(String from_account, String to_account, int amount) {
        RestAssured.baseURI = prop.getProperty("BaseURL");
        TransactionModel transactionModel=new TransactionModel(from_account, to_account, amount);
        Response res =
                given()
                        .contentType("application/json")
                        .header("Authorization", prop.getProperty("token"))
                        .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                        .body(transactionModel)
                        .when()
                        .post("/transaction/sendmoney");

                return res.jsonPath();
    }
    public JsonPath checkCustomerStatement(String customerPhoneNumber){
        RestAssured.baseURI = prop.getProperty("BaseURL");
        Response res = given()
                            .contentType("application/json")
                            .header("Authorization", prop.getProperty("token"))
                            .header("X-AUTH-SECRET-KEY", "ROADTOSDET")
                            .when()
                            .get("transaction/statement/"+customerPhoneNumber);

                    return res.jsonPath();
    }

}
