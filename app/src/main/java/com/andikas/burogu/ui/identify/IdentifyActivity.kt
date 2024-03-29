package com.andikas.burogu.ui.identify

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andikas.burogu.R
import com.andikas.burogu.data.IdentifySharedPref
import com.andikas.burogu.ui.components.MainButton
import com.andikas.burogu.ui.components.MainField
import com.andikas.burogu.ui.home.HomeActivity
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo

class IdentifyActivity : AppCompatActivity(), IdentifyView {

    private lateinit var presenter: IdentifyPresenterImp
    private lateinit var sharedPreference: IdentifySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()

        presenter = IdentifyPresenterImp(this)
        sharedPreference = IdentifySharedPref(this)

        presenter.loginCheck()

        val query by presenter.query

        setContent {
            MaterialTheme {
                IdentifyContent(
                    value = query,
                    onValueChange = presenter::onQueryChange,
                    onButtonClick = {
                        sharedPreference.userName = it.ifEmpty { "Guest" }
                        sharedPreference.userIdentified = true
                        navigateTo(HomeActivity::class.java)
                        finish()
                    }
                )
            }
        }
    }

    override fun checkIfAlreadyIdentified() {
        val isLogin = sharedPreference.userIdentified

        if (isLogin) {
            navigateTo(HomeActivity::class.java)
            finish()
        }
    }
}

@Composable
fun IdentifyContent(
    value: String,
    onValueChange: (String) -> Unit,
    onButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        AsyncImage(
            model = R.drawable.img_hello,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        )
        Row(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 20.dp
                )
        ) {
            MainField(
                placeholder = "Nama",
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .weight(3f)
            )
            MainButton(
                icon = Icons.Rounded.Check,
                contentDescription = null,
                onClick = { onButtonClick(value) },
                modifier = Modifier
                    .heightIn(52.dp)
                    .padding(start = 12.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_4_XL,
    showBackground = true,
    showSystemUi = false,
)
@Composable
fun IdentifyPreview() {
    MaterialTheme {
        IdentifyContent(
            "",
            {},
            {}
        )
    }
}