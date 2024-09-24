package com.example.keepnotes.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatAlignLeft
import androidx.compose.material.icons.automirrored.outlined.FormatAlignRight
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.FormatAlignCenter
import androidx.compose.material.icons.outlined.FormatBold
import androidx.compose.material.icons.outlined.FormatItalic
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.FormatSize
import androidx.compose.material.icons.outlined.FormatStrikethrough
import androidx.compose.material.icons.outlined.FormatUnderlined
import androidx.compose.material.icons.outlined.Link
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.model.RichTextState

@Composable
fun KeepNotePanel(
    state: RichTextState,
    openLinkDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    onCloseEditor: ()-> Unit
) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        item {
            KeepNotePanelButton(
                onClick = {
                    state.addParagraphStyle(
                        ParagraphStyle(
                            textAlign = TextAlign.Left,
                        )
                    )
                },
                isSelected = state.currentParagraphStyle.textAlign == TextAlign.Left,
                icon = Icons.AutoMirrored.Outlined.FormatAlignLeft
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.addParagraphStyle(
                        ParagraphStyle(
                            textAlign = TextAlign.Center
                        )
                    )
                },
                isSelected = state.currentParagraphStyle.textAlign == TextAlign.Center,
                icon = Icons.Outlined.FormatAlignCenter
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.addParagraphStyle(
                        ParagraphStyle(
                            textAlign = TextAlign.Right
                        )
                    )
                },
                isSelected = state.currentParagraphStyle.textAlign == TextAlign.Right,
                icon = Icons.AutoMirrored.Outlined.FormatAlignRight
            )
        }

        item {
            Box(
                Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(Color(0xFF393B3D))
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                isSelected = state.currentSpanStyle.fontWeight == FontWeight.Bold,
                icon = Icons.Outlined.FormatBold
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            fontStyle = FontStyle.Italic
                        )
                    )
                },
                isSelected = state.currentSpanStyle.fontStyle == FontStyle.Italic,
                icon = Icons.Outlined.FormatItalic
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.Underline
                        )
                    )
                },
                isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.Underline) == true,
                icon = Icons.Outlined.FormatUnderlined
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            textDecoration = TextDecoration.LineThrough
                        )
                    )
                },
                isSelected = state.currentSpanStyle.textDecoration?.contains(TextDecoration.LineThrough) == true,
                icon = Icons.Outlined.FormatStrikethrough
            )
        }

        item {
            Box(
                Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(Color(0xFF393B3D))
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    openLinkDialog.value = true
                },
                isSelected = state.isLink,
                icon = Icons.Outlined.Link
            )
        }

        item {
            Box(
                Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(Color(0xFF393B3D))
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            fontSize = 28.sp
                        )
                    )
                },
                isSelected = state.currentSpanStyle.fontSize == 28.sp,
                icon = Icons.Outlined.FormatSize
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            color = Color.Red
                        )
                    )
                },
                isSelected = state.currentSpanStyle.color == Color.Red,
                icon = Icons.Filled.Circle,
                tint = Color.Red
            )
        }


        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            color = Color.Blue
                        )
                    )
                },
                isSelected = state.currentSpanStyle.color == Color.Blue,
                icon = Icons.Filled.Circle,
                tint = Color.Blue
            )
        }





        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            color = Color.Green
                        )
                    )
                },
                isSelected = state.currentSpanStyle.color == Color.Green,
                icon = Icons.Filled.Circle,
                tint = Color.Green
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            color = Color.Cyan
                        )
                    )
                },
                isSelected = state.currentSpanStyle.color == Color.Cyan,
                icon = Icons.Filled.Circle,
                tint = Color.Cyan
            )
        }


        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            color = Color.Magenta
                        )
                    )
                },
                isSelected = state.currentSpanStyle.color == Color.Magenta,
                icon = Icons.Filled.Circle,
                tint = Color.Magenta
            )
        }



        item {
            Box(
                Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(Color(0xFF393B3D))
            )
        }


        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            background = Color.Yellow
                        )
                    )
                },
                isSelected = state.currentSpanStyle.background == Color.Yellow,
                icon = Icons.Outlined.Circle,
                tint = Color.Yellow
            )
        }


        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            background = Color.Green
                        )
                    )
                },
                isSelected = state.currentSpanStyle.background == Color.Green,
                icon = Icons.Outlined.Circle,
                tint = Color.Green
            )
        }


        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleSpanStyle(
                        SpanStyle(
                            background = Color.Red
                        )
                    )
                },
                isSelected = state.currentSpanStyle.background == Color.Red,
                icon = Icons.Outlined.Circle,
                tint = Color.Red
            )
        }


        item {
            Box(
                Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(Color(0xFF393B3D))
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleUnorderedList()
                },
                isSelected = state.isUnorderedList,
                icon = Icons.AutoMirrored.Outlined.FormatListBulleted,
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleOrderedList()
                },
                isSelected = state.isOrderedList,
                icon = Icons.Outlined.FormatListNumbered,
            )
        }

        item {
            Box(
                Modifier
                    .height(24.dp)
                    .width(1.dp)
                    .background(Color(0xFF393B3D))
            )
        }

        item {
            KeepNotePanelButton(
                onClick = {
                    state.toggleCodeSpan()
                },
                isSelected = state.isCodeSpan,
                icon = Icons.Outlined.Code,
            )
        }


        item {
            KeepNotePanelButton(
                onClick = {
                    onCloseEditor.invoke()
                },
                isSelected = state.isCodeSpan,
                icon = Icons.Outlined.Close,
            )
        }



    }
}