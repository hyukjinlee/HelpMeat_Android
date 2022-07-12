package com.project.helpmeat.constant

class Constants {
    companion object {
        const val MEAT_TYPE_STANDARD = 100

        fun getMeatType(meatValue: Int): MeatType {
            return when (meatValue / MEAT_TYPE_STANDARD * MEAT_TYPE_STANDARD) {
                MeatType.MEAT_TYPE_BEEF.value -> {
                    MeatType.MEAT_TYPE_BEEF
                }
                MeatType.MEAT_TYPE_FORK.value -> {
                    MeatType.MEAT_TYPE_FORK
                }
                else -> {
                    MeatType.MEAT_TYPE_ERROR
                }
            }
        }

        fun getMeatIndex(meatValue: Int) = meatValue % MEAT_TYPE_STANDARD
    }

    enum class MeatType(val value: Int) {
        MEAT_TYPE_ERROR(-1),
        MEAT_TYPE_FORK(100),
        MEAT_TYPE_BEEF(200)
    }
}