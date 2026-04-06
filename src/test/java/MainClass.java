import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pageObjectModel.LogInClass;
import pageObjectModel.MainPage;

public class MainClass extends BaseClass {

    @Test
    void verifyUserDetails() {

        logger.info("**************Staring the first Test case***********");
        LogInClass logIn = new LogInClass(driver);

        logger.info("*****************loggin to the application*****************");
        logIn.setUserName("standard_user");
        logIn.setPassword("secret_sauce");
        logIn.clickLoginButton();
        String logInDetails = logIn.loginStatus();
        Assert.assertEquals(logInDetails, "Swag Labs");
    }

    @Test(dependsOnMethods = {"verifyUserDetails"})
    void verifyCartDetails() {
        logger.info("**********Adding item to the cart***************");
        MainPage mainPage = new MainPage(driver);
        mainPage.addBag();
        mainPage.clickAddToCart();

    }


}
