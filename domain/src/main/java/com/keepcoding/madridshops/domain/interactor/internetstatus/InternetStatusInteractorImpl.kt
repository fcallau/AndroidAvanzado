package com.keepcoding.madridshops.domain.interactor.internetstatus

import com.keepcoding.madridshops.domain.interactor.CodeClosure
import com.keepcoding.madridshops.domain.interactor.ErrorClosure

class InternetStatusInteractorImpl : InternetStatusInteractor {
    override fun execute(success: CodeClosure, error: ErrorClosure) {
        success()
    }
}