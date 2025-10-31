// app/src/main/java/com/example/gratio/data/AppRepository.kt

package com.example.gratio.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gratio.model.*
import com.example.gratio.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val database: AppDatabase) {

    private val userDao = database.userDao()
    private val userProgressDao = database.userProgressDao()
    private val userSettingsDao = database.userSettingsDao()
    private val taskDao = database.taskDao()
    private val messageTemplateDao = database.messageTemplateDao()
    private val avatarDao = database.avatarDao()
    private val userAvatarDao = database.userAvatarDao()
    private val completedTaskDao = database.completedTaskDao()

    // --- Пользователь ---
    suspend fun insertUser(user: User) = userDao.insert(user)
    suspend fun getUserById(userId: String) = userDao.getUserById(userId)
    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)
    suspend fun getGuestByToken(token: String) = userDao.getGuestByToken(token)

    // --- Прогресс ---
    suspend fun insertUserProgress(progress: UserProgress) = userProgressDao.insert(progress)
    suspend fun getUserProgress(userId: String) = userProgressDao.getProgress(userId)
    suspend fun updateUserProgress(userId: String, points: Int, streakDays: Int, lastTaskDate: Long) =
        userProgressDao.updateProgress(userId, points, streakDays, lastTaskDate)

    // --- Настройки ---
    suspend fun insertUserSettings(settings: UserSettings) = userSettingsDao.insert(settings)
    suspend fun getUserSettings(userId: String) = userSettingsDao.getSettings(userId)
    suspend fun updateUserSettings(
        userId: String,
        soundEnabled: Boolean,
        musicVolume: Int,
        notificationsEnabled: Boolean,
        theme: String,
        appLanguage: String
    ) = userSettingsDao.updateSettings(userId, soundEnabled, musicVolume, notificationsEnabled, theme, appLanguage)

    // --- Задания ---

    suspend fun insertTask(task: Task) = taskDao.insert(task)
    suspend fun getRandomTask(category: String) = taskDao.getRandomTask(category)
    suspend fun getAllTasks() = taskDao.getAllTasks()

    suspend fun getTaskById(taskId: String): Task? = taskDao.getTaskById(taskId)

    // --- Шаблоны сообщений ---
    suspend fun insertMessageTemplate(template: MessageTemplate) = messageTemplateDao.insert(template)
    suspend fun getRandomMessageTemplate(category: String, languageCode: String) =
        messageTemplateDao.getRandomTemplate(category, languageCode)
    suspend fun getAllMessageTemplates(languageCode: String) = messageTemplateDao.getAllTemplates(languageCode)

    // --- Аватары ---
    suspend fun insertAvatar(avatar: Avatar) = avatarDao.insert(avatar)
    suspend fun getDefaultAvatar() = avatarDao.getDefaultAvatar()
    suspend fun getAllAvatars() = avatarDao.getAllAvatars()

    // --- Аватары пользователя ---
    suspend fun insertUserAvatar(userAvatar: UserAvatar) = userAvatarDao.insert(userAvatar)
    suspend fun getEquippedAvatar(userId: String) = userAvatarDao.getEquippedAvatar(userId)
    suspend fun getAllUserAvatars(userId: String) = userAvatarDao.getAllUserAvatars(userId)
    suspend fun unequipAllAvatars(userId: String) = userAvatarDao.unequipAllAvatars(userId)
    suspend fun equipAvatar(userAvatarId: Long) = userAvatarDao.equipAvatar(userAvatarId)

    // --- Выполненные задания ---
    suspend fun insertCompletedTask(completedTask: CompletedTask) = completedTaskDao.insert(completedTask)
    suspend fun getTasksCompletedToday(userId: String, startDate: Long) = completedTaskDao.getTasksCompletedToday(userId, startDate)
    suspend fun getRecentCompletedTasks(userId: String) = completedTaskDao.getRecentCompletedTasks(userId)

    fun getCurrentUserId(): String {
        // В реальном приложении вы бы получали ID из SharedPreferences
        return "test_user_1" // Для теста
    }

    // --- Инициализация начальных данных ---
    suspend fun initializeDefaultData(context: Context) {
        withContext(Dispatchers.IO) {
            // Проверяем, есть ли хотя бы один пользователь
            val userCount = userDao.getUserById("dummy")?.let { 1 } ?: 0
            if (userCount == 0) {
                // Добавляем тестового пользователя
                val testUser = User(
                    user_id = "test_user_1",
                    email = "test@example.com",
                    is_guest = false,
                    created_at = System.currentTimeMillis(),
                    last_login = System.currentTimeMillis()
                )
                userDao.insert(testUser)

                // Добавляем прогресс для тестового пользователя
                val testProgress = UserProgress(
                    user_id = "test_user_1",
                    points = 100,
                    streak_days = 5,
                    last_task_date = System.currentTimeMillis()
                )
                userProgressDao.insert(testProgress)

                // Добавляем настройки
                val testSettings = UserSettings(
                    user_id = "test_user_1",
                    sound_enabled = true,
                    music_volume = 70,
                    notifications_enabled = true,
                    theme = "light",
                    app_language = "ru"
                )
                userSettingsDao.insert(testSettings)

                // === ДОБАВЛЯЕМ ВСЕ ЗАДАНИЯ ЗДЕСЬ ===
                val tasks = listOf(
                    Task(
                        task_id = "task_1",
                        title = "Напишите комплимент другу",
                        description = "Выберите одного друга и отправьте ему искренний комплимент.",
                        category = "daily",
                        points_reward = 10,
                        is_recurring = true
                    ),
                    Task(
                        task_id = "task_2",
                        title = "Зайдите в приложение",
                        description = "Просто откройте приложение Gratio сегодня.",
                        category = "daily",
                        points_reward = 5,
                        is_recurring = true
                    ),
                    Task(
                        task_id = "task_3",
                        title = "Зайдите в игру",
                        description = "Посетите вкладку 'Игра' и попробуйте что-нибудь сделать.",
                        category = "daily",
                        points_reward = 8,
                        is_recurring = true
                    ),
                    Task(
                        task_id = "task_4",
                        title = "Поблагодарите коллегу",
                        description = "Поблагодарите коллегу за помощь или поддержку.",
                        category = "daily",
                        points_reward = 15,
                        is_recurring = true
                    ),
                    Task(
                        task_id = "task_5",
                        title = "Сделайте добрые дела",
                        description = "Сделайте что-то доброе для незнакомца.",
                        category = "weekly",
                        points_reward = 25,
                        is_recurring = false
                    )
                )
                tasks.forEach { taskDao.insert(it) }

                // === ДОБАВЛЯЕМ КОМПЛИМЕНТЫ ЗДЕСЬ ===
                val compliments = listOf(
                    MessageTemplate("c1", "Ты делаешь мир лучше просто своим присутствием!", "compliment", "ru", true),
                    MessageTemplate("c2", "Твоя улыбка способна растопить любой лёд!", "compliment", "ru", true),
                    MessageTemplate("c3", "В тебе столько доброты и тепла — это вдохновляет!", "compliment", "ru", true),
                    MessageTemplate("c4", "Ты обладаешь невероятной внутренней силой!", "compliment", "ru", true),
                    MessageTemplate("c5", "Каждый твой день делает этот мир ярче!", "compliment", "ru", true),
                    MessageTemplate("c6", "Ты — уникальная личность с потрясающим характером!", "compliment", "ru", true),
                    MessageTemplate("c7", "Твоя энергия заряжает всех вокруг позитивом!", "compliment", "ru", true),
                    MessageTemplate("c8", "Ты заслуживаешь самого лучшего в этой жизни!", "compliment", "ru", true),
                    MessageTemplate("c9", "Ты невероятно талантлив(а) и умён(на)!", "compliment", "ru", true),
                    MessageTemplate("c10", "Твоя доброта — настоящее сокровище!", "compliment", "ru", true),
                    MessageTemplate("c11", "Ты умеешь находить красоту в мелочах — это дар!", "compliment", "ru", true),
                    MessageTemplate("c12", "Ты — надёжный и преданный друг!", "compliment", "ru", true),
                    MessageTemplate("c13", "Ты сияешь изнутри своим обаянием!", "compliment", "ru", true),
                    MessageTemplate("c14", "Ты способен(на) на великие дела!", "compliment", "ru", true),
                    MessageTemplate("c15", "Просто быть собой — твоя самая большая сила!", "compliment", "ru", true),
                    // Добавьте остальные до 40, если нужно
                    MessageTemplate("c16", "Ты — свет в этом мире, и без тебя он был бы темнее!", "compliment", "ru", true)
                )
                compliments.forEach { messageTemplateDao.insert(it) }

                // === ДОБАВЛЯЕМ АВАТАРЫ ЗДЕСЬ ===
                val avatars = listOf(
                    Avatar(
                        avatar_id = "avatar_1",
                        name = "Котик",
                        image_url = "@drawable/avatar_cat",
                        rarity = "common",
                        price_coins = 0,
                        is_default = true
                    ),
                    Avatar(
                        avatar_id = "avatar_2",
                        name = "Собачка",
                        image_url = "@drawable/avatar_dog",
                        rarity = "common",
                        price_coins = 50,
                        is_default = false
                    ),
                    Avatar(
                        avatar_id = "avatar_3",
                        name = "Лисичка",
                        image_url = "@drawable/avatar_fox",
                        rarity = "rare",
                        price_coins = 100,
                        is_default = false
                    )
                )
                avatars.forEach { avatarDao.insert(it) }

                // Назначаем дефолтный аватар
                val defaultAvatar = avatarDao.getDefaultAvatar()
                if (defaultAvatar != null) {
                    val userAvatar = UserAvatar(
                        user_id = "test_user_1",
                        avatar_id = defaultAvatar.avatar_id,
                        is_equipped = true
                    )
                    userAvatarDao.insert(userAvatar)
                }
            }
        }
    }
}