package com.project.learnasl.pages

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.Dashboard.components.Banner
import com.project.learnasl.data.allLettersAslPairs
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.MATCH_EXP

@Composable
fun UserPage(
    username: String,
    experience: Int,
    level: Int
) {
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

        Spacer(modifier = Modifier.height(48.dp))
        Banner(label, image)
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
        level = 4)
}


