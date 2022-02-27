package com.andikas.burogu.ui.identify

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andikas.burogu.data.IdentifySharedPref
import com.andikas.burogu.databinding.ActivityIdentifyBinding
import com.andikas.burogu.ui.home.HomeActivity
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo

class IdentifyActivity : AppCompatActivity(), IdentifyView {
    private lateinit var binding: ActivityIdentifyBinding
    private lateinit var presenter: IdentifyPresenterImp
    private lateinit var sharedPreference: IdentifySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIdentifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()

        presenter = IdentifyPresenterImp(this)
        sharedPreference = IdentifySharedPref(this)

        presenter.loginCheck()
    }

    override fun checkIfAlreadyIdentified() {
        val isLogin = sharedPreference.userIdentified

        if (isLogin) {
            navigateTo(HomeActivity::class.java)
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            val name = binding.edtName.text.toString()

            when {
                name.isEmpty() -> {
                    binding.edtName.error = "Nama tidak boleh kosong"
                }
                else -> {
                    sharedPreference.userName = name
                    sharedPreference.userIdentified = true
                    navigateTo(HomeActivity::class.java)
                    finish()
                }
            }
        }
    }
}