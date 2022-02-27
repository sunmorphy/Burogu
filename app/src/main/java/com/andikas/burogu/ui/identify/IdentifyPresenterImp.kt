package com.andikas.burogu.ui.identify

class IdentifyPresenterImp(private val view: IdentifyView) : IdentifyPresenter {
    override fun loginCheck() {
        view.checkIfAlreadyIdentified()
    }
}