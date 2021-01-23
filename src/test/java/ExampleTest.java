import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class ExampleTest {
    protected static WebDriver driver;
    protected static Logger logger = LogManager.getLogger(ExampleTest.class);
    protected String browser;


    @Before
    public void SetUp() {
        if (System.getProperty("Browser") == null) {
            browser = "";
        } else {
            browser = System.getProperty("Browser").toLowerCase();
        }

        switch (browser) {
            case ("\'opera\'"):
                WebDriverManager.operadriver().setup();
                driver = new OperaDriver();
                break;

            case ("\'firefox\'"):
                WebDriverManager.firefoxdriver().setup();
                break;

            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        logger.info("Browser driver open");
    }

    @Test
    public void yandexTest() {
        driver.manage().window().maximize();
        logger.debug("Browser window is open in \"no kiosk\" mode");
        driver.get("https://yandex.ru");
        logger.debug("Yandex page successfully opened");
        String titlePage = driver.getTitle();
        assertEquals(titlePage, "Яндекс");
        logger.info("Title validation test passed");
    }

    @Test
    public void tele2Test() {
        driver.get("https://msk.tele2.ru/shop/number");
        logger.debug("Tele2 page successfully opened");
        new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.id("searchNumber")));
        driver.findElement(By.id("searchNumber")).clear();
        driver.findElement(By.id("searchNumber")).sendKeys("97");
        driver.findElement(By.id("searchNumber")).sendKeys(Keys.ENTER);
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Показать еще')]")));
        logger.info("Expected numbers are displayed on the page");
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser driver closed");
        }
    }
}
