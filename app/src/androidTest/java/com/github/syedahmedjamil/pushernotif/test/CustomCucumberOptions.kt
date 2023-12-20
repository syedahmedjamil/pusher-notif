package com.github.syedahmedjamil.pushernotif.test

import io.cucumber.junit.CucumberOptions

@Suppress("unused")
@CucumberOptions(
    features = ["features"],
    glue = ["com.github.syedahmedjamil.pushernotif.test.acceptance"]
)
class CustomCucumberOptions