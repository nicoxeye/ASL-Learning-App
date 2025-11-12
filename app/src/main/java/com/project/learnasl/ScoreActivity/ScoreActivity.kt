package com.project.learnasl.ScoreActivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.project.learnasl.MainActivity
import com.project.learnasl.R
import com.project.learnasl.ui.theme.LearnASLTheme

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor= ContextCompat.getColor(this, R.color.purple_500)
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        val score = intent.getIntExtra("Score", 0)
        //val context = LocalContext.current
        setContent {
            LearnASLTheme {
                val context = LocalContext.current
                ScoreScreen(score = score, context=context)
//                ScoreScreen(score=score) {
//                    startActivity(Intent(this, MainActivity::class.java))
//                    finish()
//                }
            }
        }
    }
}