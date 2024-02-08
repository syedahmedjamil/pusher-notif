package org.example.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.After;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class TestBase {

    public static AppiumDriver driver;
    public static String APK_NAME = "PusherNotif.apk";

    public static String DIR_NAME = System.getProperty("user.dir");


    public static String APK_PATH = DIR_NAME + "/apps/" + APK_NAME;

    public static void Android_setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("platformVersion", "11.0");
        capabilities.setCapability("deviceName", "Android Emulator");
        capabilities.setCapability("app", APK_PATH);
        driver = new AndroidDriver(new URL("http://localhost:4723"), capabilities);
    }

    public static void tearDown() {
        if (null != driver) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
            driver.quit();
        }
    }
}