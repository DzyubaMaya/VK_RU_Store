package ru.rustore.mvp.ui.navigation

object Routes {
    const val ONBOARDING = "onboarding"
    const val STORE = "store"
    const val APP_DETAILS = "app_details/{appId}"
    const val PROFILE = "profile"
    const val SCREENSHOTS = "screenshots/{appId}?startIndex={startIndex}"
    const val INSTALLED_APP = "installed/{appId}"

    fun appDetails(appId: String): String = "app_details/$appId"
    fun screenshots(appId: String, startIndex: Int): String =
        "screenshots/$appId?startIndex=$startIndex"
    fun installedApp(appId: String): String = "installed/$appId"
}
