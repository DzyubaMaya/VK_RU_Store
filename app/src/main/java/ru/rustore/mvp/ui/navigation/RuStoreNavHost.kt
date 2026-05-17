package ru.rustore.mvp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.rustore.mvp.di.AppContainer
import ru.rustore.mvp.ui.details.AppDetailsScreen
import ru.rustore.mvp.ui.details.AppDetailsViewModel
import ru.rustore.mvp.ui.installed.InstalledAppScreen
import ru.rustore.mvp.ui.installed.InstalledAppViewModel
import ru.rustore.mvp.ui.onboarding.OnboardingScreen
import ru.rustore.mvp.ui.onboarding.OnboardingViewModel
import ru.rustore.mvp.ui.profile.ProfileScreen
import ru.rustore.mvp.ui.profile.ProfileViewModel
import ru.rustore.mvp.ui.screenshots.ScreenshotsScreen
import ru.rustore.mvp.ui.screenshots.ScreenshotsViewModel
import ru.rustore.mvp.ui.store.StoreScreen
import ru.rustore.mvp.ui.store.StoreViewModel

@Composable
fun RuStoreNavHost(
    container: AppContainer,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(Routes.ONBOARDING) {
            val viewModel: OnboardingViewModel = viewModel(
                factory = OnboardingViewModel.factory(container, navController),
            )
            OnboardingScreen(
                onContinue = viewModel::completeOnboarding,
            )
        }

        composable(Routes.STORE) {
            val viewModel: StoreViewModel = viewModel(
                factory = StoreViewModel.factory(container),
            )
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            StoreScreen(
                uiState = uiState,
                onOpenCategories = viewModel::openCategorySheet,
                onDismissCategories = viewModel::dismissCategorySheet,
                onSelectCategory = viewModel::selectCategory,
                onClearFilter = viewModel::clearFilter,
                onSearchChange = viewModel::setSearchQuery,
                onClearSearch = viewModel::clearSearch,
                onAppClick = { appId ->
                    navController.navigate(Routes.appDetails(appId))
                },
                onOpenProfile = { navController.navigate(Routes.PROFILE) },
            )
        }

        composable(Routes.PROFILE) {
            val viewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModel.factory(container),
            )
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onResetOnboarding = viewModel::resetOnboarding,
                onNavigateToOnboarding = {
                    navController.navigate(Routes.ONBOARDING) {
                        popUpTo(Routes.STORE) { inclusive = true }
                    }
                },
            )
        }

        composable(
            route = Routes.APP_DETAILS,
            arguments = listOf(navArgument("appId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId").orEmpty()
            val viewModel: AppDetailsViewModel = viewModel(
                factory = AppDetailsViewModel.factory(container, appId),
            )
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            AppDetailsScreen(
                uiState = uiState,
                events = viewModel.events,
                onBack = { navController.popBackStack() },
                onInstall = viewModel::install,
                onUninstall = viewModel::uninstall,
                onOpenInstalledApp = viewModel::openInstalledApp,
                onOpenInstalledAppById = { id ->
                    navController.navigate(Routes.installedApp(id))
                },
                onOpenScreenshots = { id, startIndex ->
                    navController.navigate(Routes.screenshots(id, startIndex))
                },
            )
        }

        composable(
            route = Routes.SCREENSHOTS,
            arguments = listOf(
                navArgument("appId") { type = NavType.StringType },
                navArgument("startIndex") {
                    type = NavType.IntType
                    defaultValue = 0
                },
            ),
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId").orEmpty()
            val startIndex = backStackEntry.arguments?.getInt("startIndex") ?: 0
            val viewModel: ScreenshotsViewModel = viewModel(
                factory = ScreenshotsViewModel.factory(container, appId, startIndex),
            )
            ScreenshotsScreen(
                uiState = viewModel.uiState,
                onClose = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.INSTALLED_APP,
            arguments = listOf(navArgument("appId") { type = NavType.StringType }),
        ) { backStackEntry ->
            val appId = backStackEntry.arguments?.getString("appId").orEmpty()
            val viewModel: InstalledAppViewModel = viewModel(
                factory = InstalledAppViewModel.factory(container, appId),
            )
            InstalledAppScreen(
                uiState = viewModel.uiState,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
