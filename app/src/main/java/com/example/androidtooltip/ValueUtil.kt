package com.example.androidtooltip

import android.content.res.Resources
import kotlin.math.floor
import kotlin.math.roundToLong

private val displayMetrics = Resources.getSystem().displayMetrics

val Int.toPx: Int get() = (this / displayMetrics.density).toInt()
val Int.toDp: Int get() = (this * displayMetrics.density).toInt()

val Float.toDp: Float get() = this * displayMetrics.density
val Float.toPx: Float get() = this / displayMetrics.density

fun Long.toCertainDigitsString(len: Int): String {
    val str = this.toString()
    if (str.length >= len) return str
    val digitsString = "00000000$str"
    val s = digitsString.length - len
    return digitsString.substring(s)
}

fun Long.millisecToTimeString(div: String = ":", isFillDigits: Boolean = false): String {
    if (this < 0) return "00${div}00"
    val i: Long = (this.toDouble() / 1000f).roundToLong()
    val s: Long = i % 60
    val m: Long = floor((i % 3600).toDouble() / 60.0).toLong()
    val h: Long = floor(i.toDouble() / 3600.0).toLong()
    var str = ""
    if (isFillDigits || h > 0) str += (h.toCertainDigitsString(2) + div)
    str += (m.toCertainDigitsString(2) + div)
    str += (s.toCertainDigitsString(2))
    return str
}