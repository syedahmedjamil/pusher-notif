package com.github.syedahmedjamil.pushernotif.test.integration

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ServiceTestRule
import androidx.test.uiautomator.UiDevice
import com.github.syedahmedjamil.pushernotif.domain.SubscribeService
import com.github.syedahmedjamil.pushernotif.framework.MyPusherMessagingService
import com.github.syedahmedjamil.pushernotif.framework.SubscribeServiceImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.util.Properties

/*
Waits or delays are not good in a test. Unable to mock fcm so adding some waits to make it more
reliable.
*/

class SubscribeServiceImplTest {
    private lateinit var service: SubscribeService
    private lateinit var context: Context

    @get:Rule
    val serviceRule = ServiceTestRule()

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        service = SubscribeServiceImpl(context)
    }

    @Ignore("passing locally on physical and virtual devices but failing on github actions")
    @Test
    fun should_subscribe() = runBlocking {
        // given
        val contextForFile = InstrumentationRegistry.getInstrumentation().context
        val file = contextForFile.assets.open("test.properties")
        val properties = Properties()
        properties.load(file)
        val accessToken = properties["accessToken"] as String
        val instanceId = properties["instanceId"] as String
        val interests = listOf("debug-test")
        val serviceIntent =
            Intent(
                ApplicationProvider.getApplicationContext(),
                MyPusherMessagingService::class.java
            )

        // when
        serviceRule.startService(serviceIntent)
        Thread.sleep(10000)
        service.subscribe(instanceId, interests)
        Thread.sleep(5000)
        sendPush(instanceId, interests[0], accessToken)
        Thread.sleep(5000)

        // then
        Assert.assertTrue(MyPusherMessagingService.isOnMessageReceivedCalled)
        Assert.assertEquals(MyPusherMessagingService.data["title"], "Test Title")
    }

    private fun sendPush(instanceId: String, interest: String, accessToken: String) {
        val client = OkHttpClient();
        val mediaType = MediaType.get("application/json")
        val url = getUrl(instanceId)

        val body = RequestBody.create(mediaType, getRequestBody(interest))

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer $accessToken")
            .post(body)
            .build()
        try {
            val response = client.newCall(request).execute()
        } catch (e: Exception) {

        }

    }

    private fun getUrl(instanceId: String): String {
        return "https://${instanceId}.pushnotifications.pusher.com/publish_api/v1/instances/${instanceId}/publishes"
    }

    private fun getRequestBody(interest: String): String {
        val body = "{\n" +
                "    \"interests\": [\n" +
                "        \"${interest}\"\n" +
                "    ],\n" +
                "    \"fcm\": {\n" +
                "        \"data\": {\n" +
                "            \"interest\": \"${interest}\",\n" +
                "            \"category\": \"test\",\n" +
                "            \"date\": \"1/1/2024\",\n" +
                "            \"title\": \"Test Title\",\n" +
                "            \"body\": \"Test Body\",\n" +
                "            \"subtext\": \"Test Subtext\",\n" +
                "            \"link\": \"https://www.test.com\",\n" +
                "            \"image\": \"https://test.png\"\n" +
                "        }\n" +
                "    }\n" +
                "}"

        return body
    }

}