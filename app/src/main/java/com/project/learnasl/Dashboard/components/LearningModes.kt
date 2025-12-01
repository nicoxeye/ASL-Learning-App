package com.project.learnasl.Dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.learnasl.R
import com.project.learnasl.ui.theme.LearnASLTheme


@Composable
@Preview
fun LearningModesButtons(
    // button logic here when implementing
){
    Row (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp)
        .height(145.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        LearnButton(
            icon_resource = R.drawable.alphabet_icon,
            text = "Learn Alphabet",
            modifier = Modifier
                .weight(1f)
        )
        Spacer(Modifier.width(12.dp))
        LearnButton(
            icon_resource = R.drawable.number_icon,
            text = "Learn Numbers",
            modifier = Modifier
                .weight(1f)
        )
        Spacer(Modifier.width(12.dp))
        LearnButton(
            icon_resource = R.drawable.mixed_icon,
            text = "Mixed Mode",
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun LearnButton(
    icon_resource: Int,
    text: String,
    onClick:(()->Unit)?=null,
    modifier: Modifier = Modifier
) {
    LearnASLTheme {
        Column(
            modifier = modifier
                .fillMaxHeight()
                .clickable(enabled = onClick != null) { onClick?.invoke() }
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = icon_resource),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.labelLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
