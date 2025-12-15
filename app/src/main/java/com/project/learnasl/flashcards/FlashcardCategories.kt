package com.project.learnasl.flashcards

import android.content.Intent
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.flashcards.model.Flashcard
import com.project.learnasl.flashcards.model.aslAlphabetFlashcards
import com.project.learnasl.flashcards.model.aslNumbersFlashcards
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.R

@Composable
fun FlashcardCategories(
    onBackClick: () -> Unit,
    onCategoryClick: (flashcards: List<Flashcard>, title: String) -> Unit
) {
    LearnASLTheme (
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
               ){
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
                Row (
                    modifier = Modifier
                        .padding(start = 35.dp, top = 24.dp)
                ){
                    Category(
                        R.drawable.alphabet_icon,
                        "Alphabet",
                        onClick = { onCategoryClick(aslAlphabetFlashcards, "Flashcards: Alphabet") }
                    )
                    Spacer(modifier = Modifier.padding(start=34.dp))
                    Category(
                        R.drawable.number_icon,
                        "Numbers",
                        onClick = { onCategoryClick(aslNumbersFlashcards, "Flashcards: Numbers")}
                    )
                }
            }
        }
    }
}

@Composable
fun Category(
    icon: Int,
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 39.dp, end = 39.dp, top = 60.dp, bottom = 60.dp)
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .padding(start =17.dp)
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
