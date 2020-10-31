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
    INPUT_OUTPUT("입출력"),
    VARIABLE_DATATYPE("변수와 자료형"),
    FOR("수식과 연산"),
    IF("조건문"),
    WHILE("반복문"),
    ONE_DIMENSION_ARRAY("배열"),
    FUNCTION("함수"),
    MULTI_DIMENSION_ARRAY("포인터"),
    STRING("문자열"),
    STRUCTURE("구조체"),
    RECURSIVE("재귀"),
    SORT("정렬"),
    BACKTRACKING("백트래킹"),
    DYNAMIC_PROGRAMMING("동적프로그래밍");
}

data class Grammer(
    val description: String,
    val grammerName: String,
    val grammerType: GrammerType
)

enum class GrammerType {

}