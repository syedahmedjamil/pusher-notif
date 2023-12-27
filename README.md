# Update
Rewriting app with clean architecture, acceptance test driven development and continous integration/delivery using Github Actions and Firebase App Distribution.
To access the old version, navigate to this [commit](https://github.com/syedahmedjamil/pushier/commit/ea7f30f8890fba63ff5571d64c3dffbe08dd9bfd) and click "Browse Files".

# About
Android client app that integrates with pusher beams to show realtime in-app notifications.

#  Before building the app
1. configure fcm https://pusher.com/docs/beams/getting-started/android/configure-fcm/?ref=docs-index#open-firebase-console
2. make sure when creating app in firebase the **"Android package name"** is same as you set in **build.grade** `applicationId`
3. paste your fcm server key in your pusher beams instance "Settings" page under "Google FCM Integration" field
4. download and store your `google-services.json` file in the app folder

# Payload
> NOTE: your interest name has to be present in the `interests` array as well as in `fcm.data.interest` as shown below for it to work properly.
```json
{
    "interests": [
        "reddit"
    ],
    "fcm": {
        "data": {
            "interest": "reddit",
            "category": "Important",
            "date": "1/1/2022",
            "title": "How to optimise text size on lower dpi devices",
            "body": "In XML, is there any other way than defining new layouts files for different dpi devices just to handle text sizes as it completely messes up the entire layout if not handled properly?",
            "subtext": "r/androiddev",
            "link": "https://www.reddit.com/r/androiddev/comments/13a3p1c/how_to_optimise_text_size_on_lower_dpi_devices",
            "image": "https://media.glassdoor.com/sql/796358/reddit-squarelogo-1490630845152.png"
        }
    }
}
```
Sample using curl is located in : https://github.com/syedahmedjamil/pushier/blob/main/extras/payload%20sample%20using%20curl.bat.

To use this sample you will need `PUSHER-BEAMS-INSTANCE-PRIMARY-KEY` and `PUSHER-BEAMS-INSTANCE-ID` both of which can be found by going to the "Keys" page of your pusher beams instance

# Demo
![](https://github.com/syedahmedjamil/pushier/blob/main/extras/demo.gif)
