package com.project.learnasl.utils

import android.content.Context
import android.content.Intent
import com.project.learnasl.FlashcardsActivity
import com.project.learnasl.LearningMode.LearnAlphabetActivity
import com.project.learnasl.LearningMode.LearnNumbersActivity
import com.project.learnasl.MainActivity
import com.project.learnasl.MatchActivity
import com.project.learnasl.QuizActivity
import com.project.learnasl.UserCreationActivity

fun startMainActivity(context: Context) {
    context.startActivity(Intent(context, MainActivity::class.java))
}
fun startQuizActivity(context: Context) {
    context.startActivity(Intent(context, QuizActivity::class.java))
}

fun startFlashcardsActivity(context: Context) {
    context.startActivity(Intent(context, FlashcardsActivity::class.java))
}

fun startMatchActivity(context: Context) {
    context.startActivity(Intent(context, MatchActivity::class.java))
}

// skeleton function for testing - for now only redirects to main
fun startCameraActivity(context: Context) {
    context.startActivity(Intent(context, MainActivity::class.java))
}

fun startUserCreationActivity(context: Context) {
    context.startActivity(Intent(context, UserCreationActivity::class.java))
}

fun startAlphabetModeActivity(context: Context){
    context.startActivity(Intent(context, LearnAlphabetActivity::class.java))
}

fun startNumbersModeActivity(context: Context){
    context.startActivity(Intent(context, LearnNumbersActivity::class.java))
}