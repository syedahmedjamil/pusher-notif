package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NotificationsPage extends BasePage {
    public NotificationsPage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(id = "toolbar_title")
    WebElement pageTitleText;
    @FindBy(id = "notification_title")
    WebElement notificationTitleText;
    @FindBy(id = "notification_date")
    WebElement notificationDateText;
    @FindBy(id = "notification_subtext")
    WebElement notificationSubtitleText;
    @FindBy(id = "notification_icon")
    WebElement notificationImage;

    public String getPageTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.textToBePresentInElement(pageTitleText, "Notifications"));
        return getAttribute(pageTitleText, "text");
    }

    public String getNotificationTitle() {
        return getAttribute(notificationTitleText, "text");
    }

    public String getNotificationDate() {
        return getAttribute(notificationDateText, "text");
    }

    public String getNotificationSubtext() {
        return getAttribute(notificationSubtitleText, "text");
    }


}
