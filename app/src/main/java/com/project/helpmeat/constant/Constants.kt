package com.project.helpmeat.constant

class Constants {
    companion object {
        const val MEAT_TYPE_STANDARD = 100
    }

    enum class MeatType(val value: Int) {
        MEAT_TYPE_FORK(100),
        MEAT_TYPE_BEEF(200)
    }
}