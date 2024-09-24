package com.example.keepnotes.presentation.component

import SettingsScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.keepnotes.R
import com.example.keepnotes.presentation.screen.allnotes.AllNotesScreen
import com.example.keepnotes.presentation.screen.categories.CategoriesScreen
import com.example.keepnotes.presentation.screen.help.*
import com.example.keepnotes.ui.theme.DIMENS_16dp
import com.example.keepnotes.ui.theme.DIMENS_20dp
import com.example.keepnotes.ui.theme.DIMENS_24dp
import com.example.keepnotes.ui.theme.DIMENS_8dp
import com.example.keepnotes.ui.theme.SelectedOptionColor
import com.example.keepnotes.ui.theme.TEXT_SIZE_16sp
import com.example.keepnotes.ui.theme.TEXT_SIZE_24sp
import kotlinx.coroutines.launch


@Composable
fun DrawerAppComponent(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val currentScreen = remember { mutableStateOf(DrawerAppScreen.Notes) }
    val coroutineScope = rememberCoroutineScope()

    ModalDrawer(
        drawerShape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent =
        {

            DrawerContentComponent(
                currentScreen = currentScreen,
                closeDrawer = { coroutineScope.launch { drawerState.close() } }
            )
        },
        content =
        {

            BodyContentComponent(
                currentScreen = currentScreen.value,
                openDrawer =
                {
                coroutineScope.launch { drawerState.open() }
                },
                navController = navController
            )
        }
    )
}

@Composable
fun DrawerContentComponent(
    currentScreen: MutableState<DrawerAppScreen>, closeDrawer: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colors.background,
                shape = RoundedCornerShape(topEnd = DIMENS_20dp, bottomEnd = DIMENS_20dp)
            )
    ) {

        Row(
            modifier = Modifier
                .padding(
                    start = 20.dp,
                    top = DIMENS_24dp,
                    bottom = DIMENS_16dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.glitter_icon), // Replace with your logo drawable resource
                contentDescription = "Logo",
                modifier = Modifier
                    .size(40.dp) // Adjust the size as needed
                    .padding(end = DIMENS_8dp) // Space between the logo and the text
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = Color(0xFF4E5053), // Color for "AI"
                        fontWeight = FontWeight.Bold,
                        fontSize = TEXT_SIZE_24sp
                    )) {
                        append("AI ")
                    }
                    withStyle(style = SpanStyle(
                        color = Color(0xFF9AA0A6), // Color for "Keep"
                        fontWeight = FontWeight.Normal,
                        fontSize = TEXT_SIZE_24sp
                    )) {
                        append("Keep")
                    }
                },
                modifier = Modifier
                    .padding(start = DIMENS_8dp) // Adjust padding if needed
            )
        }

        Spacer(modifier = Modifier.height(DIMENS_16dp))

        for (index in DrawerAppScreen.values().indices) {

            val screen = getScreenBasedOnIndex(index)
            Column(
                modifier =
                Modifier.clickable(onClick = {
                currentScreen.value = screen
                closeDrawer()
                }),
                content = {

                // that bar of selected item
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = DIMENS_8dp)
                        .clip(shape = RoundedCornerShape(50)),

                    color = if (currentScreen.value == screen) {
                        SelectedOptionColor
                    } else {
                        MaterialTheme.colors.background
                    }
                ) {


                    Row {
                        Image(
                            painter = when (screen.name) {
                                DrawerAppScreen.Notes.toString() -> {
                                    painterResource(R.drawable.drawer_notes_icon)
                                }

                                DrawerAppScreen.Reminders.toString() -> {
                                    painterResource(R.drawable.icon_bell2)
                                }

                                DrawerAppScreen.Categories.toString() -> {
                                    painterResource(R.drawable.categories_icon)
                                }

                                DrawerAppScreen.Archive.toString() -> {
                                    painterResource(R.drawable.archive)
                                }

                                DrawerAppScreen.Deleted.toString() -> {
                                    painterResource(R.drawable.bin)
                                }

                                DrawerAppScreen.Settings.toString() -> {
                                    painterResource(R.drawable.settings_icon)
                                }

                                DrawerAppScreen.Help.toString() -> {
                                    painterResource(R.drawable.help_icon)
                                }

                                else -> {
                                    painterResource(R.drawable.notes_icon)
                                }
                            },
                            contentDescription = "icons_bulb",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = DIMENS_16dp)
                                .size(DIMENS_24dp),
//                            colorFilter = ColorFilter.tint(Color.Black) // Apply black tint to the icons

                        )
                        Text(
                            text = screen.name,
                            fontFamily = FontFamily.Monospace, // Use Monospace font
                            fontWeight = FontWeight(150),
                            modifier = Modifier.padding(16.dp),
                            fontSize = TEXT_SIZE_16sp,
                            color = MaterialTheme.colors.onBackground // Set color based on theme
                        )
                    }




                }
            })
        }
    }
}

fun getScreenBasedOnIndex(index: Int) = when (index) {
    0 -> DrawerAppScreen.Notes
    1 -> DrawerAppScreen.Reminders
    2 -> DrawerAppScreen.Categories
    3 -> DrawerAppScreen.Archive
    4 -> DrawerAppScreen.Deleted
    5 -> DrawerAppScreen.Settings
    6 -> DrawerAppScreen.Help
    else -> DrawerAppScreen.Notes
}

@Composable
fun BodyContentComponent(
    currentScreen: DrawerAppScreen,
    openDrawer: () -> Unit,
    navController: NavController
) {
    when (currentScreen) {


        DrawerAppScreen.Notes -> AllNotesScreen(
            navController = navController,
            openDrawer = openDrawer,

        )



        DrawerAppScreen.Reminders -> AllNotesScreen (
        navController = navController,
        openDrawer = openDrawer,
        category = "Reminder"
        )

        DrawerAppScreen.Categories -> CategoriesScreen (
            navController = navController,
            openDrawer = openDrawer,
        )



        DrawerAppScreen.Archive -> AllNotesScreen(
            navController = navController,
            openDrawer = openDrawer,
            category = "archived"
        )

        DrawerAppScreen.Deleted -> AllNotesScreen(
            navController = navController,
            openDrawer = openDrawer,
            category = "deleted"
        )

        DrawerAppScreen.Settings -> SettingsScreen()

        DrawerAppScreen.Help -> HelpScreen()

        else -> {
            AllNotesScreen(
                navController = navController,
                openDrawer = openDrawer
            )
        }
    }
}



@Composable
fun Screen2Component(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeScreenTopBar(onClickAction = openDrawer, onSearch = {}, onChangeView = {}, navController = rememberNavController())
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(text = "Keep Notes 2")
                })
        }
    }
}

@Composable
fun Screen3Component(openDrawer: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        HomeScreenTopBar(onClickAction = openDrawer, onSearch = {}, onChangeView = {}, navController = rememberNavController())
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.weight(1f)) {
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Text(text = "Keep Notes 3")
                })
        }
    }
}




enum class DrawerAppScreen {
    Notes, Reminders, Categories, Archive, Deleted, Settings, Help
}

@Preview
@Composable
fun DrawerAppComponentPreview() {
    DrawerAppComponent(navController = rememberNavController())
}
