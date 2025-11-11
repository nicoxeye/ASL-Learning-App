package com.project.learnasl.QuestionActivity.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


// BASIC QUESTION MODEL: handles question number, question text,
// possible answers, image to display and user input
@Parcelize
data class QuestionModel(
    val id:Int,
    val question:String?,
    val answer_1:String?,
    val answer_2:String?,
    val answer_3:String?,
    val answer_4:String?,
    val correct_answer: String?,
    val score:Int,
    val img_path: String?,
    val clicked_answer: String?
): Parcelable
