
package com.example.news.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateFilter(
    selectedDate: String,
    onDateChanged: (String) -> Unit
) {
    // State to show DatePicker dialog
    var showDatePickerDialog by remember { mutableStateOf(false) }

    // Track selected date as Long (milliseconds)
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Select Date to Filter News", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        // Display selected date in TextField
        TextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            label = { Text("Selected Date") },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        // Button to trigger DatePicker
        Button(
            onClick = { showDatePickerDialog = true },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Pick a Date")
        }

        // Show DatePickerDialog when triggered
        if (showDatePickerDialog) {
            DatePickerModal(
                onDateSelected = { selectedMillis ->
                    selectedDateMillis = selectedMillis
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(Date(selectedMillis!!))
                    onDateChanged(formattedDate) // Update selected date
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
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


