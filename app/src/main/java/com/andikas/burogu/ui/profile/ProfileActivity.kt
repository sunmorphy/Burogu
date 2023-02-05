package com.andikas.burogu.ui.profile

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Output
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.andikas.burogu.R
import com.andikas.burogu.data.IdentifySharedPref
import com.andikas.burogu.ui.bookmark.BookmarkActivity
import com.andikas.burogu.ui.components.MainButton
import com.andikas.burogu.ui.identify.IdentifyActivity
import com.andikas.burogu.utils.Extensions.Poppins
import com.andikas.burogu.utils.Extensions.hideActionBar
import com.andikas.burogu.utils.Extensions.navigateTo

class ProfileActivity : AppCompatActivity(), ProfileView {
    private lateinit var presenter: ProfilePresenterImp
    private lateinit var sharedPreference: IdentifySharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideActionBar()

        presenter = ProfilePresenterImp(this)
        sharedPreference = IdentifySharedPref(this)

        setContent {
            MaterialTheme {
                ProfileScreen(
                    name = sharedPreference.userName,
                    onBack = {
                        onBackPressedDispatcher.onBackPressed()
                    },
                    onBookmarkClick = {
                        navigateTo(BookmarkActivity::class.java)
                    },
                    onLogoutClick = {
                        presenter.closeScreen()
                    }
                )
            }
        }
    }

    override fun signOut() {
        getSharedPreferences("userData", Context.MODE_PRIVATE).edit().clear().apply()
        navigateTo(IdentifyActivity::class.java)
        finishAffinity()
    }
}

@Composable
fun ProfileScreen(
    name: String,
    onBack: () -> Unit,
    onBookmarkClick: () -> Unit,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(20.dp)
    ) {
        MainButton(
            icon = Icons.Rounded.ArrowBackIos,
            contentDescription = "Kembali",
            onClick = onBack,
        )
        AsyncImage(
            model = R.drawable.img_panda,
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 30.dp),
        )
        Text(
            text = name,
            fontSize = 24.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.primaryDark),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primaryLight)),
            onClick = { onBookmarkClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, top = 20.dp, end = 36.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Bookmark,
                tint = colorResource(id = R.color.primaryDark),
                contentDescription = null,
                modifier = Modifier
            )
            Text(
                text = "Bookmarks",
                fontSize = 16.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.primaryDark),
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 8.dp),
            )
            Icon(
                imageVector = Icons.Rounded.ArrowForwardIos,
                tint = colorResource(id = R.color.primaryDark),
                contentDescription = null,
                modifier = Modifier
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primaryLight)),
            onClick = { onLogoutClick() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp, top = 20.dp, end = 36.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Output,
                tint = colorResource(id = R.color.primaryDark),
                contentDescription = null,
                modifier = Modifier
            )
            Text(
                text = "Keluar",
                fontSize = 16.sp,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.primaryDark),
                modifier = Modifier
                    .weight(3f)
                    .padding(start = 8.dp),
            )
            Icon(
                imageVector = Icons.Rounded.ArrowForwardIos,
                tint = colorResource(id = R.color.primaryDark),
                contentDescription = null,
                modifier = Modifier
            )
        }
    }
}

@Preview(
    device = Devices.PIXEL_4_XL,
    showSystemUi = false,
    showBackground = true,
)
@Composable
fun ProfileScreenPreview() {
    MaterialTheme {
        ProfileScreen(
            "title",
            {},
            {},
            {}
        )
    }
}