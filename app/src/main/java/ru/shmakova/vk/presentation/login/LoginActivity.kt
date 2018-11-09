package ru.shmakova.vk.presentation.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.shmakova.vk.R
import ru.shmakova.vk.presentation.main.MainActivity
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            login()
        }
        login()
    }

    private fun login() {
        if (VKSdk.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            VKSdk.login(this, VKScope.WALL)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(
                requestCode,
                resultCode,
                data,
                object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        Timber.d("Success login")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onError(error: VKError?) {
                        Timber.e("Login error %s", error)
                    }
                })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
