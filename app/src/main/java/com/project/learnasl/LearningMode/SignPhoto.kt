package com.project.learnasl.LearningMode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.R
import com.project.learnasl.match.data.AslPair
import com.project.learnasl.match.data.Drawing
import com.project.learnasl.ui.theme.LearnASLTheme

//val drawing: Drawing,
//val label: String

@Composable
fun SignPhoto(item: AslPair,
              letOrNum: String) {
    LearnASLTheme {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp)
        )
        {
            Image(
                painter = painterResource(id = item.drawing.imageRes),
                contentDescription = "item.drawing",
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                // contentScale = ContentScale.Crop
            )
            Text(
                text = "$letOrNum ${item.label}",
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
            DashedDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(26.dp)
            )
        }
    }
}

@Preview
@Composable
fun Preview(){
    val asl_pair = AslPair(Drawing(R.drawable.asl_a,
        "A"), "A")
    SignPhoto( asl_pair, "Letter")
}