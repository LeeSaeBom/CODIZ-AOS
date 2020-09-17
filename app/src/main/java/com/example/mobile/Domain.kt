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
    FOR("For"),
    WHILE("While"),
    FUNCTION("함수"),
    ONE_DIMENSION_ARRAY("1차원 배열"),
    MULTI_DIMENSION_ARRAY("다차원 배열");
}