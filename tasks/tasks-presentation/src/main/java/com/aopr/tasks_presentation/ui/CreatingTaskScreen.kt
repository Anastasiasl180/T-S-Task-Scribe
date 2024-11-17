package com.aopr.tasks_presentation.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.changedToUp
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.aopr.tasks_domain.models.ImportanceOfTask
import com.aopr.tasks_presentation.events.creating_task_events.CreatingTaskEvents
import com.aopr.tasks_presentation.ui.UiHandlers.CreatingTaskUiEventHandler
import com.aopr.tasks_presentation.viewModels.CreatingTaskViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatingTaskScreen() {
    CreatingTaskUiEventHandler()

    val viewModel = koinViewModel<CreatingTaskViewModel>()
    val priority by viewModel.priority.collectAsState()

    val heightScreen = LocalConfiguration.current.screenHeightDp
    val tittle by viewModel.tittleOfTask.collectAsState()
    val description by viewModel.descriptionOfTask.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.DarkGray),
                actions = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Row(
                            modifier = Modifier.fillMaxWidth(0.95f),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = { viewModel.onEvent(CreatingTaskEvents.NavigateToBack) },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .size(50.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "",
                                    tint = Color.Black
                                )
                            }
                            IconButton(
                                onClick = { viewModel.onEvent(CreatingTaskEvents.SaveTask) },
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .size(50.dp)
                            ) {
                                Text(text = stringResource(id = com.aopr.shared_ui.R.string.PlusOnButton))
                            }

                        }
                    }
                },
                title = { /*TODO*/ })
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    top = paddingValues.calculateTopPadding(),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr),
                )
                .background(Color.DarkGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.95f)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .height((heightScreen * 0.15).dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = stringResource(id = com.aopr.shared_domain.R.string.tittle))
                            TextField(
                                shape = MaterialTheme.shapes.medium,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                placeholder = {
                                    Text(text = stringResource(id = com.aopr.shared_domain.R.string.tittle))
                                },
                                value = tittle,
                                onValueChange = {
                                    viewModel.onEvent(
                                        CreatingTaskEvents.UpdateTittleOfTask(
                                            it
                                        )
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),

                                )
                        }

                    }
                    item {
                        Column(
                            modifier = Modifier
                                .height((heightScreen * 0.28f).dp)
                                .fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = stringResource(id = com.aopr.shared_domain.R.string.description))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {

                                TextField(
                                    value = description,
                                    shape = MaterialTheme.shapes.medium,
                                    placeholder = {
                                        Text(text = stringResource(id = com.aopr.shared_domain.R.string.description))
                                    },
                                    onValueChange = {
                                        viewModel.onEvent(
                                            CreatingTaskEvents.UpdateDescriptionOfTask(
                                                it
                                            )
                                        )
                                    },
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(170.dp),
                                    maxLines = 20,
                                )
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height((heightScreen * 0.2).dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.5f),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(text = "date")
                                }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .fillMaxHeight(0.6f)
                                ) {
                                    Text(text = "fdgd")
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.End
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(text = "time")
                                }
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .fillMaxHeight(0.6f)
                                ) {
                                    Text(text = "dgf")
                                }
                            }
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(), horizontalArrangement = Arrangement.End
                        ) {
                            val items = ImportanceOfTask.entries
                            SegmentedDemo(
                                items = items,
                                selectedItem = priority,
                                onImportanceChange = { selectedPriority ->
                                    viewModel.onEvent(
                                        CreatingTaskEvents.UpdatePriorityOfTask(
                                            selectedPriority
                                        )
                                    )
                                }
                            )
                        }
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .height((heightScreen * 0.05).dp)
                                .fillMaxWidth(), horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { viewModel.onEvent(CreatingTaskEvents.AddTextFieldForSubTask) },
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "")
                            }
                        }
                    }
                    itemsIndexed(viewModel.listOfSubTasks) { index, subTask ->
                        SubTaskCard(
                            modifier = Modifier.padding(8.dp),
                            tittle = subTask.description,
                            onValueChange = { newDescription ->
                                viewModel.onEvent(
                                    CreatingTaskEvents.UpdateTempSubTaskDescription(
                                        index,
                                        newDescription
                                    )
                                )
                            },
                            onCheckedChange = { isChecked ->
                                viewModel.onEvent(
                                    CreatingTaskEvents.UpdateTempSubTaskIsDone(
                                        index,
                                        isChecked
                                    )
                                )
                            },
                            isCompleted = subTask.isCompleted
                        )
                    }

                }

            }
        }


    }

}

@Composable
fun SubTaskCard(
    modifier: Modifier,
    tittle: String,
    onValueChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    isCompleted: Boolean,
) {

    Card(modifier = modifier) {
        Column {
            TextField(value = tittle, onValueChange = onValueChange)
            Checkbox(checked = isCompleted, onCheckedChange = onCheckedChange)
        }
    }


}


@Composable
fun SegmentedDemo(
    items: List<ImportanceOfTask>,
    selectedItem: ImportanceOfTask, onImportanceChange: (ImportanceOfTask) -> Unit
) {

    Column(Modifier.padding(16.dp), verticalArrangement = spacedBy(16.dp)) {
        Text("Priority", style = MaterialTheme.typography.bodyLarge)

        SegmentedControl(
            items,
            selectedItem,
            onSegmentSelected = { onImportanceChange(it) }
        ) {
            Text(it)
        }
    }
}


private const val NO_SEGMENT_INDEX = -1

/** Padding inside the track. */
private val TRACK_PADDING = 2.dp

private val TRACK_COLOR = Color.LightGray.copy(alpha = .5f)

/** Additional padding to inset segments and the thumb when pressed. */
private val PRESSED_TRACK_PADDING = 1.dp

/** Padding inside individual segments. */
private val SEGMENT_PADDING = 5.dp

/** Alpha to use to indicate pressed state when unselected segments are pressed. */
private const val PRESSED_UNSELECTED_ALPHA = .6f
private val BACKGROUND_SHAPE = RoundedCornerShape(8.dp)

@Composable
fun SegmentedControl(
    segments: List<ImportanceOfTask>,
    selectedSegment: ImportanceOfTask,
    onSegmentSelected: (ImportanceOfTask) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (String) -> Unit
) {
    val state = remember { SegmentedControlState() }
    state.segmentCount = segments.size
    state.selectedSegment = segments.indexOf(selectedSegment)
    state.onSegmentSelected = { onSegmentSelected(segments[it]) }

    // Animate between whole-number indices so we don't need to do pixel calculations.
    val selectedIndexOffset by animateFloatAsState(state.selectedSegment.toFloat(), label = "")

    // Use a custom layout so that we can measure the thumb using the height of the segments. The thumb
    // is whole composable that draws itself – this layout is just responsible for placing it under
    // the correct segment.
    Layout(
        content = {
            // Each of these produces a single measurable.
            Thumb(state)
            Dividers(state)
            Segments(state, segments, content)
        },
        modifier = modifier
            .fillMaxWidth()
            .then(state.inputModifier)
            .background(TRACK_COLOR, BACKGROUND_SHAPE)
            .padding(TRACK_PADDING)
    ) { measurable, constraints ->
        val (thumbMeasurable, dividersMeasurable, segmentsMeasurable) = measurable

        // Measure the segments first so we know how tall to make the thumb.
        val segmentsPlaceable = segmentsMeasurable.measure(constraints)
        state.updatePressedScale(segmentsPlaceable.height, this)

        // Now we can measure the thumb and dividers to be the right size.
        val thumbPlaceable = thumbMeasurable.measure(
            Constraints.fixed(
                width = segmentsPlaceable.width / segments.size,
                height = segmentsPlaceable.height
            )
        )
        val dividersPlaceable = dividersMeasurable.measure(
            Constraints.fixed(
                width = segmentsPlaceable.width,
                height = segmentsPlaceable.height
            )
        )

        layout(segmentsPlaceable.width, segmentsPlaceable.height) {
            val segmentWidth = segmentsPlaceable.width / segments.size

            // Place the thumb first since it should be drawn below the segments.
            thumbPlaceable.placeRelative(
                x = (selectedIndexOffset * segmentWidth).toInt(),
                y = 0
            )
            dividersPlaceable.placeRelative(IntOffset.Zero)
            segmentsPlaceable.placeRelative(IntOffset.Zero)
        }
    }
}

@Composable
private fun Thumb(state: SegmentedControlState) {
    Box(
        Modifier
            .then(
                state.segmentScaleModifier(
                    pressed = state.pressedSegment == state.selectedSegment,
                    segment = state.selectedSegment
                )
            )
            .shadow(4.dp, BACKGROUND_SHAPE)
            .background(Color.White, BACKGROUND_SHAPE)
    )
}

/**
 * Draws dividers between segments. No dividers are drawn around the selected segment.
 */
@Composable
private fun Dividers(state: SegmentedControlState) {
    // Animate each divider independently.
    val alphas = (0 until state.segmentCount).map { i ->
        val selectionAdjacent = i == state.selectedSegment || i - 1 == state.selectedSegment
        animateFloatAsState(if (selectionAdjacent) 0f else 1f, label = "")
    }

    Canvas(Modifier.fillMaxSize()) {
        val segmentWidth = size.width / state.segmentCount
        val dividerPadding = TRACK_PADDING + PRESSED_TRACK_PADDING

        alphas.forEachIndexed { i, alpha ->
            val x = i * segmentWidth
            drawLine(
                Color.White,
                alpha = alpha.value,
                start = Offset(x, dividerPadding.toPx()),
                end = Offset(x, size.height - dividerPadding.toPx())
            )
        }
    }
}

/**
 * Draws the actual segments in a [SegmentedControl].
 */
@Composable
private fun Segments(
    state: SegmentedControlState,
    segments: List<ImportanceOfTask>,
    content: @Composable (String) -> Unit
) {
    CompositionLocalProvider(
        LocalTextStyle provides TextStyle(fontWeight = FontWeight.Medium)
    ) {
        Row(
            horizontalArrangement = spacedBy(TRACK_PADDING),
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
        ) {
            segments.forEachIndexed { i, segment ->
                val isSelected = i == state.selectedSegment
                val isPressed = i == state.pressedSegment

                // Unselected presses are represented by fading.
                val alpha by animateFloatAsState(
                    if (!isSelected && isPressed) PRESSED_UNSELECTED_ALPHA else 1f,
                    label = ""
                )

                // We can't use Modifier.selectable because it does way too much: it does its own input
                // handling and wires into Composes indication/interaction system, which we don't want because
                // we've got our own indication mechanism.
                val semanticsModifier = Modifier.semantics(mergeDescendants = true) {
                    selected = isSelected
                    role = Role.Button
                    onClick { state.onSegmentSelected(i); true }
                    stateDescription = if (isSelected) "Selected" else "Not selected"
                }

                Box(
                    Modifier
                        // Divide space evenly between all segments.
                        .weight(1f)
                        .then(semanticsModifier)
                        .padding(SEGMENT_PADDING)
                        // Draw pressed indication when not selected.
                        .alpha(alpha)
                        // Selected presses are represented by scaling.
                        .then(state.segmentScaleModifier(isPressed && isSelected, i))
                        // Center the segment content.
                        .wrapContentWidth()
                ) {
                    content(segment.name)
                }
            }
        }
    }
}

private class SegmentedControlState {
    var segmentCount by mutableIntStateOf(0)
    var selectedSegment by mutableIntStateOf(0)
    var onSegmentSelected: (Int) -> Unit by mutableStateOf({})
    var pressedSegment by mutableIntStateOf(NO_SEGMENT_INDEX)

    /**
     * Scale factor that should be used to scale pressed segments (both the segment itself and the
     * thumb). When this scale is applied, exactly [PRESSED_TRACK_PADDING] will be added around the
     * element's usual size.
     */
    var pressedSelectedScale by mutableFloatStateOf(1f)
        private set

    /**
     * Calculates the scale factor we need to use for pressed segments to get the desired padding.
     */
    fun updatePressedScale(controlHeight: Int, density: Density) {
        with(density) {
            val pressedPadding = PRESSED_TRACK_PADDING * 2
            val pressedHeight = controlHeight - pressedPadding.toPx()
            pressedSelectedScale = pressedHeight / controlHeight
        }
    }

    /**
     * Returns a [Modifier] that will scale an element so that it gets [PRESSED_TRACK_PADDING] extra
     * padding around it. The scale will be animated.
     *
     * The scale is also performed around either the left or right edge of the element if the [segment]
     * is the first or last segment, respectively. In those cases, the scale will also be translated so
     * that [PRESSED_TRACK_PADDING] will be added on the left or right edge.
     */
    @SuppressLint("ModifierFactoryExtensionFunction")
    fun segmentScaleModifier(
        pressed: Boolean,
        segment: Int,
    ): Modifier = Modifier.composed {
        val scale by animateFloatAsState(if (pressed) pressedSelectedScale else 1f, label = "")
        val xOffset by animateDpAsState(if (pressed) PRESSED_TRACK_PADDING else 0.dp, label = "")

        graphicsLayer {
            this.scaleX = scale
            this.scaleY = scale

            // Scales on the ends should gravitate to that edge.
            this.transformOrigin = TransformOrigin(
                pivotFractionX = when (segment) {
                    0 -> 0f
                    segmentCount - 1 -> 1f
                    else -> .5f
                },
                pivotFractionY = .5f
            )

            // But should still move inwards to keep the pressed padding consistent with top and bottom.
            this.translationX = when (segment) {
                0 -> xOffset.toPx()
                segmentCount - 1 -> -xOffset.toPx()
                else -> 0f
            }
        }
    }

    /**
     * A [Modifier] that will listen for touch gestures and update the selected and pressed properties
     * of this state appropriately.
     *
     * Input will be reset if the [segmentCount] changes.
     */
    val inputModifier = Modifier.pointerInput(segmentCount) {
        val segmentWidth = size.width / segmentCount

        // Helper to calculate which segment an event occurred in.
        fun segmentIndex(change: PointerInputChange): Int =
            ((change.position.x / size.width.toFloat()) * segmentCount)
                .toInt()
                .coerceIn(0, segmentCount - 1)

        forEachGesture {
            awaitPointerEventScope {
                val down = awaitFirstDown()

                pressedSegment = segmentIndex(down)
                val downOnSelected = pressedSegment == selectedSegment
                val segmentBounds = Rect(
                    left = pressedSegment * segmentWidth.toFloat(),
                    right = (pressedSegment + 1) * segmentWidth.toFloat(),
                    top = 0f,
                    bottom = size.height.toFloat()
                )

                // Now that the pointer is down, the rest of the gesture depends on whether the segment that
                // was "pressed" was selected.
                if (downOnSelected) {
                    // When the selected segment is pressed, it can be dragged to other segments to animate the
                    // thumb moving and the segments scaling.
                    horizontalDrag(down.id) { change ->
                        pressedSegment = segmentIndex(change)

                        // Notify the SegmentedControl caller when the pointer changes segments.
                        if (pressedSegment != selectedSegment) {
                            onSegmentSelected(pressedSegment)
                        }
                    }
                } else {
                    // When an unselected segment is pressed, we just animate the alpha of the segment while
                    // the pointer is down. No dragging is supported.
                    waitForUpOrCancellation(inBounds = segmentBounds)
                        // Null means the gesture was cancelled (e.g. dragged out of bounds).
                        ?.let { onSegmentSelected(pressedSegment) }
                }

                // In either case, once the gesture is cancelled, stop showing the pressed indication.
                pressedSegment = NO_SEGMENT_INDEX
            }
        }
    }
}

/**
 * Copy of nullable waitForUpOrCancellation that works with bounds that may not be at 0,0.
 */
private suspend fun AwaitPointerEventScope.waitForUpOrCancellation(inBounds: Rect): PointerInputChange? {
    while (true) {
        val event = awaitPointerEvent(PointerEventPass.Main)
        if (event.changes.all { it.changedToUp() }) {
            // All pointers are up
            return event.changes[0]
        }

        if (event.changes.any { it.consumed.downChange || !inBounds.contains(it.position) }) {
            return null // Canceled
        }

        // Check for cancel by position consumption. We can look on the Final pass of the
        // existing pointer event because it comes after the Main pass we checked above.
        val consumeCheck = awaitPointerEvent(PointerEventPass.Final)
        if (consumeCheck.changes.any { it.isConsumed }) {
            return null
        }
    }
}
