package com.keepcoding.madridshops.repository.thread

import android.os.Handler
import android.os.Looper

fun DispatchOnMainTread(codeToRun: Runnable) {
    val uiHandler = Handler(Looper.getMainLooper())
    uiHandler.post(codeToRun)
}