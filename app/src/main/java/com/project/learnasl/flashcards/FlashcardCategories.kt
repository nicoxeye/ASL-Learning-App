package com.project.learnasl.flashcards

import android.content.Intent
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.project.learnasl.flashcards.model.Flashcard
import com.project.learnasl.flashcards.model.aslAlphabetFlashcards
import com.project.learnasl.flashcards.model.aslNumbersFlashcards
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.R
import com.project.learnasl.data.allNumbersAslPair

@Composable
fun FlashcardCategories(
    onBackClick: () -> Unit,
    onCategoryClick: (flashcards: List<Flashcard>, title: String) -> Unit,
    // favourites
    favouriteFlashcards: List<Flashcard>
) {
    // TODO: connect flashcards with Room database
    // (disclaimer: bc of no connection to the room database the favourites will always return a pop-up)

    // pop-up if there's no favourites
    var showEmptyFavouritesDialog by remember { mutableStateOf(false) }

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
                        text = "Categories",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
            // categories list
            item {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Category(
                            R.drawable.alphabet_icon,
                            "Alphabet",
                            onClick = {
                                onCategoryClick(
                                    aslAlphabetFlashcards,
                                    "Flashcards: Alphabet"
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Category(
                            R.drawable.number_icon,
                            "Numbers",
                            onClick = {
                                onCategoryClick(
                                    aslNumbersFlashcards,
                                    "Flashcards: Numbers"
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Category(
                            R.drawable.star,
                            "Favourites",
                            onClick = {
                                if (favouriteFlashcards.isNotEmpty()) {
                                    onCategoryClick(
                                        favouriteFlashcards,
                                        "Flashcards: Favourites"
                                    )
                                } else {
                                    showEmptyFavouritesDialog = true
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        if (showEmptyFavouritesDialog) {
            EmptyFavouritesDialog (
                onDismiss = {showEmptyFavouritesDialog = false}
            )
        }
    }
}

@Composable
fun Category(
    icon: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 39.dp, end = 39.dp, top = 60.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelLarge,
            fontSize = 15.sp,
            modifier = Modifier
                .padding(top = 15.dp)
        )
    }
}

// pop-up if the favourites is empty
@Composable
fun EmptyFavouritesDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                // header
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No favourite flashcards :(",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 25.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center,
                    )
                }
                // flashcards summary
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Click on the star in the top right corner of the flashcard to add flashcard to favourites! :D",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = { onDismiss() }
                ) {
                    Text("OK")
                }
            }
        }
    }
}