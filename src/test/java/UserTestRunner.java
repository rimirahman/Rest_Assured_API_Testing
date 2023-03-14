import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class UserTestRunner extends Setup {
    User user;

    @Test(priority = 1, description = "Incorrect email provided for login")
    public void incorrectEmail() throws IOException {
        user=new User();
        JsonPath jsonResponse =user.callLoginAPI("wrongEmail.net","1234");

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User not found"));
    }

    @Test(priority = 2, description = "Incorrect password provided for login")
    public void incorrectPassword() throws IOException {
        user=new User();
        JsonPath jsonResponse =user.callLoginAPI("salman@roadtocareer.net","wrongPassword");

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Password incorrect"));
    }
    @Test(priority = 3, description = "Calling the login API by giving valid credentials")
    public void callingLoginAPI() throws ConfigurationException, IOException {
        user=new User();
        JsonPath jsonResponse =user.callLoginAPI("salman@roadtocareer.net","1234");
        String token = jsonResponse.get("token");
        String message=jsonResponse.get("message");

        Utils.setCollectionVariable("token",token);

        System.out.println(token);
        System.out.println(message);
        Assert.assertTrue(message.contains("Login successfully"));
    }

    @Test(priority = 4, description = " Get user list will be extracted by giving proper authorization")
    public void getUserList() throws IOException {
        user = new User();
        JsonPath jsonResponse =user.getUserList();
        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User list"));
    }
    @Test(priority = 5, description = "If user does not input token will show error message")
    public void getUserListForBlankAuthorizationToken() throws IOException {
        user = new User();
        JsonPath jsonResponse = user.getUserListForBlankAuthorizationToken();

        System.out.println(jsonResponse.get("error.message").toString());
        Assert.assertTrue(jsonResponse.get("error.message").toString().contains("No Token Found!"));

    }

    @Test(priority = 6, description = "Already Created User")
    public void alreadyCreatedUser() throws IOException {
        user = new User();
        JsonPath jsonResponse =user.alreadyCreatedUser();
        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User already exists"));
    }

    @Test(priority = 7, description = "New Customer created")
    public void createCustomer() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonResponse =user.createUser();
        String message=jsonResponse.get("message");

        Utils.setCollectionVariable("customer_id",jsonResponse.get("user.id").toString());
        Utils.setCollectionVariable("customer_name",jsonResponse.get("user.name"));
        Utils.setCollectionVariable("customer_email",jsonResponse.get("user.email"));
        Utils.setCollectionVariable("customer_phone_number",jsonResponse.get("user.phone_number"));
        Utils.setCollectionVariable("customer_nid",jsonResponse.get("user.nid"));


        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User created"));
    }

    @Test(priority =8, description = "Already agent created")
    public void alreadyCreatedAgent() throws IOException {
        user = new User();
        JsonPath jsonResponse =user.alreadyCreatedUser();

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User already exists"));
    }
    @Test(priority = 9, description = "New Agent created")
    public void createAgent() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonResponse =user.createAgent();

        Utils.setCollectionVariable("agent_id",jsonResponse.get("user.id").toString());
        Utils.setCollectionVariable("agent_name",jsonResponse.get("user.name"));
        Utils.setCollectionVariable("agent_email",jsonResponse.get("user.email"));
        Utils.setCollectionVariable("agent_phone_number",jsonResponse.get("user.phone_number"));
        Utils.setCollectionVariable("agent_nid",jsonResponse.get("user.nid"));

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User created"));
    }

    @Test(priority = 10, description = "Search customer by invalid phone number")
    public void searchUserInvalidPhoneNumber() throws IOException {
        user = new User();
        JsonPath jsonResponse =user.searchUserByPhoneNumber("01412123456");
        System.out.println(jsonResponse.get().toString());
        String message=jsonResponse.get("message");
        Assert.assertTrue(message.contains("User not found"));
    }
    @Test(priority = 11, description = "Search customer by phone number")
    public void searchUserByPhoneNumber() throws IOException {
        user = new User();
        JsonPath jsonResponse =user.searchUserByPhoneNumber(prop.getProperty("customer_phone_number"));

        System.out.println(jsonResponse.get().toString());
        String message=jsonResponse.get("message");
        Assert.assertTrue(message.contains("User found"));
    }

    @Test(priority = 12, description = "New Customer created")
    public void createCustomer2() throws ConfigurationException, IOException {
        user = new User();
        JsonPath jsonResponse =user.createUser();
        String message=jsonResponse.get("message");

        Utils.setCollectionVariable("customer_2_id", jsonResponse.get("user.id").toString());
        Utils.setCollectionVariable("customer_2_name", jsonResponse.get("user.name"));
        Utils.setCollectionVariable("customer_2_email", jsonResponse.get("user.email"));
        Utils.setCollectionVariable("customer_2_phone_number", jsonResponse.get("user.phone_number"));

        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User created"));
    }

}
