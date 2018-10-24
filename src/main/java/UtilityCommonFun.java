package Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.sun.javafx.PlatformUtil;

  public class UtilityCommonFun {
	public static WebDriver driver;
	private By by;
	public static Properties prop;
	String propFilePath;
	
	static String browserName,varDriverPath,appUrl;
	Properties testDataProp;
	
	// constructor to access the properties file
	public UtilityCommonFun() throws FileNotFoundException
	{
		try 
		{
			prop=new Properties();
			propFilePath=System.getProperty("user.dir")+"\\src\\test\\java\\config\\config.properties";
			System.out.println(propFilePath);
			FileInputStream fileinputstream=new FileInputStream(propFilePath);
			prop.load(fileinputstream);
		}catch(Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		varDriverPath = prop.getProperty("driverPath");
		setDriverPath(varDriverPath);
		 
	}
	
	 private static void setDriverPath(String driverURL) 
	 {
		// Create prefs map to store all preferences 
		 Map<String, Object> prefs = new HashMap<String, Object>();
		
		//Put this into prefs map to switch off browser notification
		 prefs.put("profile.default_content_setting_values.notifications", 2);
		 prefs.put("chrome.switches", 
		"--disable-extensions --disable-extensions-file-access-check"
		+ "--disable-extensions-http-throttling --disable-infobars --enable-automation --start-maximized");
		 prefs.put("credentials_enable_service", false);
		 prefs.put("profile.password_manager_enabled", false);
		 
		//Create chrome options to set this prefs
		 ChromeOptions options = new ChromeOptions();
		 options.setExperimentalOption("prefs", prefs);
		 options.addArguments("--disable-save-password");
		 
        if (PlatformUtil.isMac()) {
        	//changes- to get the driver path using relative path
        	String driverPath=System.getProperty("user.dir")+"\\src\\test\\java\\"+driverURL;
			System.setProperty("webdriver.chrome.driver", driverPath);
             
        }
        if (PlatformUtil.isWindows()) {
        	//Changes- to get the driver path using relative path
        	String driverPath=System.getProperty("user.dir")+"\\src\\test\\java\\"+driverURL;
			System.setProperty("webdriver.chrome.driver", driverPath);
         //   System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        }
        if (PlatformUtil.isLinux()) {
        	//Changes- to get the driver path using relative path
        	String driverPath=System.getProperty("user.dir")+"\\src\\test\\java\\"+driverURL;
			System.setProperty("webdriver.chrome.driver", driverPath);
           // System.setProperty("webdriver.chrome.driver", "chromedriver_linux");
        }
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        appUrl = prop.getProperty("url");
        launchApp(appUrl);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.MINUTES);
       
	 }
	 
	

	protected static void waitFor(int durationInMilliSeconds) 
	 {
        try {
            Thread.sleep(durationInMilliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
	 }
	 
	//Launch application
	public static void launchApp(String appUrl)
	{ 	
		 
		  
		driver.get(appUrl);
		 
	} 
	
	 protected boolean isElementPresent(By by) {
	        try {
	            driver.findElement(by);
	            return true;
	        } catch (NoSuchElementException e) {
	            return false;
	        }
	    }
	
	 
}
