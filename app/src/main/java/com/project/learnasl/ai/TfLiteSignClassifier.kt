package com.project.learnasl.ai

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import com.project.learnasl.ai.domain.Classification
import com.project.learnasl.ai.domain.SignClassifier
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import androidx.core.graphics.get


class TfLiteSignClassifier(
    context: Context,
    private val threshold: Float = 0.2f //from which score we want to include classification; sureness
    // ^im lowering it to 0.2 since i added more classes :) (numbers)
) : SignClassifier {

    // using interpreter from tensorflow cause the previous one needed normalization that the model did not have :]
    private val interpreter: Interpreter

    // IMPORTANT: it matches the labels from the model that i took from the internet
//    private val labels = listOf(
//        "A","B","C","D","E","F","G","H","I","J","K","L",
//        "M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
//        "Delete", "Nothing", "Space"
//    )

   //  same labels as in training set in custom model
    private val labels = listOf(
        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
        "w", "x", "y", "z"
    )

    init {
        val model = FileUtil.loadMappedFile(context, "model_compatible_2.tflite")
        //val model = FileUtil.loadMappedFile(context, "model_ASL.tflite")
        interpreter = Interpreter(model)
    }

    override fun classify(bitmap: Bitmap): List<Classification> {

        // i found this line connected to scaling bitmap *before* using it in classification
        // maybe that could help with it?? lmk !! if it doesnt work just remove it:)
        // source - https://firebase.google.com/docs/ml/android/use-custom-models?hl=pl#run_the_interpreter
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        // ^ (documentation) Creates a new bitmap, scaled from an existing bitmap, when possible. If the specified width and height
        // are the same as the current width and height of the source bitmap, the source bitmap
        // is returned and no new bitmap is created
        // here the same thing - https://medium.com/walmartglobaltech/custom-tensorflow-lite-model-implementation-in-android-5c1c65bd9f97

        // TFLite models expect input as a multi dimensional array of floats
        val input = Array(1) { // batch size
            Array(224) { // img height (pixels)
                Array(224) { // img width
                    FloatArray(3) // rgb channels
                }
            }
        }

        // converting bitmap pixels to normalized floats
        for (y in 0 until 224) {
            for (x in 0 until 224) {
                val pixel = resizedBitmap[x, y] // bitmap[x, y]
                input[0][y][x][0] = Color.red(pixel) / 255f
                input[0][y][x][1] = Color.green(pixel) / 255f
                input[0][y][x][2] = Color.blue(pixel) / 255f
            }
        }

        val output = Array(1) {
            FloatArray(labels.size)
        }
        interpreter.run(input, output) // fills the output array with predictions
        // after that^ output[0] contains the scores for each label for the single input image and returns the one with the most sure score below...

        return labels.mapIndexed { index, label ->
            Classification(label, output[0][index])
        }
            .filter { it.score >= threshold }
            .sortedByDescending { it.score }
    }

}
