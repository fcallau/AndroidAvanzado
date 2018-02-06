package com.keepcoding.madridshops.domain.interactor.internetstatus

import com.keepcoding.madridshops.domain.interactor.CodeClosure
import com.keepcoding.madridshops.domain.interactor.ErrorClosure


interface InternetStatusInteractor {
    fun execute(success: CodeClosure, error: ErrorClosure)
}
