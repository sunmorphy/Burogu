package com.andikas.burogu.utils

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

object Extensions {

    fun AppCompatActivity.hideActionBar() {
        this.supportActionBar?.hide()
    }

    fun Activity.makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Activity.navigateTo(classTarget: Class<*>, extraKey: String? = null, extraParcel: Parcelable? = null) {
        val intent = Intent(this, classTarget)
        if (extraKey != null && extraParcel != null) {
            intent.putExtra(extraKey, extraParcel)
        }
        startActivity(intent)
    }

}