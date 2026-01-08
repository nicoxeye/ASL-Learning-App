package com.project.learnasl.camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.R
import com.project.learnasl.flashcards.Category
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
fun CameraCategories (
    onBackClick: () -> Unit,
    onCategoryClick: (value: Int) -> Unit
) {
    LearnASLTheme(
        //darkTheme = true
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // back to menu button
            item {
                Row(
                    modifier = Modifier.padding(top = 70.dp, start = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(R.drawable.go_back),
                            contentDescription = "Back to menu"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Camera Categories",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }


            // categories list
            item {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Category(
                            R.drawable.star,
                            "Live Hands Detection",
                            onClick = {
                                onCategoryClick(1)
                            },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Category(
                            R.drawable.star,
                            "AI Quiz",
                            onClick = {
                                onCategoryClick(2)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }

                }
            }
        }
    }
}