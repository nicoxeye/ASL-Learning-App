package com.project.learnasl.Dashboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.learnasl.R
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.ui.theme.LearnASLTheme


@Composable
fun UserText(viewModel: UserViewModel) {
    //gets the one (1) user existing in the database and shows theri name and exp for testing
    val user = viewModel.currentUser.value

    if (user != null) {
        Text("Welcome, ${user.name}",
            color = MaterialTheme.colorScheme.onBackground
        )
        Text("XP: ${user.experience}",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
//@Preview
// viewModel : UserViewModel
fun UserSection(
    username: String,
    exp: Int
){
    LearnASLTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Welcome, $username",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(modifier = Modifier.width(50.dp))
            Row(
                modifier = Modifier
                    .height(40.dp)
                    .width(160.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.star), contentDescription = "Star symbol",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "XP: $exp",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    softWrap = false
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}