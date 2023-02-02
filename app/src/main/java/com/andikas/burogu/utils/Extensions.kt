package com.andikas.burogu.utils

import android.app.Activity
import android.content.Intent
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.andikas.burogu.R

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

    val Roboto = FontFamily(
        Font(R.font.roboto_regular),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_bold, FontWeight.Bold),
    )

    val Poppins = FontFamily(
        Font(R.font.poppins_medium, FontWeight.Medium),
        Font(R.font.poppins_semi_bold, FontWeight.SemiBold),
        Font(R.font.poppins_bold, FontWeight.Bold),
    )

}