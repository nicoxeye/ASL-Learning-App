package com.project.learnasl.utils

import android.content.Context
import android.content.Intent
import com.project.learnasl.FlashcardsActivity
import com.project.learnasl.MatchActivity
import com.project.learnasl.QuizActivity

fun startQuizActivity(context: Context) {
    context.startActivity(Intent(context, QuizActivity::class.java))
}

fun startFlashcardsActivity(context: Context) {
    context.startActivity(Intent(context, FlashcardsActivity::class.java))
}

fun startMatchActivity(context: Context) {
    context.startActivity(Intent(context, MatchActivity::class.java))
}