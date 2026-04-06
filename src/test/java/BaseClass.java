import io.github.bonigarcia.wdm.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;


public class BaseClass {
  static   WebDriver driver;
public Logger logger ;
Properties prop ;
    @BeforeClass
    @Parameters({"os","browser"})
    public void setUp(String os , String browser) throws IOException {
        logger = LogManager.getLogger(this.getClass());
        FileInputStream fileInputStream = new FileInputStream("config.properties");
        prop = new Properties();
        prop.load(fileInputStream);
        if(prop.getProperty("execution_env").equals("remote")){
            DesiredCapabilities capabilities = new DesiredCapabilities();

            if (os.equalsIgnoreCase("windows")){
                capabilities.setPlatform(Platform.WIN11);
            }else if (os.equalsIgnoreCase("mac")){
                capabilities.setPlatform(Platform.MAC);
            }else if (os.equalsIgnoreCase("linux")){
                capabilities.setPlatform(Platform.LINUX);
            }

            switch (browser.toLowerCase()){
                case "chrome": capabilities.setBrowserName("chrome");break;
                case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
                case "firefox": capabilities.setBrowserName("firefox");break;
                default: System.out.println("invalid driver"); return;

          /*  capabilities.setPlatform(Platform.WIN11);
            capabilities.setBrowserName("chrome");*/
        }
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
        }
        if(prop.getProperty("execution_env").equals("local")) {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;
                case "edge":
                    driver = new EdgeDriver();
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
                    break;
                default:
                    System.out.println("invalid driver");
                    return;
            }
        }



       // WebDriverManager.chromedriver().setup();



        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(prop.getProperty("url"));
        driver.manage().deleteAllCookies();
    }


    @AfterClass
    public void tearDown() {

        logger.info("***********Closing the browser ***************");
        driver.quit();
    }

    public String captureScreenShot(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver ;
       File sourceFile =  ts.getScreenshotAs(OutputType.FILE) ;
       String targetFile = System.getProperty("user.dir")+".\\screenShots\\" + tname +"_"+timeStamp+".png" ;
       File destination = new File(targetFile);
        FileUtils.copyFile(sourceFile,destination);
        //or
//sourceFile.renameTo(destination);

return  targetFile ;
    }
}


