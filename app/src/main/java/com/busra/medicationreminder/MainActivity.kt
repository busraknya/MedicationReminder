package com.busra.medicationreminder

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.busra.medicationreminder.ui.theme.MedicationReminderTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
        val scheduler = AndroidAlarmScheduler(this)
        var alarmItem: MedicationItem? = null

        setContent {
            MedicationReminderTheme {
                var selectedDate by remember { mutableStateOf(LocalDate.now()) }
                var selectedTime by remember { mutableStateOf(LocalTime.now()) }
                var message by remember { mutableStateOf("") }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = selectedDate.toString(),
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        label = { Text("Select Date") },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker { date -> selectedDate = date } }) {
                                Icon(Icons.Default.DateRange, contentDescription = "Pick Date")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = selectedTime.toString(),
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        label = { Text("Select Time") },
                        trailingIcon = {
                            IconButton(onClick = { showTimePicker { time -> selectedTime = time } }) {
                                Icon(Icons.Default.DateRange , contentDescription = "Pick Time")
                            }
                        }
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Message") },
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = {
                            val dateTime = LocalDateTime.of(selectedDate, selectedTime)
                            alarmItem = MedicationItem(
                                time = dateTime,
                                message = message
                            )
                            alarmItem?.let(scheduler::schedule)
                            message = ""
                        }) {
                            Text(text = "Schedule")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            alarmItem?.let(scheduler::cancel)
                        }) {
                            Text(text = "Cancel")
                        }
                    }
                }
            }
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                println("Bildirim izni verildi")
            } else {
                println("Bildirim izni reddedildi")
            }
        }
    }



    private fun showDatePicker(onDateSelected: (LocalDate) -> Unit) {
        val today = LocalDate.now()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            today.year, today.monthValue - 1, today.dayOfMonth
        ).show()
    }

    private fun showTimePicker(onTimeSelected: (LocalTime) -> Unit) {
        val now = LocalTime.now()
        TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                onTimeSelected(LocalTime.of(hourOfDay, minute))
            },
            now.hour, now.minute, true
        ).show()
    }
}
