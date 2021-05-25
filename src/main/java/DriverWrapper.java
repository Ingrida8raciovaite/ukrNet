import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class DriverWrapper {
    WebDriver driver;
    String url = "https://accounts.ukr.net/login";

    public DriverWrapper(){
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
        this.driver = new ChromeDriver();
    }

    public void init(){
        this.driver.manage().window().maximize();
        System.out.println("The profile setup process is completed");
        this.driver.get(url);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        System.out.println("The app setup process is completed");
    }

    public WebDriver getDriver(){return driver;}

    public void close(){ this.driver.close();}
}
