package com.project.learnasl.ai

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.project.learnasl.ai.domain.Classification
import com.project.learnasl.ai.domain.SignClassifier

// called every frame takes the bitmap and feeds it into classifier
class SignImageAnalyzer(
    private val classifier: SignClassifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {
    private var lastAnalyzedTime = 0L

    override fun analyze(image: ImageProxy) {
        val currentTime = System.currentTimeMillis()

        // analyse every second
        if (currentTime - lastAnalyzedTime >= 1_000) {
            lastAnalyzedTime = currentTime

            val rotationDegrees = image.imageInfo.rotationDegrees

            val bitmap = image
                .toBitmap()
                .rotate(rotationDegrees)
                .resize(224, 224)

            val results = classifier.classify(bitmap)
            onResults(results)
        }

        image.close()
    }

}