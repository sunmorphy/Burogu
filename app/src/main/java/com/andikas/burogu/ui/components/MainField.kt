package com.andikas.burogu.ui.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andikas.burogu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
) {
    when {
        leadingIcon == null && trailingIcon == null -> {
            TextField(
                value = value,
                onValueChange = onValueChange,
                maxLines = maxLines,
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(text = placeholder)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.primaryLight),
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = modifier
                    .heightIn(52.dp)
            )
        }
        leadingIcon != null && trailingIcon == null -> {
            TextField(
                value = value,
                onValueChange = onValueChange,
                maxLines = maxLines,
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(text = placeholder)
                },
                leadingIcon = {
                    Icon(imageVector = leadingIcon, contentDescription = null)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.primaryLight),
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = modifier
                    .heightIn(52.dp)
            )
        }
        leadingIcon == null && trailingIcon != null -> {
            TextField(
                value = value,
                onValueChange = onValueChange,
                maxLines = maxLines,
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(text = placeholder)
                },
                trailingIcon = {
                    Icon(imageVector = trailingIcon, contentDescription = null)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.primaryLight),
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = modifier
                    .heightIn(52.dp)
            )
        }
        else -> {
            TextField(
                value = value,
                onValueChange = onValueChange,
                maxLines = maxLines,
                shape = RoundedCornerShape(20.dp),
                placeholder = {
                    Text(text = placeholder)
                },
                leadingIcon = {
                    Icon(imageVector = leadingIcon as ImageVector, contentDescription = null)
                },
                trailingIcon = {
                    Icon(imageVector = trailingIcon as ImageVector, contentDescription = null)
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.primaryLight),
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = modifier
                    .heightIn(52.dp)
            )
        }
    }
}

@Preview
@Composable
fun MainFieldPreview() {
    MaterialTheme {
        MainField(
            placeholder = "placeholder",
            value = "text",
            onValueChange = {},
            trailingIcon = Icons.Rounded.Add
        )
    }
}