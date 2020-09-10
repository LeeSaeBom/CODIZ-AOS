package com.example.mobile

data class User(
    val userId: String,
    val userPassword: String,
    val userName: String,
    val userEmail: String
)

data class Problem(
    val id: Long,
    val problemFrame: ProblemFrame,
    val problemType: ProblemType,
    val problemDescription: String,
    val problemNumber: Long,
    val problemAnswer: Long,
    val problemAnswerDescriptionOne: String,
    val problemAnswerDescriptionTwo: String,
    val problemAnswerDescriptionThree: String,
    val problemAnswerDescriptionFour: String
)

data class ProblemFrame(
    val id: Long,
    val problemName: String,
    val problemFrameDescription: String,
    val problemType: ProblemType
)

enum class ProblemType(val problemName: String) {
    IF("If"),
    WHILE("While"),
    FOR("For"),
    ARRAY("Array");
}