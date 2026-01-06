package com.project.learnasl.flashcards

import com.project.learnasl.R
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import com.project.learnasl.flashcards.model.FlashcardsState
import kotlin.math.abs
import kotlin.math.min

// INCLUDES:
// - flashcard UI (back/front),
// - LOGIC:
//      * flashcard flip animation,
//      * swipe left/right animation (+ while swiping: blurry layer with "KNOW"/"STILL LEARNING")

@Composable
fun FlashcardFront(image: Int,
                   onClick: () -> Unit, // flip animation
                   isFavourite: Boolean,
                   onFavouriteClick: () -> Unit) // toggle favourite
{
    Box (
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clip(RoundedCornerShape(20.dp))
            // added border to make sure the stack is more visible (devilish smile)
            .border(color = MaterialTheme.colorScheme.primary, width = 3.dp, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(image),
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentDescription = null
        )

        // 'favourite' icon
        FavouriteIcon(isFavourite, onFavouriteClick)
    }
}


@Composable
fun FlashcardBack(text: String,
                  onClick: () -> Unit,
                  isFavourite: Boolean,
                  onFavouriteClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clip(RoundedCornerShape(20.dp))
            .border(color = MaterialTheme.colorScheme.primary, width = 3.dp, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() }
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            fontSize = 70.sp
        )

        // 'favourite' icon
        FavouriteIcon(isFavourite, onFavouriteClick)
    }
}

// FLIP STATE
enum class CardFace(val angle: Float) {
    Front(0f),
    Back(180f);

    fun flipped() = if (this == Front) Back else Front
}

// AUXILIARY FUNCTION (drawing overlay depending on flashcard state (FRONT vs BACK))
@Composable
fun BoxScope.SwipeOverlay(offsetX: Float, overlayAlpha: Float, isFront: Boolean) {
    val text = when {
        isFront && offsetX > 0f -> "KNOW"
        isFront && offsetX < 0f -> "STILL LEARNING"
        !isFront && offsetX < 0f -> "STILL LEARNING"
        !isFront && offsetX > 0f -> "KNOW"
        else -> null
    } ?: return

    val color =
        if (text == "KNOW") { MaterialTheme.colorScheme.secondary }
        else { MaterialTheme.colorScheme.primary }

    val fontSize = if (text == "KNOW") 32.sp else 24.sp

    Text(
        text=text,
        color = color,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .align(Alignment.Center)
            .graphicsLayer {
                alpha = overlayAlpha
                // to make sure "KNOW" and "STILL LEARNING" is displayed correctly
                if (!isFront) rotationY = 180f
            }
    )
}

// SWIPE LEFT/RIGHT + FLIP CARD ANIMATION
// right -> know, left -> still learning

@Composable
fun FlashcardAnimation(flashcardsState: FlashcardsState,
                       onFinished: () -> Unit,
                       addExp: () -> Unit) {
    // FLIP FRONT/BACK
    var face by remember { mutableStateOf(CardFace.Front) }

    // flip animation
    val rotation by animateFloatAsState(
        targetValue = face.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        ),
        label = "cardRotation"
    )

    // SWIPE
    val swipeThreshold = 250f
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    val isFront = face == CardFace.Front
    val effectiveOffsetX = if (isFront) offsetX.value else -offsetX.value
    // how much we swiped (0-1)
    val swipeProgress = min(abs(effectiveOffsetX) / swipeThreshold, 1f)
    // blur is dependent on swipeProgress (the more is swiped, the blurrier card gets)
    val blurRadius = 10.dp * swipeProgress
    // text overlay also dependent on swipeProgress (0-1) (invisible -> translucent -> fully visible)
    val overlayAlpha = swipeProgress

    val currentFlashcard = flashcardsState.currentFlashcard

    // no flashcards left + add pop-up
    if (currentFlashcard == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text("No flashcards available")
        }
        return
    }

    // favourite flashcards logic
    val isCurrentFavourite = currentFlashcard.isFavourite
    val onFavouriteToggle: () -> Unit = {
        flashcardsState.toggleFavourite(currentFlashcard)
    }

    // OUTER BOX: handles position/rotation/swiping animation
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f / 3f)
            .graphicsLayer {
                translationX = effectiveOffsetX
                rotationZ = offsetX.value / 25
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .pointerInput(flashcardsState.currentFlashcard) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        flashcardsState.isSwiping = true
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    },
                    onDragEnd = {
                        flashcardsState.isSwiping = false
                        scope.launch {
                            // after gesture is finished -> take current flashcard
                            val card = flashcardsState.currentFlashcard
                            if (card == null) {
                                offsetX.animateTo(0f, spring())
                                return@launch
                            }

                            // swipe right + add to "already_know" list
                            if (offsetX.value > swipeThreshold) {
                                // swipe right animation -> before handling logic and moving index
                                offsetX.animateTo(1000f, tween(300))

                                flashcardsState.markAsAlreadyKnown(currentFlashcard)
                                addExp() // adds experience points if flashcard marked as already known

                                // check if it's the last flashcard
                                if (flashcardsState.currentIndex == flashcardsState.currentFlashcards.lastIndex) {
                                    onFinished()
                                } else {
                                    flashcardsState.moveToNext()
                                }

                                // FlashcardFront is shown on default
                                face = CardFace.Front
                                offsetX.snapTo(0f)
                            }
                            // swipe left + add to "still learning" list
                            else if (offsetX.value < -swipeThreshold) {
                                // swipe left animation -> before handling logic and moving index
                                offsetX.animateTo(-1000f, tween(300))

                                flashcardsState.markAsStillLearning(currentFlashcard)
                                // check if it's the last flashcard
                                if (flashcardsState.currentIndex == flashcardsState.currentFlashcards.lastIndex) {
                                    onFinished()
                                } else {
                                    flashcardsState.moveToNext()
                                }

                                // FlashcardFront is shown on default
                                face = CardFace.Front
                                offsetX.snapTo(0f)
                            }
                            // no successful swipe
                            else {
                                offsetX.animateTo(0f, spring())
                            }
                        }
                    }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        // INNER BOX: actual card, with clip + blur + flip logic
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(20.dp))   // clipping przeniesione TUTAJ
        ) {
            // BLURRED FLASHCARD + FLIP LOGIC
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .blur(blurRadius)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                // flip logic
                if (rotation <= 90f) {
                    // show front of the flashcard (image)
                    FlashcardFront(
                        image = currentFlashcard.imageRes,
                        onClick = { face = face.flipped() },
                        isFavourite = isCurrentFavourite,
                        onFavouriteClick = onFavouriteToggle
                    )
                } else {
                    // show back of the flashcard (text)}
                    Box(
                        // additional 180f rotation to make sure the text itself ISN'T flipped
                        modifier = Modifier
                            .graphicsLayer { rotationY = 180f }
                    ) {
                        FlashcardBack(
                            text = currentFlashcard.text,
                            onClick = { face = face.flipped() },
                            isFavourite = isCurrentFavourite,
                            onFavouriteClick = onFavouriteToggle
                        )
                    }
                }
            }
            // OVERLAY WITH TEXT: "KNOW" (swipe right)/"STILL LEARNING" (swipe left)
            SwipeOverlay(effectiveOffsetX, overlayAlpha, isFront)
        }
    }
}

@Composable
fun BoxScope.FavouriteIcon(isFavourite: Boolean, onFavouriteClick: () -> Unit) {
    IconButton(
        onClick = onFavouriteClick,
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(8.dp)
    ) {
        Icon(
            painter = painterResource(
                id = if (isFavourite) R.drawable.star_filled else R.drawable.star_empty
            ),
            contentDescription =  if (isFavourite) "Remove from favourites" else "Add to favourites",
            modifier = Modifier.size(32.dp)
        )
    }
}