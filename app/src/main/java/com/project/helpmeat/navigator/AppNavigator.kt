package com.project.helpmeat.navigator

enum class AppScreens {
    INTRO,
    INIT,
    MAIN,
}

enum class Anim {
    FADE,
    SLIDE,
}

interface AppNavigator {
    // Navigate to a given screen.
    fun navigateTo(screen: AppScreens, anim: Anim)

    fun back()
}