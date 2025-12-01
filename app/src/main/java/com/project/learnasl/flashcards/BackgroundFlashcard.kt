package com.project.learnasl.flashcards

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.project.learnasl.flashcards.model.Flashcard

@Composable
fun BackgroundFlashcard(
    flashcard: Flashcard,
    stackIndex: Int,
    isSwiping: Boolean
) {
    // BASE VALUES
    // every background flashcard has to be a bit HIGHER than the previous one
    // 50f is a constant height of the background flashcard's top edge (the edge that sticking out)
    val translationY = -stackIndex * 50f
    // every background flashcard has to be a bit SMALLER than the previous one
    val scale = 1f - (stackIndex * 0.1f)

    // background flashcards are blurry; the further they are, the blurrier they get
    // flashcard which stackIndex == 1 (the one right behind the flashcard on top)
    // is fully visible IF the user is swiping the card at the top
    val alpha = if (isSwiping && stackIndex == 1) {
        1f
    }
    else {
        1f - (stackIndex * 0.2f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            // wanted the stack to be a bit higher
            .padding(bottom = 65.dp)
            .graphicsLayer{
                cameraDistance = 12f
                this.translationY = translationY
                scaleX = scale
                this.alpha = alpha
            },
        contentAlignment = Alignment.Center
    ) {
        // display only the image (front of the flashcard)
        Image(
            painter = painterResource(flashcard.imageRes),
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .border(color = MaterialTheme.colorScheme.primary, width = 3.dp, shape = RoundedCornerShape(20.dp)),
            contentDescription = null
        )
    }
}

