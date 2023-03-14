import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class TransactionTestRunner extends Setup{

    User user;

    @BeforeTest

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


    @Test(priority = 1, description = "Deposit to Agent From system with invalid account")
    public void depositToAgentWithInvalidAccount() throws IOException {
        Transaction transaction=new Transaction();
        String agentPhoneNumber=prop.getProperty("agent_phone_number");
        JsonPath jsonResponse =transaction.depositToAgent("SYSTEM","wrongAgentPhoneNumber",5000);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Account does not exist"));

    }
    @Test(priority = 2, description = "Deposit to Agent From system")
    public void depositToAgent() throws IOException {
        Transaction transaction=new Transaction();
        String agentPhoneNumber=prop.getProperty("agent_phone_number");
        JsonPath jsonResponse =transaction.depositToAgent("SYSTEM",agentPhoneNumber,5000);
        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Deposit successful"));

    }


    @Test(priority = 3, description = "Deposit to Customer From Agent with invalid account number")
    public void depositToCustomerWithInvalidAccount() throws IOException {
        Transaction transaction=new Transaction();
        String agentPhoneNumber=prop.getProperty("agent_phone_number");
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.depositToCustomer(agentPhoneNumber,"wrongCustomerPhoneNumber",2000);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Account does not exist"));

    }

    @Test(priority = 4, description = "Deposit to Customer From Agent")
    public void depositToCustomer() throws IOException, ConfigurationException {
        Transaction transaction=new Transaction();
        String agentPhoneNumber=prop.getProperty("agent_phone_number");
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.depositToCustomer(agentPhoneNumber,customerPhoneNumber,2000);

       Utils.setCollectionVariable("customer_TrnxId",jsonResponse.get("trnxId"));

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Deposit successful"));
    }

    @Test(priority = 5, description = "check customer balance")
    public void checkBalanceOfCustomer() throws IOException {
        Transaction transaction=new Transaction();
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.checkBalanceOfCustomer(customerPhoneNumber);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User balance"));

    }

    @Test(priority = 6, description = "check customer balance with invalid account")
    public void checkBalanceOfCustomerwithInvalidAccount() throws IOException {
        Transaction transaction=new Transaction();
        JsonPath jsonResponse =transaction.checkBalanceOfCustomer("invalidCustomerAccount");

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User not found"));

    }

    @Test(priority = 7, description = "Check statement by invalid transaction Id")
    public void checkstatementByWitnInvalidTrnxId() throws IOException {
        Transaction transaction=new Transaction();
        String TrnxId=prop.getProperty("agent_phone_number");
        JsonPath jsonResponse =transaction.checkstatementByTrnxId("wrongTrnxId");

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User not found"));
    }
    @Test(priority = 8, description = "Withdraw money by customer")
    public void withdrawByCustomer() throws IOException {
        Transaction transaction=new Transaction();
        String agentPhoneNumber=prop.getProperty("agent_phone_number");
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.withdrawByCustomer(customerPhoneNumber,agentPhoneNumber,1000);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Withdraw successful"));

        int currentBalance = jsonResponse.getInt("currentBalance");
        System.out.println(currentBalance);
        Assert.assertEquals(990, currentBalance);

    }

    @Test(priority = 9, description = "Withdraw money by customer with invalid creds")
    public void withdrawByinvalidCreds() throws IOException {
        Transaction transaction=new Transaction();
        String agentPhoneNumber=prop.getProperty("agent_phone_number");
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.withdrawByCustomer("wrongCustomerPhoneNumber",agentPhoneNumber,1000);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Account does not exist"));
    }

    @Test(priority = 10, description = "Send money to another customer")
    public void sendMoney() throws IOException {
        Transaction transaction=new Transaction();
        String customer2PhoneNumber=prop.getProperty("customer_2_phone_number");
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.sendMoney(customerPhoneNumber,customer2PhoneNumber,500);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Send money successful"));

        int currentBalance = jsonResponse.getInt("currentBalance");
        System.out.println(currentBalance);
        Assert.assertEquals(485, currentBalance);
    }

    @Test(priority = 11, description = "Send money to another customer with invalid creds")
    public void sendMoneyWithInvalidCreds() throws IOException {
        Transaction transaction=new Transaction();
        String customer2PhoneNumber=prop.getProperty("customer_2_phone_number");
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.sendMoney("wrongCustomerPhoneNumber",customer2PhoneNumber,500);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("From/To Account does not exist"));

    }

    @Test(priority = 12, description = "Check customer statement")
    public void checkCustomerStatement() throws IOException {
        Transaction transaction=new Transaction();
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.checkCustomerStatement(customerPhoneNumber);

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("Transaction list"));

    }

    @Test(priority = 13, description = "Check customer statement with invalid creds")
    public void checkCustomerStatementWithInvalidAccount() throws IOException {
        Transaction transaction=new Transaction();
        String customerPhoneNumber=prop.getProperty("customer_phone_number");
        JsonPath jsonResponse =transaction.checkCustomerStatement("wrongCustomerPhoneNumber");

        String message=jsonResponse.get("message");
        System.out.println(message);
        System.out.println(jsonResponse.get().toString());
        Assert.assertTrue(message.contains("User not found"));

    }
}
