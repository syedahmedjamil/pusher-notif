# pushier
android client app that integrates with pusher beams to show realtime in-app notifications.

# payload
sample using curl is located in : https://github.com/syedahmedjamil/pushier/blob/main/extras/payload%20sample%20using%20curl.bat

> interest name has to be present in the `interests` array as well as in `fcm.data.interest` for it to work properly
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

# demo
![](https://github.com/syedahmedjamil/pushier/blob/main/extras/demo.gif)
