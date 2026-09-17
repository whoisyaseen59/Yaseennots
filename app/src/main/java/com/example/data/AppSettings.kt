package com.example.data

import android.content.Context
import android.content.SharedPreferences

data class AppSettings(
    val appName: String = "Yaseen",
    val notesModuleName: String = "Yaseen Notes",
    val tasksModuleName: String = "Yaseen Tasks",
    val scheduleModuleName: String = "Yaseen Schedule",
    val chatbotModuleName: String = "YaRVerse AI",
    val iconTheme: String = "Emerald", // Emerald, Neon Cyan, Sunset Gold, Royal Purple, Minimal Slate, Crimson Ruby, Ocean Blue
    val aiProvider: String = "GEMINI", // GEMINI, OPENAI, OPENROUTER
    val geminiApiKey: String = "",
    val openAiApiKey: String = "",
    val openRouterApiKey: String = "",
    val openAiBaseUrl: String = "https://api.openai.com/v1",
    val openRouterBaseUrl: String = "https://openrouter.ai/api/v1",
    val geminiModel: String = "gemini-3.5-flash",
    val openAiModel: String = "gpt-4o-mini",
    val openRouterModel: String = "openrouter/auto",
    val systemPrompt: String = "You are YaRVerse, an intelligent, helpful AI assistant integrated into the Yaseen productivity suite. You assist users with notes, task planning, schedule optimization, and knowledge organization. Respond clearly, concisely, and insightfully.",
    val defaultTab: String = "NOTES",
    val noteLayout: String = "STAGGERED",
    val confirmDelete: Boolean = true,
    val securityLockEnabled: Boolean = false,
    val securityPinCode: String = "",
    val notificationsSound: Boolean = true,
    val notificationsVibrate: Boolean = true
) {
    fun getActiveApiKey(): String {
        return when (aiProvider) {
            "GEMINI" -> geminiApiKey
            "OPENAI" -> openAiApiKey
            "OPENROUTER" -> openRouterApiKey
            else -> geminiApiKey
        }
    }

    fun getActiveModel(): String {
        return when (aiProvider) {
            "GEMINI" -> geminiModel
            "OPENAI" -> openAiModel
            "OPENROUTER" -> openRouterModel
            else -> geminiModel
        }
    }
}

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("yaseen_app_settings", Context.MODE_PRIVATE)

    fun loadSettings(): AppSettings {
        return AppSettings(
            appName = prefs.getString("app_name", "Yaseen") ?: "Yaseen",
            notesModuleName = prefs.getString("notes_module_name", "Yaseen Notes") ?: "Yaseen Notes",
            tasksModuleName = prefs.getString("tasks_module_name", "Yaseen Tasks") ?: "Yaseen Tasks",
            scheduleModuleName = prefs.getString("schedule_module_name", "Yaseen Schedule") ?: "Yaseen Schedule",
            chatbotModuleName = prefs.getString("chatbot_module_name", "YaRVerse AI") ?: "YaRVerse AI",
            iconTheme = prefs.getString("icon_theme", "Emerald") ?: "Emerald",
            aiProvider = prefs.getString("ai_provider", "GEMINI") ?: "GEMINI",
            geminiApiKey = prefs.getString("gemini_api_key", "") ?: "",
            openAiApiKey = prefs.getString("openai_api_key", "") ?: "",
            openRouterApiKey = prefs.getString("openrouter_api_key", "") ?: "",
            openAiBaseUrl = prefs.getString("openai_base_url", "https://api.openai.com/v1") ?: "https://api.openai.com/v1",
            openRouterBaseUrl = prefs.getString("openrouter_base_url", "https://openrouter.ai/api/v1") ?: "https://openrouter.ai/api/v1",
            geminiModel = prefs.getString("gemini_model", "gemini-3.5-flash") ?: "gemini-3.5-flash",
            openAiModel = prefs.getString("openai_model", "gpt-4o-mini") ?: "gpt-4o-mini",
            openRouterModel = prefs.getString("openrouter_model", "openrouter/auto") ?: "openrouter/auto",
            systemPrompt = prefs.getString(
                "system_prompt",
                "You are YaRVerse, an intelligent, helpful AI assistant integrated into the Yaseen productivity suite. You assist users with notes, task planning, schedule optimization, and knowledge organization. Respond clearly, concisely, and insightfully."
            ) ?: "You are YaRVerse, an intelligent AI assistant in Yaseen.",
            defaultTab = prefs.getString("default_tab", "NOTES") ?: "NOTES",
            noteLayout = prefs.getString("note_layout", "STAGGERED") ?: "STAGGERED",
            confirmDelete = prefs.getBoolean("confirm_delete", true),
            securityLockEnabled = prefs.getBoolean("security_lock_enabled", false),
            securityPinCode = prefs.getString("security_pin_code", "") ?: "",
            notificationsSound = prefs.getBoolean("notifications_sound", true),
            notificationsVibrate = prefs.getBoolean("notifications_vibrate", true)
        )
    }

    fun saveSettings(settings: AppSettings) {
        prefs.edit()
            .putString("app_name", settings.appName)
            .putString("notes_module_name", settings.notesModuleName)
            .putString("tasks_module_name", settings.tasksModuleName)
            .putString("schedule_module_name", settings.scheduleModuleName)
            .putString("chatbot_module_name", settings.chatbotModuleName)
            .putString("icon_theme", settings.iconTheme)
            .putString("ai_provider", settings.aiProvider)
            .putString("gemini_api_key", settings.geminiApiKey)
            .putString("openai_api_key", settings.openAiApiKey)
            .putString("openrouter_api_key", settings.openRouterApiKey)
            .putString("openai_base_url", settings.openAiBaseUrl)
            .putString("openrouter_base_url", settings.openRouterBaseUrl)
            .putString("gemini_model", settings.geminiModel)
            .putString("openai_model", settings.openAiModel)
            .putString("openrouter_model", settings.openRouterModel)
            .putString("system_prompt", settings.systemPrompt)
            .putString("default_tab", settings.defaultTab)
            .putString("note_layout", settings.noteLayout)
            .putBoolean("confirm_delete", settings.confirmDelete)
            .putBoolean("security_lock_enabled", settings.securityLockEnabled)
            .putString("security_pin_code", settings.securityPinCode)
            .putBoolean("notifications_sound", settings.notificationsSound)
            .putBoolean("notifications_vibrate", settings.notificationsVibrate)
            .apply()
    }
}
