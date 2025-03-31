package com.example.core.data

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.secureDataStore: DataStore<Preferences> by preferencesDataStore(name = "secure_data")

class SecurePreferences(context: Context) {
    private val dataStore = context.secureDataStore
    private val encryptedPrefs: SharedPreferences

    init {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

       encryptedPrefs = EncryptedSharedPreferences.create(
            context,
            "secure_data",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    companion object {
        val MASTER_KEY_ALIAS = stringPreferencesKey("master_secure")
    }

    suspend fun saveMasterKey(key: String) {
        dataStore.edit { preferences ->
            preferences[MASTER_KEY_ALIAS] = key
        }
    }

    fun getMasterKey(): Flow<String?> {
        return dataStore.data.map { prev -> prev[MASTER_KEY_ALIAS] }
    }

    suspend fun clearMasterKey() {
        dataStore.edit { preferences ->
            preferences.remove(MASTER_KEY_ALIAS)
        }
    }
}
