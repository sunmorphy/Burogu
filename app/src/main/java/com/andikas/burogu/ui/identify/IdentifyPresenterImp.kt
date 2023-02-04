package com.andikas.burogu.ui.identify

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class IdentifyPresenterImp(private val view: IdentifyView) : IdentifyPresenter {

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    override fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    override fun loginCheck() {
        view.checkIfAlreadyIdentified()
    }
}