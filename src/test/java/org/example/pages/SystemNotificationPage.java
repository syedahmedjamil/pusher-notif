package org.example.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SystemNotificationPage extends BasePage {
    public SystemNotificationPage(AppiumDriver driver) {
        super(driver);
    }

    @FindBy(xpath = """
            //*[@text="Pusher Notif (debug)"]/../..//*[@resource-id="android:id/header_text"]
            """)
    WebElement notificationHeaderText;
    @FindBy(xpath = """
             //*[@text="Pusher Notif (debug)"]/../..//*[@resource-id="android:id/title"]
            """)
    WebElement notificationTitleText;
    @FindBy(xpath = """
            //*[@text="Pusher Notif (debug)"]/../..//*[@resource-id="android:id/big_text"]
            """)
    WebElement notificationBodyText;

    public void openAndroidNotificationTray() {
        ((AndroidDriver) driver).openNotifications();
    }
    public String getNotificationHeaderText() {
        return getAttribute(notificationHeaderText, "text");
    }

    public String getNotificationTitle() {
        return getAttribute(notificationTitleText, "text");
    }

    public String getNotificationBody() {
        return getAttribute(notificationBodyText, "text");
    }


}
