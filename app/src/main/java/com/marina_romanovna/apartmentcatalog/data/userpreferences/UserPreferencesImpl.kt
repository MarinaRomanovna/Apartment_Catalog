package com.marina_romanovna.apartmentcatalog.data.userpreferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.marina_romanovna.apartmentcatalog.domain.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    private val context: Context
) : UserPreferences() {

    companion object {
        private const val USER_PREFERENCES = "app_prefs"
        private val KEY_AUTH = stringPreferencesKey("key_auth")
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES)
    }

    override val authToken: Flow<String?>
        get() = context.dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    override suspend fun saveAuthToken(authToken: String) {
        context.dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }
}