package ru.rustore.mvp.data.repository

import ru.rustore.mvp.R
import ru.rustore.mvp.data.model.AgeRating
import ru.rustore.mvp.data.model.AppCategory
import ru.rustore.mvp.data.model.AppItem
import ru.rustore.mvp.data.model.CategorySummary
import ru.rustore.mvp.data.model.ImageRef
import ru.rustore.mvp.data.model.Screenshot

class FakeAppRepository : AppRepository {

    private val screenshotPalette = listOf(
        R.drawable.ic_screenshot_blue,
        R.drawable.ic_screenshot_violet,
        R.drawable.ic_screenshot_green,
        R.drawable.ic_screenshot_orange,
    )

    private val defaultCaptions = listOf(
        "Главный экран",
        "Каталог",
        "Карточка товара",
        "Профиль",
        "Настройки",
    )

    private val apps: List<AppItem> = listOf(
        AppItem(
            id = "sber-online",
            title = "СберБанк Онлайн",
            shortDescription = "Платежи, переводы, инвестиции",
            fullDescription = "Управляйте картами и счетами, отправляйте мгновенные переводы по " +
                "номеру телефона, оплачивайте услуги ЖКХ и связь, открывайте вклады и следите за " +
                "тратами в одном приложении. Биометрический вход и push-уведомления о каждой операции.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.FINANCE,
            developer = "ПАО Сбербанк",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Главный экран", "Перевод по телефону", "История операций"),
            ),
            rating = 4.6,
            downloads = "100 млн+",
            version = "14.27.1",
            updatedAt = "12 мая 2026",
            sizeMb = 184,
        ),
        AppItem(
            id = "tinkoff",
            title = "Т-Банк",
            shortDescription = "Банк, который всегда с тобой",
            fullDescription = "Бесплатные переводы по номеру телефона, кэшбэк до 30%, инвестиции " +
                "и брокерский счёт, ипотека и автокредиты. Поддержка 24/7 в чате и встроенный " +
                "помощник для планирования бюджета.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.FINANCE,
            developer = "АО «ТБанк»",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Главный экран", "Кэшбэк за месяц", "Инвестиции"),
            ),
            rating = 4.8,
            downloads = "50 млн+",
            version = "7.12.0",
            updatedAt = "10 мая 2026",
            sizeMb = 142,
        ),
        AppItem(
            id = "yandex-maps",
            title = "Яндекс Карты",
            shortDescription = "Навигация и пробки в реальном времени",
            fullDescription = "Маршруты на машине, общественном транспорте и пешком. Подробные " +
                "карты городов и сёл, отзывы об организациях, панорамы улиц, информация о " +
                "пробках и дорожных событиях от других водителей.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.TOOLS,
            developer = "Яндекс",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Карта города", "Построение маршрута", "Пробки", "Поиск организации"),
            ),
            rating = 4.7,
            downloads = "500 млн+",
            version = "16.4.0",
            updatedAt = "9 мая 2026",
            sizeMb = 96,
        ),
        AppItem(
            id = "kaspersky",
            title = "Kaspersky: Антивирус",
            shortDescription = "Защита от вирусов и фишинга",
            fullDescription = "Проверка приложений и файлов в реальном времени, защита от " +
                "опасных сайтов и фишинговых ссылок, блокировка нежелательных звонков и SMS. " +
                "VPN на 200 МБ в день для безопасного интернета в публичных Wi-Fi.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.TOOLS,
            developer = "АО «Лаборатория Касперского»",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Проверка устройства", "Защита VPN", "Журнал угроз"),
            ),
            rating = 4.5,
            downloads = "100 млн+",
            version = "11.110.4",
            updatedAt = "6 мая 2026",
            sizeMb = 78,
        ),
        AppItem(
            id = "atomic-heart",
            title = "Atomic Heart Mobile",
            shortDescription = "Шутер в альтернативной советской вселенной",
            fullDescription = "Сражайтесь с роботами на советской исследовательской базе в " +
                "альтернативной истории. Захватывающий сюжет, более 20 видов оружия и улучшений, " +
                "поддержка геймпада и облачных сохранений.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.GAMES,
            developer = "Mundfish",
            ageRating = AgeRating.AGE_16,
            screenshots = screenshots(
                listOf("Игровой процесс", "Босс «Близнецы»", "Инвентарь оружия", "Меню улучшений"),
            ),
            rating = 4.4,
            downloads = "5 млн+",
            version = "1.8.3",
            updatedAt = "2 мая 2026",
            sizeMb = 1850,
        ),
        AppItem(
            id = "wordle-ru",
            title = "Словесы — игра слов",
            shortDescription = "Угадай слово за 6 попыток",
            fullDescription = "Ежедневная головоломка на русском языке: за шесть попыток отгадайте " +
                "слово из пяти букв. Подсказки по цветам, статистика серий, режим дуэли с " +
                "друзьями и архив прошлых дней.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.GAMES,
            developer = "Brainy Games",
            ageRating = AgeRating.AGE_6,
            screenshots = screenshots(
                listOf("Игровое поле", "Победа", "Статистика серий"),
            ),
            rating = 4.9,
            downloads = "1 млн+",
            version = "2.4.0",
            updatedAt = "14 мая 2026",
            sizeMb = 32,
        ),
        AppItem(
            id = "gosuslugi",
            title = "Госуслуги",
            shortDescription = "Госуслуги онлайн без очередей",
            fullDescription = "Запись к врачу и в детский сад, получение справок и выписок, " +
                "оплата налогов и штрафов ГИБДД, проверка задолженностей, заявления в " +
                "электронном виде. Подтверждение личности через ЕСИА.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.GOVERNMENT,
            developer = "Минцифры России",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Главный экран", "Каталог услуг", "Оплата штрафа", "Документы"),
            ),
            rating = 4.2,
            downloads = "100 млн+",
            version = "5.41.0",
            updatedAt = "8 мая 2026",
            sizeMb = 110,
        ),
        AppItem(
            id = "mos-ru",
            title = "Моя Москва",
            shortDescription = "Городские сервисы столицы",
            fullDescription = "Запись к врачу, оплата ЖКУ и парковок, передача показаний " +
                "счётчиков, запись ребёнка в школу и кружки, информация о городских событиях. " +
                "Все сервисы Mos.ru в одном приложении.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.GOVERNMENT,
            developer = "ДИТ Москвы",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Главный экран", "Оплата ЖКУ", "Запись к врачу"),
            ),
            rating = 4.1,
            downloads = "20 млн+",
            version = "8.7.2",
            updatedAt = "5 мая 2026",
            sizeMb = 88,
        ),
        AppItem(
            id = "yandex-go",
            title = "Яндекс Go",
            shortDescription = "Такси, доставка, каршеринг",
            fullDescription = "Закажите такси за пару секунд, отслеживайте машину на карте, " +
                "выбирайте тариф и платите картой или наличными. Доставка из ресторанов и " +
                "магазинов, аренда самокатов и каршеринг в одном приложении.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.TRANSPORT,
            developer = "Яндекс",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Заказ такси", "Маршрут водителя", "Тарифы", "Доставка еды"),
            ),
            rating = 4.7,
            downloads = "500 млн+",
            version = "4.215.0",
            updatedAt = "11 мая 2026",
            sizeMb = 124,
        ),
        AppItem(
            id = "metro-moscow",
            title = "Метро Москвы",
            shortDescription = "Маршруты и оплата проезда",
            fullDescription = "Постройте маршрут по метро, МЦК и МЦД с учётом времени поездки " +
                "и пересадок. Оплата проезда по Face Pay и QR-коду, информация о работе " +
                "станций и эскалаторов, поиск вещей в бюро находок.",
            icon = ImageRef.Resource(R.drawable.ic_app_placeholder),
            category = AppCategory.TRANSPORT,
            developer = "ГУП «Московский метрополитен»",
            ageRating = AgeRating.AGE_0,
            screenshots = screenshots(
                listOf("Схема метро", "Маршрут поездки", "Face Pay"),
            ),
            rating = 4.3,
            downloads = "10 млн+",
            version = "3.18.1",
            updatedAt = "7 мая 2026",
            sizeMb = 64,
        ),
    )

    override fun getApps(): List<AppItem> = apps

    override fun getAppById(id: String): AppItem? = apps.find { it.id == id }

    override fun getAppsByCategory(category: AppCategory): List<AppItem> =
        apps.filter { it.category == category }

    override fun getCategorySummaries(): List<CategorySummary> =
        AppCategory.entries.map { category ->
            CategorySummary(
                category = category,
                appCount = apps.count { it.category == category },
            )
        }

    private fun screenshots(captions: List<String>): List<Screenshot> =
        captions.mapIndexed { index, caption ->
            Screenshot(
                image = ImageRef.Resource(screenshotPalette[index % screenshotPalette.size]),
                caption = caption.ifBlank { defaultCaptions[index % defaultCaptions.size] },
            )
        }
}
