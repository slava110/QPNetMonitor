package com.slava_110.qpnetmonitor.data.local

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class UserCredentialsStore(
    appContext: Context
) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val prefs = EncryptedSharedPreferences.create(
        "creds",
        masterKeyAlias,
        appContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun retrieveCreds(): Pair<String, String>? =
        prefs.getString("login", null)?.let { login ->
            prefs.getString("password", null)?.let { pass ->
                login to pass
            }
        }

    fun putCreds(login: String, pass: String) {
        prefs.edit {
            putString("login", login)
            putString("password", pass)
        }
    }

    fun clearCreds() {
        prefs.edit {
            remove("login")
            remove("password")
        }
    }
}