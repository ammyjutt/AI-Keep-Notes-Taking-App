package com.example.keepnotes.presentation.screen.help

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.keepnotes.R // Replace with your actual package name
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()), // To enable scrolling
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top section with Profile Picture
        Spacer(modifier = Modifier.height(60.dp))
        Image(
            painter = painterResource(id = R.drawable.help_page_icon), // Replace with your image resource
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Name and Membership
        Text(
            text = "Help Section",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light)
        )


        Spacer(modifier = Modifier.height(6.dp))

        // Account Information Section
        Text(
            text = "Instructions Manual",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Light, fontSize = 14.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        ProfileItem(label = "Getting Started", value = "To start using the app, simply create a new note by clicking on the '+' button. You can then add text, images, and other elements to your note.To start using the app, simply create a new note by clicking on the '+' button. You can then add text, images, and other elements to your note.")
        ProfileItem(label = "Managing Notes", value = "You can organize your notes by creating categories and tags. Use the sidebar to filter and view your notes by category.You can organize your notes by creating categories and tags. Use the sidebar to filter and view your notes by category.")
        ProfileItem(label = "Troubleshooting", value = "If you encounter any issues, try restarting the app. For persistent problems, refer to our online FAQ or contact support.If you encounter any issues, try restarting the app")
        ProfileItem(label = "Contact Us", value = "For further assistance, you can reach out to our support team via the 'Contact Us' section in the app settings.")


        Spacer(modifier = Modifier.height(24.dp))


    }
}

@Composable
fun ProfileSection(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelSmall.copy(
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
fun ProfileItem(label: String, value: String = "") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(20.dp)) // Rounded corners
            .background(Color(0xFFF5F5F5)) // Slightly grayish white background
            .padding(16.dp), // Internal padding for content
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

            )
            if (value.isNotEmpty()) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }


    }
}



@Preview(showBackground = true)
@Composable
fun HelpScreenPreview() {
    HelpScreen()
}
