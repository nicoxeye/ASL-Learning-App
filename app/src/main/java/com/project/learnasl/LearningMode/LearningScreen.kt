package com.project.learnasl.LearningMode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.QuestionActivity.Model.QuestionModel
import com.project.learnasl.QuestionActivity.Model.QuestionUiState
import com.project.learnasl.QuestionActivity.QuestionScreen
import com.project.learnasl.R
import com.project.learnasl.match.data.AslPair
import com.project.learnasl.match.data.Drawing
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
fun LearningScreen(
    asl_pairs: List<AslPair>,
    onBackClick: () -> Unit
) {
    LearnASLTheme {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            // header
            item {
                Row(
                    modifier = Modifier.padding(top = 70.dp, start = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.go_back),
                            contentDescription = "Go back button",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(Modifier.width(16.dp))

                    Text(
                        text = "Learning mode",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            // scrollable photo list
            items(items = asl_pairs) { pair ->
                SignPhoto(pair)
            }
        }
    }
}


@Preview
@Composable
fun LearningScreenPreview(){
    val pairs = listOf(
        AslPair(Drawing(R.drawable.asl_a,
        "A"), "A"),
        AslPair(Drawing(R.drawable.asl_b,
        "B"), "B")
    )
    LearningScreen(pairs, onBackClick = {})
}