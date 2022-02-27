package com.andikas.burogu.ui.profile

class ProfilePresenterImp(
    private val view: ProfileView
) : ProfilePresenter {
    override fun closeScreen() {
        view.signOut()
    }
}