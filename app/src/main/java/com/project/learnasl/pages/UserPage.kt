package com.project.learnasl.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.Dashboard.components.Banner
import com.project.learnasl.data.allLettersAslPairs
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.MATCH_EXP
import kotlinx.coroutines.launch

@Composable
fun UserPage(
    username: String,
    experience: Int,
    level: Int,
    resetExp: suspend () -> Unit,
    editName: suspend (String) -> Unit
) {

    val scope = rememberCoroutineScope()
    var showDialogEditName by remember { mutableStateOf(false) } // toggling the edit name diaglog
    var showDialogReset by remember { mutableStateOf(false) }
    var newName by remember { mutableStateOf("") } // storing input

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "PROFILE",
            fontSize = 48.sp,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Icon",
            modifier = Modifier.scale(3F)
        )
        Spacer(modifier = Modifier.height(36.dp))
        Text(
            username,
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "level $level",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        val minXP = xpForLevel(level)
        val maxXP = xpForLevel(level + 1)
        val neededForNext = maxXP - minXP
        val progressXP = experience - minXP
        val progress = (progressXP.toFloat() / neededForNext)
        val xpForNextLvl = neededForNext - progressXP

        // shows level progress
        LinearProgressIndicator(
            progress = { progress },
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.width(200.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "$progressXP / $neededForNext",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "experience points until next level",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        // temporary, just to see if variables are correct
        /*
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                "XP FOR NEXT LEVEL: $xpForNextLvl",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
             */
        val ASLpair = allLettersAslPairs.shuffled().first()
        val label = ASLpair.label
        val image = ASLpair.drawing.imageRes

        Spacer(modifier = Modifier.height(64.dp))
        //Banner(label, image)

        CustomButton2(
            text = "Reset experience progress",
            onClick = {
                showDialogReset = true
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        CustomButton2(
            text = "Edit Name",
            onClick = {
                showDialogEditName = true
            }
        )
        Spacer(modifier = Modifier.height(32.dp))

    }

    // edit name dialog
    if (showDialogEditName) {
        AlertDialog(
            onDismissRequest = { showDialogEditName = false },
            title = { Text("Edit your name") },
            text = {
                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    placeholder = { Text("Enter new name") }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            editName(newName)
                        }
                        showDialogEditName = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialogEditName = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }


    if (showDialogReset) {
        AlertDialog(
            onDismissRequest = { showDialogReset = false },
            title = { Text("Are you sure?") },
            text = {
                Text(
                    text = "Resetting progress will set your experience points to 0."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            resetExp()
                        }
                        showDialogReset= false
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialogReset = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

}



@Composable
fun CustomButton2(text: String, onClick: () -> Unit) {

    Button(
        onClick = { onClick() },
        modifier = Modifier.width(300.dp)
            .height(55.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = text,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    }

}


// refactored the formula to calculate the xp needed for the next level
fun xpForLevel(level: Int): Int {
    return 25 * level * level - 25 * level
}

@Composable
@Preview(showBackground = true)
fun UserPagePreview() {
    UserPage(username = "Nick",
        experience = 300,
        level = 4,
        resetExp = { },
        editName = { })
}


