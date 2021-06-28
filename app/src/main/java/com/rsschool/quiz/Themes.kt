package com.rsschool.quiz

enum class Themes(val theme: Int,
                  val color: Int) {
    THEME_FIRST(R.style.Theme_Quiz_First, R.color.deep_orange_100_dark),
    THEME_SECOND(R.style.Theme_Quiz_Second, R.color.yellow_100_dark),
    THEME_THIRD(R.style.Theme_Quiz_Third, R.color.light_green_100_dark),
    THEME_FOURTH(R.style.Theme_Quiz_Fourth, R.color.cyan_100_dark),
    THEME_FIVES(R.style.Theme_Quiz_Fives, R.color.deep_purple_100_dark)
}