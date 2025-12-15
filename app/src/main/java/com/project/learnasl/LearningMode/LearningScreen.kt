package com.project.learnasl.LearningMode

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.learnasl.R
import com.project.learnasl.data.AslPair
import com.project.learnasl.data.Drawing
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
fun LearningScreen(
    asl_pairs: List<AslPair>,
    onBackClick: () -> Unit,
    letOrNum: String
) {
    LearnASLTheme {
        Scaffold(
            topBar = {
                TopBar("Learning mode",onBackClick)
            }
        ) { innerPadding ->
            LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {

            // header

//                Row(
//                    modifier = Modifier.padding(top = 70.dp, start = 24.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    IconButton(onClick = onBackClick) {
//                        Icon(
//                            painter = painterResource(R.drawable.go_back),
//                            contentDescription = "Go back button",
//                            tint = MaterialTheme.colorScheme.onBackground
//                        )
//                    }
//
//                    Spacer(Modifier.width(16.dp))
//
//                    Text(
//                        text = "Learning mode",
//                        fontSize = 20.sp,
//                        color = MaterialTheme.colorScheme.onBackground,
//                        style = MaterialTheme.typography.labelLarge
//                    )
//                }


            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
            // scrollable photo list
            items(items = asl_pairs) { pair ->
                SignPhoto(pair, letOrNum)
            }
        }
        }
    }
}


// https://forum.devtalk.com/t/my-sdk-dont-see-a-function-scrollcontent/143510/3
@Composable
fun ScrollContent(innerPadding: PaddingValues) {
    val range = 1..100
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(range.count()) { index ->
            Text(text = "- List item number ${index + 1}")
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
    LearningScreen(pairs, onBackClick = {}, "Letter")
}
