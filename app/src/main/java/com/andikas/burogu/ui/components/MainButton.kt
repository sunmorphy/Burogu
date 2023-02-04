package com.andikas.burogu.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.andikas.burogu.R

@Composable
fun MainButton(
    icon: ImageVector,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.primaryDark)),
        onClick = { onClick() },
        modifier = modifier,
    ) {
        Icon(
            imageVector = icon,
            tint = Color.White,
            contentDescription = contentDescription,
        )
    }
}

@Preview
@Composable
fun MainButtonPreview() {
    MaterialTheme {
        MainButton(
            Icons.Rounded.ArrowBackIos,
            "",
            onClick = {}
        )
    }
}