package com.example.mobile

data class ProblemCreateResponse(
    val problem: Problem
)

data class ProblemFrameCreateResponse(
    val problemFrame: ProblemFrame
)

data class ProblemFrameListResponse(
    val problemFrames: List<ProblemFrame>
)

data class ProblemFrameResponse(
    val problemFrame: ProblemFrame,
    val problems: List<Problem>
)

data class GrammerResponse(
    val grammers: List<Grammer>
)
