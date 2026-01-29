package ru.sochasapps.gvosynative

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.util.UUID

class AppPreferences(private val dataStore: DataStore<Preferences>) {

    val conversationIdFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[CURRENT_CONVERSATION_ID]
    }

    companion object {
        val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")
        val CURRENT_CONVERSATION_ID = stringPreferencesKey("current_conversation_id")
        val MICROPHONE_PERMISSION_ASKED = booleanPreferencesKey("microphone_permission_asked")
        val MICROPHONE_PERMISSION_GRANTED = booleanPreferencesKey("microphone_permission_granted")
    }

    suspend fun getIsFirstLaunch(): Boolean {
        return dataStore.data.map{ preferences -> preferences[IS_FIRST_LAUNCH] ?: true }.first()
    }

    suspend fun setFirstLaunchCompleted() {
        dataStore.edit { preferences ->
            preferences[IS_FIRST_LAUNCH] = false
        }
    }

    suspend fun wasPermissionAsked():Boolean {
        return dataStore.data.map { preferences ->
            preferences[MICROPHONE_PERMISSION_ASKED] ?: false
        }.first()
    }

    suspend fun setPermissionAsked() {
        dataStore.edit { preferences ->
            preferences[MICROPHONE_PERMISSION_ASKED] = true
        }
    }

    suspend fun setPermissionGranted(granted: Boolean) {
        dataStore.edit { preferences ->
            preferences[MICROPHONE_PERMISSION_GRANTED] = granted
        }
    }

    suspend fun isPermissionGranted(): Boolean {
        return dataStore.data.map { preferences ->
            preferences[MICROPHONE_PERMISSION_GRANTED] ?: false
        }.first()
    }

    suspend fun getOrCreateConversationId(): String {
        val existingId = dataStore.data.map { preferences ->
            preferences[CURRENT_CONVERSATION_ID]
        }.first()

        return if (existingId != null) {
            existingId
        } else {
            val newId = generateConversationId()
            saveConversationId(newId)
            newId
        }
    }

    suspend fun saveConversationId(conversationId: String) {
        dataStore.edit { preferences ->
            preferences[CURRENT_CONVERSATION_ID] = conversationId
        }
    }

    suspend fun clearConversationId() {
        dataStore.edit { preferences ->
            preferences.remove(CURRENT_CONVERSATION_ID)
        }
    }

    private fun generateConversationId(): String {
        return UUID.randomUUID().toString()
    }

    suspend fun getCurrentConversationId(): String? {
        return dataStore.data.map { preferences ->
            preferences[CURRENT_CONVERSATION_ID]
        }.first()
    }

}