package com.project.learnasl.match.data

import com.project.learnasl.R


data class AslPair(
    val drawing: Drawing,
    val label: String
)

val allAslPairs = listOf(
    AslPair(Drawing(R.drawable.asl_a, "A"), "A"),
    AslPair(Drawing(R.drawable.asl_b, "B"), "B"),
    AslPair(Drawing(R.drawable.asl_c, "C"), "C"),
    AslPair(Drawing(R.drawable.asl_d, "D"), "D"),
    AslPair(Drawing(R.drawable.asl_e, "E"), "E")
)
