package com.project.learnasl.QuestionActivity

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.QuestionActivity.Model.QuestionModel
import com.project.learnasl.QuestionActivity.Model.QuestionUiState
import com.project.learnasl.QuestionActivity.components.AnswerItem
import com.project.learnasl.R
import com.project.learnasl.ui.theme.LearnASLTheme

// QUIZ UI AND LOGIC: displays current question from previously
// defined question list, takes user input and tracks their progress, shows correct answer
// immediately
@SuppressLint("DiscouragedApi")
@Composable
fun QuestionScreen(
    questions:List<QuestionModel>,
    onFinish:(finalScore:Int)->Unit,
    onBackClick: ()->Unit
) {
    LearnASLTheme {
        var state by remember {
            mutableStateOf(
                value = QuestionUiState(questions = questions)
            )
        }

        val currentQuestion = state.questions[state.currentIndex]
        var selectedAnswer = currentQuestion.clicked_answer
        val context = LocalContext.current
        val imageResID = remember(key1 = currentQuestion.img_path) {
            context.resources.getIdentifier(
                currentQuestion.img_path ?: "",
                "drawable",
                context.packageName
            )
        }

        /*
     containerColor = MaterialTheme.colorScheme.primary, // background
     contentColor = MaterialTheme.colorScheme.onPrimary // text
     */

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                // header
                Row(
                    modifier = Modifier.padding(top = 70.dp, start = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {    // go back button
                    IconButton(onClick = onBackClick ) {
                        Icon(
                            painter = painterResource(R.drawable.go_back),
                            contentDescription = "Go back button",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    // header text
                    Text(
                        text = "Quiz mode",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        //fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            item {
                // questions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // questions counter 1/num_of_questions
                    Text(
                        text = "Question ${state.currentIndex + 1}/5",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                    // buttons that enable user to move through questions (back and next)
                    IconButton(
                        onClick = {
                            if (state.currentIndex > 0) {
                                selectedAnswer = null
                                state = state.copy(currentIndex = state.currentIndex - 1)
                            }
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.left_arrow),
                            contentDescription = String(),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                    IconButton(
                        onClick = {
                            // when index is last question
                            if (state.currentIndex == 4) {
                                onFinish(state.score)
                            } else {
                                selectedAnswer = null
                                state = state.copy(currentIndex = state.currentIndex + 1)
                            }
                        }
                    ) {
                        Icon(
                            painterResource(R.drawable.right_arrow),
                            contentDescription = String(),
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }
                }
            }
            // progress bar
            item {
                LinearProgressIndicator(
                    progress = { (state.currentIndex + 1) / 5f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(14.dp)
                        .clip(RoundedCornerShape(50)),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    // ProgressIndicatorDefaults.linearTrackColor
                    trackColor = MaterialTheme.colorScheme.primary,
                    strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
                )
            }
            // question text
            item {
                Text(
                    text = currentQuestion.question ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            // sign image
            item {
//            Column(
//                modifier = Modifier.fillMaxHeight(),
//                verticalArrangement = Arrangement.Center
//            )
//            {  }
                Image(
                    painterResource(imageResID),
                    contentDescription = "Sign for a letter",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        //.width(200.dp)
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            itemsIndexed(
                listOf(
                    currentQuestion.answer_1 ?: "",
                    currentQuestion.answer_2 ?: "",
                    currentQuestion.answer_3 ?: "",
                    currentQuestion.answer_4 ?: "",
                )
            )
            { index, answerText ->
                val answerLetter = listOf("a", "b", "c", "d")[index] // possible answers
                val isCorrect = selectedAnswer != null
                        && answerLetter == currentQuestion.correct_answer
                val isWrong = selectedAnswer == answerLetter && !isCorrect

                AnswerItem(
                    text = answerText,
                    isCorrect = isCorrect,
                    isWrong = isWrong,
                    isSelected = selectedAnswer != null
                ) {
                    val updatedQuestion = state.questions.toMutableList()
                    val updateQuestion =
                        updatedQuestion[state.currentIndex].copy(
                            clicked_answer = answerLetter
                        )
                    updatedQuestion[state.currentIndex] = updateQuestion
                    val scoreToAdd = if (answerLetter == updateQuestion.correct_answer)
                        1 else 0 // give 1 point for each correct answer
                    state = state.copy(
                        questions = updatedQuestion,
                        score = state.score + scoreToAdd
                    )
                }
            }
            item {
                Spacer(Modifier.height(32.dp))
            }
        }
    }
}

// ------
// test question for me too see if UI formats correctly :)
@Preview
@Composable
fun QuestionScreenPreview(){
    val questions = listOf(
        QuestionModel(
            id = 1,
            question = "What sign is this?",
            answer_1 = "Letter B",
            answer_2 = "Letter D",
            answer_3 = "Letter A",
            answer_4 = "Letter G",
            correct_answer = "Letter B",
            score = 10,
            img_path = "letter_a",
            clicked_answer = null
        ))
    QuestionScreen(questions = questions, onFinish = {}, onBackClick = {})
}