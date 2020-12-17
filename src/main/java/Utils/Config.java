package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static Config config = null;
	private Properties prop = null;
	
	//to get Single Instance of a class
	public static Config getInstance(){
		if(config == null){
			config = new Config();
		}
		return config;
	}
	//Added new code comment
	//private constructor for loading properties
	private Config(){
		Properties prop = new Properties();
		try {
			InputStream in = getClass().getClassLoader().getResourceAsStream("Config.properties");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.prop = prop;
	}
	
	//get value from Config properties
	public String getProperty(String key){
		String value = "";
		value = prop.getProperty(key);
		return value;
	}
	
}
