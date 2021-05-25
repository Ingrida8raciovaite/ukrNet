import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class CheckIfMessageCame {
    //variable message link
    WebElement messageLink;
    String messageLinkText;
    //variable sent message page
    WebElement sentMessagesPage;
    //list of sent messages
    List<String> links;
    //elements for the sent message content
    public String titleSentMessage;
    public String from;
    public String to;
    public String letter;

    //open the "sent messages" page
    public void openSentMessagesPage(){
        sentMessagesPage.click();
    }

    //check on the 'Messages' list
    public void findTheMessage(){
        System.out.println("Check test list");
        System.out.println(links.size());
        System.out.println(messageLinkText);
        for(int i = 0; i < links.size(); i++){
            System.out.println(links.get(i));
            if(messageLinkText.equals(links.get(i))){
                i = links.size();
                System.out.println("The message is presented on the list");
            }
        }
    }

    //check content of the message
    public void checkContent(String from, String to, String title, String letter){
        Assert.assertEquals(this.from, from, "From who email the same");
        Assert.assertEquals(this.to, to, "To whom email the same");
        Assert.assertEquals(titleSentMessage, title, "Titles are not the same");
        Assert.assertEquals(this.letter, letter, "Content of the message the same");
    }
}
