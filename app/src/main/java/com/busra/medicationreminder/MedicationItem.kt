package com.busra.medicationreminder

import java.time.LocalDateTime

data class MedicationItem(
    val time: LocalDateTime,
    val message: String
)
