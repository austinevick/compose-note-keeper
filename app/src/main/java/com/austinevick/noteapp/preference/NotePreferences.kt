package com.austinevick.noteapp.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class NotePreferences(context: Context) {
    private val passcodeKey = stringPreferencesKey("passcode_key")

    suspend fun saveToDataStore(context: Context, value: String) {
        context.dataStore.edit { preferences ->
            preferences[passcodeKey] = value
        }
    }

    val getFromDataStore: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[passcodeKey] ?: ""
        }

}