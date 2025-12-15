package com.project.learnasl.match.data

import android.os.Parcelable
import com.project.learnasl.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class AslPair(
    val drawing: Drawing,
    val label: String
) : Parcelable

val allAslPairs = listOf(
    AslPair(Drawing(R.drawable.asl_a, "A"), "A"),
    AslPair(Drawing(R.drawable.asl_b, "B"), "B"),
    AslPair(Drawing(R.drawable.asl_c, "C"), "C"),
    AslPair(Drawing(R.drawable.asl_d, "D"), "D"),
    AslPair(Drawing(R.drawable.asl_e, "E"), "E"),
    AslPair(Drawing(R.drawable.asl_f, "F"), "F"),
    AslPair(Drawing(R.drawable.asl_g, "G"), "G"),
    AslPair(Drawing(R.drawable.asl_h, "H"), "H"),
    AslPair(Drawing(R.drawable.asl_i, "I"), "I"),
    AslPair(Drawing(R.drawable.asl_j, "J"), "J"),
    AslPair(Drawing(R.drawable.asl_k, "K"), "K"),
    AslPair(Drawing(R.drawable.asl_l, "L"), "L"),
    AslPair(Drawing(R.drawable.asl_m, "M"), "M"),
    AslPair(Drawing(R.drawable.asl_n, "N"), "N"),
    AslPair(Drawing(R.drawable.asl_o, "O"), "O"),
    AslPair(Drawing(R.drawable.asl_p, "P"), "P"),
    AslPair(Drawing(R.drawable.asl_q, "Q"), "Q"),
    AslPair(Drawing(R.drawable.asl_r, "R"), "R"),
    AslPair(Drawing(R.drawable.asl_s, "S"), "S"),
    AslPair(Drawing(R.drawable.asl_t, "T"), "T"),
    AslPair(Drawing(R.drawable.asl_u, "U"), "U"),
    AslPair(Drawing(R.drawable.asl_v, "V"), "V"),
    AslPair(Drawing(R.drawable.asl_w, "W"), "W"),
    AslPair(Drawing(R.drawable.asl_x, "X"), "X"),
    AslPair(Drawing(R.drawable.asl_y, "Y"), "Y"),
    AslPair(Drawing(R.drawable.asl_z, "Z"), "Z")
)

// wheres 0 tho </3
val allNumbersAslPair = listOf(
    AslPair(Drawing(R.drawable.asl_1, "1"), "1"),
    AslPair(Drawing(R.drawable.asl_2, "2"), "2"),
    AslPair(Drawing(R.drawable.asl_3, "3"), "3"),
    AslPair(Drawing(R.drawable.asl_4, "4"), "4"),
    AslPair(Drawing(R.drawable.asl_5, "5"), "5"),
    AslPair(Drawing(R.drawable.asl_6, "6"), "6"),
    AslPair(Drawing(R.drawable.asl_7, "7"), "7"),
    AslPair(Drawing(R.drawable.asl_8, "8"), "8"),
    AslPair(Drawing(R.drawable.asl_9, "9"), "9"),
    AslPair(Drawing(R.drawable.asl_10, "10"), "10")
)
