
package com.example.keepnotes.presentation.screen.categories

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.keepnotes.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    openDrawer: () -> Unit,
    categoriesViewModel: CategoriesViewModel = hiltViewModel()
) {
    val categories by categoriesViewModel.categories.collectAsState()

    val categoryIcons = mapOf(
        "Work" to R.drawable.notes_icon,
        "Journal" to R.drawable.diary,
        "Schedule" to R.drawable.schedule,
        "Password" to R.drawable.lock,
        "Health" to R.drawable.battery,
        "Others" to R.drawable.archive
    )


    // Define the background color and border color
    val buttonBackgroundColor = Color(0xFFFFFFFF) // Light grayish white
    val contentColor = Color(0xFF000000) // Black text
    val buttonShape = RoundedCornerShape(12.dp) // Slightly rounded corners

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Select a Category") },
                navigationIcon = {
                    IconButton(onClick = openDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Open Drawer")
                    }
                }
            )
        }
    ) { paddingValues ->

        Spacer(modifier = Modifier.height(8.dp))


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(34.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            items(categories.chunked(2)) { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    row.forEachIndexed { _, category ->

                        val iconResId = categoryIcons[category] ?: R.drawable.glitter_icon
                        ElevatedButton(
                            onClick = {
                                categoriesViewModel.onCategorySelected(category) { selectedCategory: String ->
                                    val lowerCaseCategory = selectedCategory.lowercase()
                                    navController.navigate("all_notes_screen/$lowerCaseCategory")
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .width(110.dp)
                                , // Optional border
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = buttonBackgroundColor,
                                contentColor = contentColor
                            ),
                            shape = buttonShape,
                            elevation = ButtonDefaults.elevatedButtonElevation(5.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    painter = painterResource(id = iconResId),
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp), // Icon size
                                    tint = Color.Unspecified // Ensures the icon's original colors are used

                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = contentColor,
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCategoriesScreen() {
    val navController = rememberNavController()

    // Sample CategoriesViewModel with default categories
    val categoriesViewModel = CategoriesViewModel()

    CategoriesScreen(
        navController = navController,
        openDrawer = {},
        categoriesViewModel = categoriesViewModel
    )
}



