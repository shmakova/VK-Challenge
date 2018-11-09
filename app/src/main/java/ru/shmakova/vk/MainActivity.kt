package ru.shmakova.vk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!VKSdk.isLoggedIn()) {
            VKSdk.login(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(
                requestCode,
                resultCode,
                data,
                object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        Timber.e("LOGGED IN %s", res)
                    }

                    override fun onError(error: VKError) {
                        Timber.e("LOGGED ERROR %s", error)
                    }
                })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
