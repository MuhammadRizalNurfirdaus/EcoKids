package com.example.ecokids

data class Animal(
    val id: Int = 0,
    val name: String,
    val habitat: String,
    val description: String,
    val imageResId: Int
)

data class Fruit(
    val id: Int = 0,
    val name: String,
    val color: String,
    val description: String,
    val imageResId: Int
)

data class QuizQuestion(
    val id: Int = 0,
    val question: String,
    val optionA: String,
    val optionB: String,
    val optionC: String,
    val optionD: String,
    val correctAnswer: Int, // 0-3
    val level: String, // A, B, C, D
    val imageResId: Int = 0
)
