package com.example.bookmarks_presentation.ui_elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookmarks_domain.models.Category
import kotlinx.coroutines.selects.select

@Composable
fun CustomDropDownMenu(listOfCategories:List<Category>,onExpanded:()-> Unit, isExpanded: Boolean) {

    Box( modifier = Modifier
        .fillMaxWidth()){
        Column {


            DropdownHeader(
                selectedOption = "Select", onClick = {
                    onExpanded()
                }
            )
            DropdownBody(
                menuItems = listOfCategories,
                expanded = isExpanded,
                onOptionSelected = { option ->

                }
            )
        }
    }


}
@Composable
fun DropdownHeader(
    selectedOption: String,
    onClick: () -> Unit
) {
    // Styling parameters (customize as needed)
    val backgroundColor = Color(0xFF1E1E1E)
    val textColor = Color.White
    val arrowIcon = if (true) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor)
            .clickable() { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = selectedOption,
            color = textColor,
        )
        Icon(
            imageVector = arrowIcon,
            contentDescription = null,
            tint = textColor
        )
    }
}
@Composable
fun DropdownBody(
    menuItems: List<Category>,
    expanded: Boolean,
    onOptionSelected: (String) -> Unit
) {
    // Animation for showing/hiding the menu
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2E2E2E))
        ) {
            menuItems.forEach { item ->
                DropdownMenuItem(text = item.tittle, onClick = {

                })
            }
        }
    }
}

@Composable
fun DropdownMenuItem(
    text: String,
    onClick: () -> Unit
) {
    val textColor = Color.White

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}

