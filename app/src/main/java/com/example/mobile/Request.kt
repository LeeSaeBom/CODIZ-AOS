package com.example.mobile

data class SignInRequest(
    val userId: String,
    val userPassword: String
)

data class SignUpRequest(
    val user: User
)

data class ProblemFrameRequest(
    val problemName: String,
    val problemFrameDescription: String,
    val problemType: ProblemType,
    val problemFrame: ProblemFrame
)

data class ProblemPutRequest(
    val problem: Problem
)

data class GrammerRequest(
    val grammer: Grammer
)
