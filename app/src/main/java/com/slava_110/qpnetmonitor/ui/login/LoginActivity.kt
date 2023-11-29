package com.slava_110.qpnetmonitor.ui.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import com.slava_110.qpnetmonitor.R
import com.slava_110.qpnetmonitor.databinding.ActivityLoginBinding
import com.slava_110.qpnetmonitor.model.dto.AuthResponse
import com.slava_110.qpnetmonitor.ui.login.data.model.LoginResult
import com.slava_110.qpnetmonitor.ui.main.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(R.layout.activity_login) {
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginEditEmail.setOnFocusChangeListener { v, hasFocus ->

        }

        binding.loginEditPassword.setOnFocusChangeListener { v, hasFocus ->

        }

        binding.loginButton.setOnClickListener {
            //binding.loginLoading.visibility = View.VISIBLE
            Log.d("Login", "Logging in...")

            lifecycleScope.launch {
                val result = viewModel.authorize(
                    binding.loginEditEmail.text.toString(),
                    binding.loginEditPassword.text.toString()
                )

                withContext(Dispatchers.Main) {
                    //binding.loginLoading.visibility = View.GONE

                    when(result) {
                        is AuthResponse.Success -> {
                            Log.d("Login", "Logged in")
                            Toast.makeText(
                                applicationContext,
                                getString(R.string.welcome_toast, result.displayName),
                                Toast.LENGTH_LONG
                            ).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                        is AuthResponse.Failure -> {
                            Log.d("Login", "Error on login!")
                            Toast.makeText(
                                applicationContext,
                                result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}