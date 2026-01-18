package com.project.learnasl

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.project.learnasl.ai.CameraPreview
import com.project.learnasl.ai.SignImageAnalyzer
import com.project.learnasl.ai.domain.Classification
import com.project.learnasl.ai.TfLiteSignClassifier
import com.project.learnasl.camera.CameraCategories
import com.project.learnasl.data.allLettersAslPairs
import com.project.learnasl.database.UserViewModel
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.MATCH_EXP
import com.project.learnasl.utils.UserViewModelHelper
import com.project.learnasl.utils.startMainActivity
import kotlin.getValue

// AI Landmark Recognition With Tensorflow Lite and CameraX on Android
// https://www.youtube.com/watch?v=ViRfnLAR_Uc
class AiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userViewModel by viewModels<UserViewModel> {
            UserViewModelHelper.getFactory(application)
        }

        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), 0
            )
        }

        enableEdgeToEdge()
        setContent {
            LearnASLTheme {

                var screen by remember { mutableStateOf("categories") }

                when (screen) {
                    "categories" -> CameraCategories(
                        onBackClick = {
                            finish()
                            startMainActivity(this)
                        },
                        // probably possible to simplify this code but im just doing it so it works for now
                        onCategoryClick = { value ->
                            screen = when (value) {
                                1 -> "live"
                                2 -> "quiz"
                                else -> "categories"
                            }
                        }
                    )

                    "live" -> LiveHandsDetection(
                        onBackClick = {
                            startMainActivity(this)
                            finish()
                        })

                    "quiz" -> LiveHandsQuiz(
                        onWin = {
                            userViewModel.addExp(MATCH_EXP)
                        },
                        onBackClick = {
                            startMainActivity(this)
                            finish()
                        })

                }
            }
        }
    }

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    @Composable
    private fun LiveHandsDetection(onBackClick: () -> Unit) {

        var classifications by remember {
            mutableStateOf(emptyList<Classification>())
        }

        val analyzer = remember {
            SignImageAnalyzer(
                classifier = TfLiteSignClassifier(
                    context = applicationContext
                ),
                onResults = {
                    Log.d("ASL", it.toString())
                    classifications = it
                }
            )
        }

        val controller = remember {
            LifecycleCameraController(applicationContext).apply {
                setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(applicationContext),
                    analyzer
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            CameraPreview(
                controller,
                Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) // semi-transparent
                    .padding(8.dp)
            ) {

                IconButton(modifier = Modifier.padding(top=25.dp),
                    onClick = { onBackClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.go_back),
                        contentDescription = "Go back button",
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }

                classifications.firstOrNull()?.let {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                            .padding(top = 36.dp, bottom = 36.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.background // contrasts overlay
                    )
                }
            }
        }
    }


    @Composable
    private fun LiveHandsQuiz(onWin: () -> Unit,
                              onBackClick: () -> Unit) {

        var classifications by remember {
            mutableStateOf(emptyList<Classification>())
        }

        val analyzer = remember {
            SignImageAnalyzer(
                classifier = TfLiteSignClassifier(
                    context = applicationContext
                ),
                onResults = {
                    Log.d("ASL", it.toString())
                    classifications = it
                }
            )
        }

        val controller = remember {
            LifecycleCameraController(applicationContext).apply {
                setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                setImageAnalysisAnalyzer(
                    ContextCompat.getMainExecutor(applicationContext),
                    analyzer
                )
            }
        }

        // 5 pairs of all letters for quiz questions
        val questions = remember {
            allLettersAslPairs.shuffled().take(1) // change it to larger, 1 for testing
        }

        var indexQuestion by remember { mutableIntStateOf(0) }

        val currentPrediction = classifications.firstOrNull()?.name
        val currentQuestion = questions.getOrNull(indexQuestion)

        var visiblePrediction by remember { mutableStateOf<String?>(null) }

        // remembers the last classification so the predictions doesn't flicker when it's null
        LaunchedEffect(classifications) {
            classifications.firstOrNull()?.let {
                visiblePrediction = it.name
            }
        }

        // if the prediction of model is the same as the questions label +1 do question index:]
        LaunchedEffect(currentPrediction) {
            if (currentPrediction != null && currentQuestion != null && currentPrediction == currentQuestion.label.lowercase()) {
                indexQuestion++
            }
        }

        var hasWon by remember { mutableStateOf(false) }

        LaunchedEffect(indexQuestion) {
            // win condition
            if (!hasWon && indexQuestion >= questions.size) {
                hasWon = true
                onWin() //safely add the exp
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            CameraPreview(
                controller,
                Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)) // semi-transparent
                    .padding(8.dp)
            ) {
                    IconButton(modifier = Modifier.padding(top=25.dp),
                        onClick = { onBackClick() }) {
                        Icon(
                            painter = painterResource(R.drawable.go_back),
                            contentDescription = "Go back button",
                            tint = MaterialTheme.colorScheme.onBackground,
                        )
                    }

                    Text(
                        text = "Current prediction: $visiblePrediction",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                            .padding(
                                top = 36.dp,
                                bottom = 36.dp
                            ), //moved it a bit lower cause on my emulator i can't see the letter, previously (vertical = 16.dp)
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.background // contrasts overlay
                    )

                    currentQuestion?.let { question ->
                        Text(
                            text = "Show: " + question.label,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                .padding(
                                    top = 36.dp,
                                    bottom = 36.dp
                                ),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.background
                        )

                    }

                    // winning text
                    if (hasWon) {
                        Text(
                            "YOU WON, go back to main screen",
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                                .padding(
                                    top = 36.dp,
                                    bottom = 36.dp
                                ),
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.background
                        )
                    }

                }


            }
        }

}

