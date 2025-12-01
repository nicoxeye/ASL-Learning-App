package com.project.learnasl.Dashboard.components

//import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.ui.theme.LearnASLTheme

@Composable
//@Preview
fun Banner(
    name: String,
    path: Int
){
    LearnASLTheme {
        ExampleBox(shape = RoundedCornerShape(10.dp), name, path)
    }
}

@Composable
fun ExampleBox(shape: Shape,
               name: String,
               path : Int) {
    LearnASLTheme {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .width(340.dp)
                    .height(200.dp)
                    .clip(shape)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = shape
                    )
                    .padding(24.dp)
            ) {
                Row{
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        //horizontalAlignment = AbsoluteAlignment.Left
                    ) {
                        Text(
                            text = "New word",
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "Letter $name",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            style = MaterialTheme.typography.labelLarge
                        )
                        Spacer(Modifier.height(14.dp))
                        Row(
                            modifier = Modifier
                                .height(40.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Go practice",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    }
                    Image(
                        painterResource(path),
                        contentDescription = "Sign for a letter",
                        //contentScale = ContentScale.Inside,

                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .weight(1f)
                    )
                }
            }
        }
    }
}
