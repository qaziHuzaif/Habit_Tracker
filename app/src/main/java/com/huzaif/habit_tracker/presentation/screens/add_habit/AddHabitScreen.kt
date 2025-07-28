package com.huzaif.habit_tracker.presentation.screens.add_habit

import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.huzaif.habit_tracker.presentation.common.TopBar
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHabitScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    var habitName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val dateFormatter = DateTimeFormatter.ofPattern("d/MM/y")
    val timeFormatter = DateTimeFormatter.ofPattern("h:m a")
    var startDate by remember { mutableStateOf(LocalDate.now()) }
    var endDate by remember { mutableStateOf(LocalDate.now().plusWeeks(2)) }
    var useEndDate by remember { mutableStateOf(false) }
    var setReminder by remember { mutableStateOf(false) }
    var reminder by remember { mutableStateOf<LocalTime?>(LocalTime.now()) }
    var priority by remember { mutableIntStateOf(1) }

    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }
    val timeDialogState = rememberMaterialDialogState()

    Scaffold(
        topBar = { TopBar(title = "Add Habit") },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                InputTextBox(
                    input = habitName,
                    onInputChange = { habitName = it },
                    label = "Habit",
                    placeholder = "e.g., Android",
                )

                Spacer(modifier = Modifier.height(12.dp))

                InputTextBox(
                    input = description,
                    onInputChange = { description = it },
                    label = "Description (optional)",
                    placeholder = "e.g., Learn Android development",
                    isSingleLine = false
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Start Date: ", fontSize = 16.sp)
                    Text(
                        text = startDate.format(dateFormatter),
                        fontSize = 14.sp,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                shape = MaterialTheme.shapes.small
                            )
                            .border(
                                1.dp,
                                MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 10.dp, vertical = 8.dp)
                            .clickable { showStartDatePicker = true }
                    )
                }

                if (showStartDatePicker) {
                    val startDatePickerState = rememberDatePickerState(
                        initialSelectedDateMillis = startDate.atStartOfDay()
                            .toInstant(ZoneOffset.UTC).toEpochMilli(),
                        initialDisplayMode = DisplayMode.Picker
                    )
                    DatePickerDialog(
                        onDismissRequest = { showStartDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                startDatePickerState.selectedDateMillis?.let {
                                    startDate = LocalDate.ofEpochDay(it / (1000 * 60 * 60 * 24))
                                }
                                showStartDatePicker = false
                            }) { Text("OK") }
                        }
                    ) {
                        DatePicker(state = startDatePickerState)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Use End Date")
                    Switch(
                        checked = useEndDate,
                        onCheckedChange = { useEndDate = it },
                        colors = SwitchDefaults.colors()
                            .copy(checkedThumbColor = MaterialTheme.colorScheme.background)
                    )
                }

                if (useEndDate) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("End Date: ", fontSize = 16.sp)
                        Text(
                            text = endDate.format(dateFormatter),
                            fontSize = 14.sp,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                    shape = MaterialTheme.shapes.small
                                )
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .clickable { showEndDatePicker = true }
                        )
                        if (showEndDatePicker) {
                            val endDatePickerState = rememberDatePickerState(
                                initialSelectedDateMillis = endDate.atStartOfDay()
                                    .toInstant(ZoneOffset.UTC).toEpochMilli(),
                                initialDisplayMode = DisplayMode.Picker,
                                selectableDates = object : SelectableDates {
                                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                                        val selectedDate =
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                                                LocalDate.ofInstant(
                                                    Instant.ofEpochMilli(
                                                        utcTimeMillis
                                                    ), ZoneId.systemDefault()
                                                )
                                            } else {
                                                LocalDate.ofEpochDay(utcTimeMillis / (1000 * 60 * 60 * 24))
                                            }
                                        return selectedDate.isAfter(startDate)
                                    }
                                }
                            )
                            DatePickerDialog(
                                onDismissRequest = { showEndDatePicker = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        endDatePickerState.selectedDateMillis?.let {
                                            endDate =
                                                LocalDate.ofEpochDay(it / (1000 * 60 * 60 * 24))
                                        }
                                        showEndDatePicker = false
                                    }) { Text("OK") }
                                }) {
                                DatePicker(state = endDatePickerState)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Set Reminder")
                    Switch(
                        checked = setReminder,
                        onCheckedChange = { setReminder = it },
                        colors = SwitchDefaults.colors()
                            .copy(checkedThumbColor = MaterialTheme.colorScheme.background)
                    )
                }

                if (setReminder) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Reminder: ", fontSize = 16.sp)
                        Text(
                            text = reminder?.format(timeFormatter) ?: "Not Set",
                            fontSize = 14.sp,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                                    shape = MaterialTheme.shapes.small
                                )
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.primary,
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(horizontal = 10.dp, vertical = 8.dp)
                                .clickable { timeDialogState.show() }
                        )
                    }

                    MaterialDialog(
                        dialogState = timeDialogState,
                        buttons = {
                            positiveButton(
                                text = "Ok",
                                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
                            )
                            negativeButton(
                                text = "Cancel",
                                textStyle = TextStyle(color = MaterialTheme.colorScheme.primary)
                            )
                        },
                        backgroundColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
                        timepicker(
                            title = "Pick a time",
                            colors = TimePickerDefaults.colors(
                                activeBackgroundColor = MaterialTheme.colorScheme.primary,
                                activeTextColor = MaterialTheme.colorScheme.onBackground,
                                inactiveTextColor = MaterialTheme.colorScheme.onBackground,
                                selectorColor = MaterialTheme.colorScheme.primary,
                                selectorTextColor = MaterialTheme.colorScheme.onBackground,
                                headerTextColor = MaterialTheme.colorScheme.onBackground,
                            )
                        ) {
                            reminder = it
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Priority: ", fontSize = 16.sp)
                    OutlinedTextField(
                        value = priority.toString(),
                        onValueChange = { priority = it.toInt() },
                        singleLine = true,
                        textStyle = TextStyle().copy(textAlign = TextAlign.Center),
                        colors = TextFieldDefaults.colors().copy(
                            cursorColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .wrapContentHeight()
                            .widthIn(max = 80.dp)
                    )
                }

            }

            ActionHandler(navController, startDate > endDate || habitName.isBlank())
        }
    }
}


@Composable
private fun ActionHandler(navController: NavHostController, isError: Boolean) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                navController.navigateUp()
            },
        ) {
            Text(
                text = "Cancel",
                fontSize = 16.sp
            )
        }
        Button(
            onClick = {
                if (isError) {
                    Toast.makeText(
                        context,
                        "Start date can not be greater than end date",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    navController.navigateUp()
                }
            },
        ) {
            Text(
                text = "Save",
                fontSize = 16.sp
            )
        }
    }
}


@Composable
private fun InputTextBox(
    input: String,
    onInputChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isSingleLine: Boolean = true
) {
    OutlinedTextField(
        value = input,
        onValueChange = { onInputChange(it) },
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        singleLine = isSingleLine,
        colors = TextFieldDefaults.colors().copy(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
//            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        modifier = Modifier.fillMaxWidth(),
        isError = input.isEmpty() && input.isNotBlank()
    )
}


@Preview
@Composable
private fun PreviewAddHabitScreen() {
    AddHabitScreen(navController = rememberNavController())
}