package com.andikas.burogu.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andikas.burogu.data.IdentifySharedPref
import com.andikas.burogu.databinding.ActivityProfileBinding
import com.andikas.burogu.ui.bookmark.BookmarkActivity
import com.andikas.burogu.ui.identify.IdentifyActivity
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPreference: IdentifySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        sharedPreference = IdentifySharedPref(this)

        binding.txtAuthor.text = sharedPreference.userName
        binding.btnBack.setOnClickListener { onBackPressed() }
        binding.btnNavigateBookmark.setOnClickListener { navigateTo(BookmarkActivity::class.java) }
        binding.btnNavigateSignOut.setOnClickListener {
            getSharedPreferences("userData", Context.MODE_PRIVATE).edit().clear().apply()
            navigateTo(IdentifyActivity::class.java)
            finishAffinity()
        }
    }
}