import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier



import androidx.compose.runtime.Composable


import androidx.compose.ui.tooling.preview.Preview

import com.example.keepnotes.R




import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown

import androidx.compose.ui.Alignment

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun RoundedDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Select") }
    val items = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val dropdownWidth = screenWidth * 0.3f // 30% of screen width

    Box(
        modifier = Modifier
            .width(dropdownWidth)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .clickable { expanded = true }
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = selectedItem, color = Color.Black)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                tint = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.width(dropdownWidth)
        ) {
            items.forEach { item ->
                DropdownMenuItem(onClick = {
                    selectedItem = item
                    expanded = false
                }) {
                    Text(text = item)
                }
            }
        }
    }
}









@Preview(showBackground = true)
@Composable
fun PreviewMyComposable() {
    RoundedDropdown()
}


