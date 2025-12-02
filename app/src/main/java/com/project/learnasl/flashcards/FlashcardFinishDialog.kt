package com.project.learnasl.flashcards

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

// POP-UP AFTER FINISHING FLASHCARDS' STACK
@Composable
fun FlashcardFinishDialog(
    onBackToMenu: () -> Unit,
    onRepeatWholeSet: () -> Unit,
    onRepeatStillLearning: () -> Unit,
    totalCards: Short,
    knownCount: Short,
    stillLearningCount: Short,
    showRepeatWholeSetButton: Boolean = true,
    showStillLearningButton: Boolean = true
) {
    Dialog(
        onDismissRequest = {
            onBackToMenu()
        }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                // header
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Great job!",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }
                // flashcards summary
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "You went through all $totalCards flashcards :)",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }
                // if NOT in still learning mode -> display stats
                if (showStillLearningButton || showRepeatWholeSetButton) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Already know: $knownCount\n Still learning: $stillLearningCount",
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = { onBackToMenu() }
                    ) {
                        Text("Back to menu")
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = { onRepeatWholeSet() }
                    ) {
                        Text("Repeat whole set")
                    }
                }
                if (showStillLearningButton && stillLearningCount > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Button(onClick = { onRepeatStillLearning() }) {
                            Text("Learn $stillLearningCount flashcards")
                        }
                    }
                }
            }
        }
    }

}