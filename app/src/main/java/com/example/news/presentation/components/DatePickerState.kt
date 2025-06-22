
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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateFilter(
    selectedDate: String,
    onDateChanged: (String) -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Hardcoded strings
        Text(text = "Select Date to Filter News",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp)

        TextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Selected Date") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = { showDatePickerDialog = true },
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag("pickDateButton")
        ) {
            Text("Pick a Date")  // Hardcoded
        }

        if (showDatePickerDialog) {
            DatePickerModal(
                onDateSelected = { selectedMillis ->
                    selectedMillis?.let {
                        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            .format(Date(it))
                        onDateChanged(formattedDate)
                    }
                    showDatePickerDialog = false
                },
                onDismiss = { showDatePickerDialog = false }
            )
        }
    }
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


