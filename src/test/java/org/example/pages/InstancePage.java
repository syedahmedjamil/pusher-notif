package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InstancePage extends BasePage {
    public InstancePage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(id = "toolbar_title")
    WebElement pageTitleText;
    @FindBy(id = "instance_id_edit_text")
    WebElement instanceIdText;
    @FindBy(id = "instance_interest_edit_text")
    WebElement interestNameText;
    @FindBy(id = "instance_add_interest_button")
    WebElement addInterestButton;
    @FindBy(id = "instance_subscribe_button")
    WebElement subscribeButton;

    public String getPageTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElement(pageTitleText, "Instance"));
        return getAttribute(pageTitleText, "text");
    }

    public void enterInstanceId(String instanceId) {
        clear(instanceIdText);
        sendText(instanceIdText, instanceId);
    }

    public void enterInterestName(String interestName) {
        clear(interestNameText);
        sendText(interestNameText, interestName);
    }

    public void clickAddInterestButton() {
        click(addInterestButton);
    }

    public void clickSubscribeButton() {
        click(subscribeButton);
    }

}
