package com.project.helpmeat.navigator

enum class AppScreens {
    INTRO,
    MAIN,
}

interface AppNavigator {
    // Navigate to a given screen.
    fun navigateTo(screen: AppScreens)
}