package com.project.learnasl.Dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.ui.theme.LearnASLTheme


@Composable
//@Preview
fun CardGrid(
    onQuizClick: () -> Unit,
    onFlashcardsClick: () -> Unit,
    onMatchClick: () -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column (modifier = modifier) {
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            OptionCard("Quiz", Modifier.weight(1f).padding(start=24.dp, end=12.dp,
                top=16.dp),
                onClick = onQuizClick
            )
            OptionCard("Flashcards", Modifier.weight(1f).padding(start=12.dp, end=24.dp,
                top=16.dp),
                onClick = onFlashcardsClick
            )
        }
        Spacer(Modifier.padding(top = 8.dp))
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            OptionCard("Match", Modifier.weight(1f).padding(start=24.dp, end=12.dp,
                top=16.dp),
                onClick = onMatchClick
            )
            OptionCard("Camera", Modifier.weight(1f).padding(start=12.dp, end=24.dp,
                top=16.dp),
                onClick = onCameraClick
            )
        }
    }
}

@Composable
fun OptionCard(title:String,
               modifier: Modifier = Modifier,
               onClick: () -> Unit
) {
    LearnASLTheme {
        Row(
            modifier = modifier
                .height(55.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(start=16.dp)
                .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}