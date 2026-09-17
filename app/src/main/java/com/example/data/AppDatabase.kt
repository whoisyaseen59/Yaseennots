package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Database(
    entities = [
        Note::class,
        Notebook::class,
        TaskItem::class,
        NoteVersion::class,
        ScheduleItem::class,
        ChatMessage::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "yaseen_app.db"
                )
                .fallbackToDestructiveMigration()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                populateInitialData(database.appDao())
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }

        private suspend fun populateInitialData(dao: AppDao) {
            val personalNotebook = Notebook(
                id = "nb-personal",
                name = "Personal",
                color = 0xFF2DBD6C,
                icon = "heart"
            )
            val workNotebook = Notebook(
                id = "nb-work",
                name = "Work & Ideas",
                color = 0xFF0288D1,
                icon = "work"
            )
            val studyNotebook = Notebook(
                id = "nb-study",
                name = "Study & Reading",
                color = 0xFF7C3AED,
                icon = "book"
            )

            dao.insertNotebook(personalNotebook)
            dao.insertNotebook(workNotebook)
            dao.insertNotebook(studyNotebook)

            // Welcome Note
            dao.insertNote(
                Note(
                    id = "note-welcome",
                    title = "Welcome to Yaseen Suite 🚀",
                    content = "Yaseen is your unified productivity hub:\n\n📝 Yaseen Notes: Rich notes, color themes, notebooks, drawing canvas & version history.\n✅ Yaseen Tasks: Categorized to-dos with priorities and deadlines.\n📅 Yaseen Schedule: Daily timetable and event schedule.\n🤖 YaRVerse Chatbot: AI assistant running with your choice of Gemini, OpenAI, or OpenRouter keys configured in Settings.",
                    description = "Overview of Yaseen suite features",
                    tags = "Welcome,Guide,Yaseen",
                    color = 0xFFE8F5E9,
                    isPinned = true,
                    notebookId = personalNotebook.id
                )
            )

            dao.insertNote(
                Note(
                    id = "note-meeting-template",
                    title = "Product Strategy & Architecture",
                    content = "Agenda:\n1. Roadmap for Yaseen suite\n2. Offline-first Room database sync\n3. YaRVerse AI chatbot integration\n\nDecisions:\n- Allow user to customize app name and icon theme in Settings.\n- Support multi-provider AI (Gemini, OpenAI, OpenRouter).",
                    description = "Meeting notes and strategy decisions",
                    tags = "Work,Planning",
                    color = 0xFFE3F2FD,
                    isPinned = false,
                    notebookId = workNotebook.id
                )
            )

            // Initial Tasks
            dao.insertTask(
                TaskItem(
                    id = "task-1",
                    title = "Configure YaRVerse API key in Settings",
                    description = "Choose Gemini, OpenAI, or OpenRouter and enter your key in Settings",
                    dueDate = System.currentTimeMillis() + 86400000L,
                    isCompleted = false,
                    priority = 2
                )
            )
            dao.insertTask(
                TaskItem(
                    id = "task-2",
                    title = "Plan week in Yaseen Schedule",
                    description = "Add calendar blocks for focused work and meetings",
                    dueDate = System.currentTimeMillis() + 172800000L,
                    isCompleted = false,
                    priority = 1
                )
            )

            // Initial Schedule Items
            val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val baseTime = System.currentTimeMillis()
            dao.insertScheduleItem(
                ScheduleItem(
                    id = "sch-1",
                    title = "Morning Planning & Review",
                    description = "Check pending tasks in Yaseen Tasks and organize daily priorities",
                    startTime = baseTime,
                    endTime = baseTime + 3600000L,
                    dateString = todayStr,
                    category = "Routine",
                    color = 0xFF10B981,
                    location = "Workstation"
                )
            )
            dao.insertScheduleItem(
                ScheduleItem(
                    id = "sch-2",
                    title = "Deep Work Session",
                    description = "Focus on project core features and documentation",
                    startTime = baseTime + 7200000L,
                    endTime = baseTime + 14400000L,
                    dateString = todayStr,
                    category = "Work",
                    color = 0xFF3B82F6,
                    location = "Office / Studio"
                )
            )

            // Welcome Chat Message
            dao.insertChatMessage(
                ChatMessage(
                    id = "msg-welcome",
                    sender = "yarverse",
                    text = "Hello! I am YaRVerse, your personal AI assistant within the Yaseen suite. You can configure your API key (Gemini, OpenAI, or OpenRouter) in Settings to start chatting, brainstorming, summarizing your notes, and planning your schedule!",
                    timestamp = System.currentTimeMillis(),
                    provider = "System"
                )
            )
        }
    }
}
