package com.rsschool.quiz.questions

class ListQuestions {
    companion object {
        val listQuestions = arrayOf(
            Question(
                1,
                "Какой из представленных университетов не входит в «Лигу плюща»?",
                arrayListOf(
                    "Йельский университет",
                    "Гарвардский университет",
                    "Дартмутский университет",
                    "Питтсбургский университет",
                    "Нет правильного ответа"
                ),
                "Дартмутский университет"
            ),
            Question(
                2,
                "Что из перечисленного не овощ и не фрукт?",
                arrayListOf("Арбуз", "Помидор", "Яблоко", "Ананас", "Нет правильного ответа"),
                "Арбуз"
            ),
            Question(
                3,
                "Пятая планета от Солнца?",
                arrayListOf("Миркурий", "Замля", "Юпитер", "Марс", "Нет правильного ответа"),
                "Юпитер"
            ),
            Question(
                4,
                "Чем занимаются домашние кошки большую часть жизни?",
                arrayListOf("Спят", "Играют", "Мурлячат", "Едят", "Нет правильного ответа"),
                "Спят"
            ),
            Question(
                5,
                "Марс и Венера это боги какого народа?",
                arrayListOf(
                    "Римские боги",
                    "Греческие боги",
                    "Египетские боги",
                    "Скандинавские боги",
                    "Нет правильного ответа"
                ),
                "Римские боги"
            )
        )
    }
}