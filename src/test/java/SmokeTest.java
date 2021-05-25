import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.stream.Collectors;

public class SmokeTest {
    final DriverWrapper driverWrapper = new DriverWrapper();
    CheckIfMessageCame link = new CheckIfMessageCame();
    Actions action = new Actions(driverWrapper.getDriver());

    //Pre-condition
    @BeforeSuite
    public void setUp(){
        driverWrapper.init();
    }

    @Test (description = "Login with valid data")
    @Parameters ({"email", "password"})
    public void login(@Optional("ingrida.test@ukr.net") String email, @Optional("qT2Kv;cFv..pcLj") String password){
        Login lg = new Login();
        lg.emailInput = driverWrapper.getDriver().findElement(By.xpath("//input[@name='login']"));
        lg.passwordInput = driverWrapper.getDriver().findElement(By.xpath("//input[@name='password']"));
        lg.loginButton = driverWrapper.getDriver().findElement(By.xpath("//button[@type = 'submit']"));
        lg.login(email, password);
        System.out.println("The login process on mail.ukr.net is completed");
        driverWrapper.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @Test(dependsOnMethods = "login",
            description = "Sending the message with valid data")
    @Parameters({"to", "title", "contentLetter"})
    public void sendMessage(@Optional("ingrida.s.test@gmail.com") String to,
                            @Optional("TestTitle") String someTitle,
                            @Optional("Here is an example message") String content) throws InterruptedException {
        SendMessage message = new SendMessage();
        //open the form to send a message
        message.openingSendMessageForm = (new WebDriverWait(driverWrapper.getDriver(), Duration.ofSeconds(30))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='button primary compose']"))));
        message.openForm();
        //find elements for filling data
        message.forWhom = driverWrapper.getDriver().findElement(By.xpath(".//*[@id='screens']//*//*[@name = 'toFieldInput']"));
        message.title = driverWrapper.getDriver().findElement(By.xpath(".//*[@id='screens']//*//*[@name = 'subject']"));
        //open iframe and write message
        driverWrapper.getDriver().switchTo().frame("mce_0_ifr");
        message.message = driverWrapper.getDriver().findElement(By.xpath("//body[@class='mce-content-body ']"));
        message.message.sendKeys(content);
        driverWrapper.getDriver().switchTo().parentFrame();

        message.send = driverWrapper.getDriver().findElement(By.xpath("//button[@class='button primary send']"));

        //fill data
        message.fillData(to, someTitle);
        //send message
        message.send();
        driverWrapper.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        //get the message link
        link.messageLink = (new WebDriverWait(driverWrapper.getDriver(), Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sendmsg__ads-ready']/a[@href]"))));
        link.messageLinkText = link.messageLink.getAttribute("href") + "/f10001";
        //open the message
        action.click(link.messageLink).build().perform();
        driverWrapper.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
    }

    @Test(dependsOnMethods = "sendMessage",
            description = "check content of the message")
    @Parameters({"email","to","title","contentLetter"})
    public void content(@Optional("ingrida.test@ukr.net") String from,
                        @Optional("ingrida.s.test@gmail.com") String to,
                        @Optional("TestTitle") String title,
                        @Optional("Here is an example message") String content){
        driverWrapper.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
        link.from = driverWrapper.getDriver().findElement(By.xpath("(//div[@class='readmsg__head-contacts'])[1]/a/em"))
                .getText();
        link.to = driverWrapper.getDriver().findElement(By.xpath("//div[@class='readmsg__head-contacts']/div/a/em"))
                .getText();
        link.titleSentMessage = driverWrapper.getDriver().findElement(By.xpath("//div[@class='screen__content']/h3"))
                .getText();
        link.letter = driverWrapper.getDriver().findElement(By.xpath("//span[@class='xfmc1']"))
                .getText();
        link.checkContent(from, to, title, content);
    }

    @Test (dependsOnMethods = "content",
            description = "Check if the message is presented on the sentMessage list")
    public void sentMessagesList(){
        link.sentMessagesPage = driverWrapper.getDriver().findElement(By.xpath("//*[@id='10001']"));
        link.openSentMessagesPage();
        driverWrapper.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
        link.links = driverWrapper.getDriver().findElements
                (By.cssSelector("#msglist a.msglist__row_href"))
                .stream()
                .map(e -> e.getAttribute("href"))
                .collect(Collectors.toList());
        link.findTheMessage();
    }

    //Post-condition
    @AfterSuite
    public void tearDown(){
        driverWrapper.close();
    }


}
