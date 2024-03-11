package com.xml.guard.utils

import java.util.regex.Pattern
import kotlin.math.pow

/**
 * User: ljx
 * Date: 2022/3/1
 * Time: 17:31
 */

//移除后缀
fun String.removeSuffix(): String {
    val index = lastIndexOf(".")
    return if (index == -1) this else substring(0, index)
}

//获取后缀
fun String.getSuffix(): String {
    val index = lastIndexOf(".")
    return if (index == -1) "" else substring(index)
}

fun String.getDirPath(): String {
    val index = lastIndexOf(".")
    return if (index == -1) this else substring(0, index)
}


fun String.findWord(
    word: String,
    ignoreCase: Boolean = false
): Int {
    var occurrenceIndex: Int = indexOf(word, 0, ignoreCase)
    // FAST PATH: no match
    if (occurrenceIndex < 0) return -1

    val oldValueLength = word.length
    val searchStep = oldValueLength.coerceAtLeast(1)

    do {
        if (isWord(occurrenceIndex, word)) {
            return occurrenceIndex
        }
        if (occurrenceIndex >= length) break
        occurrenceIndex = indexOf(word, occurrenceIndex + searchStep, ignoreCase)
    } while (occurrenceIndex > 0)
    return -1
}

fun String.replaceWords(
    oldValue: String,
    newValue: String,
    ignoreCase: Boolean = false
): String {
    var occurrenceIndex: Int = indexOf(oldValue, 0, ignoreCase)
    // FAST PATH: no match
    if (occurrenceIndex < 0) return this

    val oldValueLength = oldValue.length
    val searchStep = oldValueLength.coerceAtLeast(1)
    val newLengthHint = length - oldValueLength + newValue.length
    if (newLengthHint < 0) throw OutOfMemoryError()
    val stringBuilder = StringBuilder(newLengthHint)

    var i = 0
    do {
        if (isWord(occurrenceIndex, oldValue)) {
            stringBuilder.append(this, i, occurrenceIndex).append(newValue)
        } else {
            stringBuilder.append(this, i, occurrenceIndex + oldValueLength)
        }
        i = occurrenceIndex + oldValueLength
        if (occurrenceIndex >= length) break
        occurrenceIndex = indexOf(oldValue, occurrenceIndex + searchStep, ignoreCase)
    } while (occurrenceIndex > 0)
    return stringBuilder.append(this, i, length).toString()
}

fun String.isWord(index: Int, oldValue: String): Boolean {
    val firstChar = oldValue[0].code
    if (index > 0 && (firstChar in 65..90 || firstChar == 95 || firstChar in 97..122)) {
        val prefix = get(index - 1).code
        // $ . 0-9 A-Z _ a-z
        if (prefix == 36 || prefix == 46 || prefix in 48..57 || prefix in 65..90 || prefix == 95 || prefix in 97..122) {
            return false
        }
    }
    val endChar = oldValue[oldValue.lastIndex].code
    // $ 0-9 A-Z _ a-z
    if (endChar == 36 || endChar in 48..57 || endChar in 65..90 || endChar == 95 || endChar in 97..122) {

        val suffix = getOrNull(index + oldValue.length)?.code
        // $ 0-9 A-Z _ a-z
        if (suffix == 36 || suffix in 48..57 || suffix in 65..90 || suffix == 95 || suffix in 97..122) {
            return false
        }
    }
    return true
}

// Long 转 大写字符串
fun Long.toUpperLetterStr(): String {
    return toLetterStr(true)
}

// Long 转 大/小字符串
fun Long.toLetterStr(upperCase: Boolean = false): String {
    val size = 26
    val offSize = if (upperCase) 65 else 97
    val sb = StringBuilder()
    var num = this
    do {
        val char = (num % size + offSize).toChar()
        sb.append(char)
        num /= size
    } while (num > 0)
    return sb.reverse().toString()
}

//字符串转Long, 必须是大写或小写字母, 不能是大小写混合
fun String.to26Long(): Long {
    val regexMixedCaseAndDigits = "^[a-zA-Z0-9]+$" // 允许大小写混合和数字
    val isMixedCaseAndDigits = Pattern.matches(regexMixedCaseAndDigits, this)
    if (!isMixedCaseAndDigits) {
        throw IllegalArgumentException("string must consist of mixed upper and lowercase letters and digits, but it was $this")
    }

    var num = 0L
    for (i in indices) {
        val c = get(i)
        val charValue = when {
            c.isUpperCase() -> c - 'A'
            c.isLowerCase() -> c - 'a' + 26 // 使小写字母的值位于大写字母之后
            else -> c - '0' + 52 // 使数字的值位于字母之后
        }
        num += (charValue * 62.0.pow((length - 1 - i).toDouble())).toLong()
    }
    return num
}

internal fun String.splitWords(): List<String> {
    val regex = Regex("(?<=[a-z])(?=[A-Z])|(?<=[A-Z])(?=[A-Z][a-z])")
    return split(regex).map { it.lowercase() }
}