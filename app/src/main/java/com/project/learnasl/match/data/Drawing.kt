package com.project.learnasl.match.data

// possibly rename this,
// label exists so comparing between the real label (chosen by user, can be different from this one) in a pair is possible
data class Drawing(
    val imageRes: Int,
    val label: String
)