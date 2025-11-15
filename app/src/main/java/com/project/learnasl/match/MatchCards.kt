package com.project.learnasl.match

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MatchCardImage(
    imageRes: Int,
    isSelected: Boolean,
    isSolved: Boolean,
    onClick: () -> Unit
) {

    val bg by animateColorAsState( when {
        isSolved -> MaterialTheme.colorScheme.secondaryContainer //green
        isSelected -> MaterialTheme.colorScheme.tertiaryContainer //yellow
        else -> MaterialTheme.colorScheme.primaryContainer //red
    }
    )

    val borderColor = when {
        isSolved -> MaterialTheme.colorScheme.secondary
        isSelected -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }

    Surface(
        modifier = Modifier
            .size(120.dp)
            .clickable(enabled = !isSolved) { onClick() },
        color = bg,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, borderColor),
        tonalElevation = if (isSolved) 8.dp else 4.dp,
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .padding(12.dp)
        )
    }
}


@Composable
fun MatchCardText(
    text: String,
    isSelected: Boolean,
    isSolved: Boolean,
    onClick: () -> Unit
) {

    val bg by animateColorAsState( when {
        isSolved -> MaterialTheme.colorScheme.secondaryContainer //green
        isSelected -> MaterialTheme.colorScheme.tertiaryContainer //yellow
        else -> MaterialTheme.colorScheme.primaryContainer //red
    }
    )

    val borderColor = when {
        isSolved -> MaterialTheme.colorScheme.secondary
        isSelected -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.primary
    }

    Surface(
        modifier = Modifier
            .size(120.dp)
            .clickable(enabled = !isSolved) { onClick() },
        color = bg,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(3.dp, borderColor),
        tonalElevation = if (isSolved) 8.dp else 4.dp
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}