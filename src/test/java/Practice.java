import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Practice {
    WebDriver driver;

    @BeforeMethod
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.setHeadless(true);
//        options.addArguments("--window-size=1920,1080");

//        driver = new ChromeDriver(options);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown() {
//        driver.quit();
    }

    @Test(enabled = false)
    public void clickInFrame() {
        driver.get("https://www.w3schools.com/html/html_iframe.asp");

        driver.switchTo().frame(driver.findElement(By.cssSelector("[title='W3Schools HTML Tutorial']")));

        driver.findElements(By.xpath("//*[text()='Next ❯']")).get(0).click();

        driver.switchTo().defaultContent();

        driver.findElements(By.xpath("//*[text()='Next ❯']")).get(0).click();

    }

    @Test
    public void switchTabs() throws IOException {
        driver.get("http://www.google.com");
        takeScreenshot("google");

//        driver.findElement(By.tagName("body")).sendKeys(Keys.chord(Keys.CONTROL,"t"));

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.open()");

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        driver.get("http://www.yahoo.com");
        takeScreenshot("yahoo");

        driver.switchTo().window(tabs.get(0));
        driver.get("http://www.yahoo.com");

        driver.switchTo().window(tabs.get(1));
        driver.close();
        driver.switchTo().window(tabs.get(0));
        driver.get("http://www.google.com");
    }

    @Test
    public void switchTabsRobot() throws AWTException {
        driver.get("http://www.google.com");

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_T);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_T);
    }

    @Test
    public void scrollPage() throws InterruptedException {
        driver.get("https://www.google.com/search?q=java");

        Thread.sleep(3000);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
//      jse.executeScript("window.scrollBy(0,500)");

        jse.executeScript("arguments[0].scrollIntoView()",driver.findElement(By.cssSelector("table.AaVjTc")));
    }

    public void takeScreenshot(String name) throws IOException {
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file,new File("src/results/"+name+".png"));
    }

    @Test
    public void getDateTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));

        System.out.println(getEmail(10));
    }

    public String getEmail(int num){
        String[] chars = {"q","w","e","r","t"};
        Random r = new Random();
        String email = "";

        for (int i = 1; i <= num;i++){
            email = email+chars[r.nextInt(chars.length)];
        }

        return email+"@testmail.com";
    }

}