package com.project.learnasl.ScoreActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.R
import androidx.compose.ui.platform.LocalContext
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
fun ScoreScreen(score: Int, onBackToMain:()->Unit)
{
    LearnASLTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.trophy),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    // add this to res pls
                    text="YOUR SCORE IS: ",
                    color=MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 20.sp,
                )
                Text(
                    text= "$score/5",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(top=8.dp)
                )
                Button(
                    onClick = onBackToMain,
                    modifier = Modifier
                        .padding(top=16.dp),
                    colors = ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.primaryContainer
                    ), shape= RoundedCornerShape(8.dp)
                ) {
                    Text(text="Back to Main",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style= MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}



@Preview
@Composable
fun ScoreScreenPreview(){
    ScoreScreen(score=5, onBackToMain = {})
}