package com.project.learnasl.QuestionActivity.Model

// helper function for keeping score of current screen progress
data class QuestionUiState(
    val questions:List<QuestionModel>,
    val currentIndex: Int=0,
    val score: Int=0
)
