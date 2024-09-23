package com.slava_110.qpnetmonitor.ui.login

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.ActivityLoginBinding
import com.slava_110.qpnetmonitor.ui.main.MainActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS))
            window.enterTransition = Explode()

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginEditEmail.setOnFocusChangeListener { v, hasFocus ->

        }

        binding.loginEditPassword.setOnFocusChangeListener { v, hasFocus ->

        }

        lifecycleScope.launch {
            viewModel.authResult.collectLatest { result ->
                result.onSuccess {
                    Log.d("Login", "Logged in")
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.welcome_toast, it.displayName),
                            Toast.LENGTH_LONG
                        ).show()
                        notifyUser()
                    }
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                result.onFailure {
                    Log.d("Login", "Error on login!")
                    runOnUiThread {
                        Toast.makeText(
                            applicationContext,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        viewModel.authorizeSaved()

        binding.loginButton.setOnClickListener {
            Log.d("Login", "Logging in...")

            viewModel.authorize(
                binding.loginEditEmail.text.toString(),
                binding.loginEditPassword.text.toString()
            )
        }
    }

    private fun notifyUser() {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, "QPNetMonitor")
            .setSmallIcon(R.drawable.duck)
            .setContentTitle(getString(R.string.welcome_notification_title))
            .setContentText(getString(R.string.welcome_notification))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("QPNetMonitor", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}