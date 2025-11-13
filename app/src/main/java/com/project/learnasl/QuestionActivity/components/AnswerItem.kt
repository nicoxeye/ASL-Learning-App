package com.project.learnasl.QuestionActivity.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.R
import com.project.learnasl.ui.theme.LearnASLTheme

// ANSWERS UI: handles how each answer is displayed, all start with default light color and
// depending on user's pick they change accordingly (green-correct, red-wrong)
// its used in QuestionScreen
@Composable
fun AnswerItem(
    text: String,
    isCorrect: Boolean=false,
    isWrong: Boolean=false,
    isSelected: Boolean = false,
    onClick: ()-> Unit,
) {
    LearnASLTheme {
        val backgroundColor = when {
            isCorrect -> MaterialTheme.colorScheme.secondary
            isWrong -> MaterialTheme.colorScheme.error
            else -> MaterialTheme.colorScheme.primaryContainer
        }
        val icon = when {
            isCorrect -> painterResource(R.drawable.correct)
            isWrong -> painterResource(R.drawable.wrong)
            else -> null
        }
        // for text visibility
        val textColor = if (isCorrect || isWrong) MaterialTheme.colorScheme.onSecondary
                        else MaterialTheme.colorScheme.onPrimaryContainer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .clickable(enabled = !isSelected) { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    color = textColor,
                    modifier = Modifier.weight(1f)
                )
                icon?.let {
                    Icon(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}


// only for me for checking how UI formats:)
@Preview
@Composable
fun NormalAnswerItemPreview(){
    AnswerItem(text="Neutral Answer", isCorrect = false, isWrong = false, onClick = {})
}

@Preview
@Composable
fun CorrectAnswerItemPreview(){
    AnswerItem(text="Correct Answer", isCorrect = true, onClick = {})
}

@Preview
@Composable
fun WrongAnswerItemPreview(){
    AnswerItem(text="Wrong Answer", isCorrect = false, isWrong = true, onClick = {})
}


