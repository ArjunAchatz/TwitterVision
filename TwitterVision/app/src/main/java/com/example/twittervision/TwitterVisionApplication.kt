package com.example.twittervision

import android.app.Application
import android.util.Log
import com.twitter.sdk.android.core.*

class TwitterVisionApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val twitterConfig = TwitterConfig.Builder(this).apply {
            logger(DefaultLogger(Log.DEBUG))
            twitterAuthConfig(
                TwitterAuthConfig(
                    TODO(),
                    TODO()
                )
            )
            debug(true)
        }.build()
        Twitter.initialize(twitterConfig)
    }

}