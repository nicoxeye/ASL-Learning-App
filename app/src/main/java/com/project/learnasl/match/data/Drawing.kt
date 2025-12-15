package com.project.learnasl.match.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// possibly rename this,
// label exists so comparing between the real label (chosen by user, can be different from this one) in a pair is possible
@Parcelize
data class Drawing(
    val imageRes: Int,
    val label: String
) : Parcelable