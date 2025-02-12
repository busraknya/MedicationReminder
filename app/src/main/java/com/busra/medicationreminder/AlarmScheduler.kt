package com.busra.medicationreminder

interface AlarmScheduler {
    fun schedule(item: MedicationItem)
    fun cancel(item: MedicationItem)
}