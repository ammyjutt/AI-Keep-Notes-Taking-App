package com.example.keepnotes.presentation.screen.authscreen


import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    label: String,
    text: String, // Accept current text value as a parameter
    onTextChange: (String) -> Unit, // Provide a callback for text change
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    readOnly: Boolean = false,
    height: Dp = 42.dp,
    trailingIcon: ImageVector? = null
) {
    Column(modifier = modifier) {
        Text(text = label)
        Spacer(modifier = Modifier.height(10.dp))
        BasicTextField(
            value = text, // Use the passed-in text value
            onValueChange = onTextChange, // Call the passed-in callback when text changes
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            readOnly = readOnly,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .height(height)
                    .background(Color(0xFFEFEEEE)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (text.isEmpty()) {
                        Text(text = label, color = Color.Gray) // Placeholder text
                    }
                    it() // The actual text input
                }
                trailingIcon?.let {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = trailingIcon, contentDescription = null, tint = Color(0xFF828282))
                    }
                }
            }
        }
    }
}



