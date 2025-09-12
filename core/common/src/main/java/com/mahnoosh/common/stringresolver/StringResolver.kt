package com.mahnoosh.common.stringresolver

import androidx.annotation.StringRes

fun interface StringResolver {
    fun findString(@StringRes stringId: Int): String
}