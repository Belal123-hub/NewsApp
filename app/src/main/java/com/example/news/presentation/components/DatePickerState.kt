
package com.example.news.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.news.R
import com.example.news.util.DateFormatter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateFilter(
    selectedDate: String,
    onPickDateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.select_date_label),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        TextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.selected_date)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = onPickDateClick,
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag("pickDateButton")
        ) {
            Text(stringResource(R.string.pick_date))
        }
    }
}

@Composable
fun DateFilterWrapper(onDateChanged: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedDateText by remember { mutableStateOf("") }

    if (showDialog) {
        DatePickerModal(
            onDateSelected = { millis ->
                millis?.let {
                    selectedDateText = DateFormatter.formatToDisplay(it)
                    onDateChanged(selectedDateText)
                }
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    DateFilter(
        selectedDate = selectedDateText,
        onPickDateClick = { showDialog = true }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


