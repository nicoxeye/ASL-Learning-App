package com.project.learnasl

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.project.learnasl.ai.CameraPreview
import com.project.learnasl.ai.SignImageAnalyzer
import com.project.learnasl.ai.domain.Classification
import com.project.learnasl.ai.TfLiteSignClassifier
import com.project.learnasl.camera.CameraCategories
import com.project.learnasl.ui.theme.LearnASLTheme
import com.project.learnasl.utils.startMainActivity

// AI Landmark Recognition With Tensorflow Lite and CameraX on Android
// https://www.youtube.com/watch?v=ViRfnLAR_Uc
class AiActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                    "live" -> LiveHandsDetection()

                    "quiz" -> {
                        Text("AI Quiz TODO()")
                    }

                }
            }
        }
    }

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    @Composable
    private fun LiveHandsDetection() {

        var classifications by remember {
            mutableStateOf(emptyList<Classification>())
        }

        val analyzer = remember {
            SignImageAnalyzer (
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
                classifications.firstOrNull()?.let {
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
                            .padding(vertical = 16.dp),
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.background // contrasts overlay
                    )
                }
            }
        }
    }


}

