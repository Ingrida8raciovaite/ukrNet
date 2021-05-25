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
         System.out.println("Filled email");
         title.sendKeys(someTitle);
         System.out.println("Filled title");
     }

     public void send(){
         send.click();
     }
}

