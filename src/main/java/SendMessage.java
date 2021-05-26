import org.openqa.selenium.WebElement;

public class SendMessage {

     WebElement openingSendMessageForm;
     WebElement forWhom;
     WebElement title;
     WebElement message;
     WebElement send;

     public void openForm(){
         openingSendMessageForm.click();
     }

     public void fillData(String email, String someTitle){
         forWhom.sendKeys(email);
         title.sendKeys(someTitle);
     }

     public void send(){
         send.click();
     }
}

