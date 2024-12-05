package com.example.bookmarks_presentation.ui_elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bookmarks_domain.models.Category
import kotlinx.coroutines.selects.select

@Composable
fun CustomDropDownMenu(
    chosenCategory: Int?,
    listOfCategories: List<Category>,
    onExpanded: () -> Unit,
    isExpanded: Boolean,
    onOptionSelected: (Int?) -> Unit // Accepts nullable Int
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
            DropdownHeader(
                selectedOption = getSelectedOptionTitle(chosenCategory, listOfCategories),
                onClick = { onExpanded() }
            )
            DropdownBody(
                menuItems = listOfCategories,
                expanded = isExpanded,
                onOptionSelected = { categoryId ->
                    onOptionSelected(categoryId)
                }
            )
        }
    }
}

/**
 * Helper function to get the selected category title.
 * Returns "No Category" if chosenCategory is null.
 */
@Composable
fun getSelectedOptionTitle(
    chosenCategory: Int?,
    listOfCategories: List<Category>
): String {
    return listOfCategories.find { it.id == chosenCategory }?.tittle ?: "No Category"
}
@Composable
fun DropdownHeader(
    selectedOption: String,
    onClick: () -> Unit
) {
    val backgroundColor = Color(0xFF1E1E1E)
    val textColor = Color.White
    val arrowIcon = Icons.Default.KeyboardArrowDown

    // State to animate arrow rotation
    val rotationAngle by animateFloatAsState(
        targetValue = if (/* pass isExpanded here */ false) 180f else 0f, // Adjust as needed
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = selectedOption,
            color = textColor,

        )
        Icon(
            imageVector = arrowIcon,
            contentDescription = if (/* pass isExpanded here */ false) "Collapse dropdown" else "Expand dropdown",
            tint = textColor,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .rotate(rotationAngle) // Apply rotation
        )
    }
}@Composable
fun DropdownBody(
    menuItems: List<Category>,
    expanded: Boolean,
    onOptionSelected: (Int?) -> Unit
) {
    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF2E2E2E), shape = RoundedCornerShape(8.dp))
        ) {
            // "No Category" option
            DropdownMenuItem(text = "No Category", onClick = {
                onOptionSelected(null)
            })
            // Existing categories
            menuItems.forEach { item ->
                DropdownMenuItem(text = item.tittle, onClick = {
                    onOptionSelected(item.id)
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
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = text,
            color = textColor,
        )
    }
}