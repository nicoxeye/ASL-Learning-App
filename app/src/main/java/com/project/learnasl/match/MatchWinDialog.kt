package com.project.learnasl.match

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun MatchWinDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    elapsedTime: Int
) {

    Dialog(
        onDismissRequest = {
            onDismiss()
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
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "! You won !",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }

                Box(
                    modifier = Modifier.padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "it took you $elapsedTime seconds",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                ) {

                    Button(
                        onClick = { onDismiss() }
                    ) {
                        Text("Go Back")
                    }

                    Button(
                        onClick = { onConfirm() }
                    ) {
                        Text("Play Again")
                    }
                }
            }


        }
    }
}

@Preview
@Composable
fun MatchWinDialogPreview() {
    MatchWinDialog(
        onDismiss = { },
        onConfirm = { },
        16
    )
}