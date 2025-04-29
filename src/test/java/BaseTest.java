import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import static com.codeborne.selenide.Selenide.open;
import static ge.tbc.testautomation.data.Constants.SWOOP_BASE_URL;

public class BaseTest {

    @BeforeMethod
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            Configuration.browser = "chrome";
        } else {
            WebDriverManager.firefoxdriver().setup();
            Configuration.browser = "firefox";
        }

        Configuration.timeout = 3000;
        Configuration.pageLoadTimeout = 100000;
        Configuration.screenshots = true;
        Configuration.browserSize = "1920x1080"; // Still sets window size generally
        open(SWOOP_BASE_URL);
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
