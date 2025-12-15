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

    // skipping frames to make it analyze once a second
    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {

        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap =
                image.toBitmap() // TODO: need the actual img width and height the model expects...
                    .centerCrop(224, 224)
            val results = classifier.classify(bitmap, rotationDegrees)
            onResults(results)
        }
        frameSkipCounter++

        image.close()
    }

}