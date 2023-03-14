import lombok.Getter;
import lombok.Setter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
@Getter
@Setter
public class Utils {
    public static void setCollectionVariable(String key,String value) throws ConfigurationException {
        PropertiesConfiguration propertiesConfiguration=new PropertiesConfiguration("./src/test/resources/config.properties");
        propertiesConfiguration.setProperty(key,value);
        propertiesConfiguration.save();
    }


    public String generatePhoneNumber(){
        String prefix="01700";
        int min=100000;
        int max=999999;
        int randomNumber=(int) Math.round(Math.random()*(max-min)+min);
        return prefix+randomNumber ;
    }
}
