package com.example.twittervision

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_twitter_login.*
import android.content.Intent
import android.support.v7.app.AlertDialog
import com.twitter.sdk.android.core.Callback
import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.TwitterSession


class TwitterLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitter_login)
        loginButton.callback = object : Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>?) {
                startActivity(Intent(this@TwitterLoginActivity, TwitterMapVisualizationActivity::class.java))
                finish()
            }

            override fun failure(exception: TwitterException?) {
                with(AlertDialog.Builder(this@TwitterLoginActivity)) {
                    title = "Something went wrong"
                    setMessage("Twitter failed to login, please try again.")
                    setPositiveButton("Ok") { dialog: DialogInterface?, _: Int -> dialog?.dismiss() }
                }.show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data)
    }
}
