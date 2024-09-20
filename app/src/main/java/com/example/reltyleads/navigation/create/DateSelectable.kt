package com.example.reltyleads.navigation.create

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates

@OptIn(ExperimentalMaterial3Api::class)
class DateSelectable : SelectableDates {

    override fun isSelectableDate(utcTimeMillis: Long) : Boolean {
        return utcTimeMillis > System.currentTimeMillis()
    }

    /**
     * Returns true if a given [year] should be enabled for selection in the UI. When a year is
     * defined as non selectable, all the dates in that year will also be non selectable.
     */
    override fun isSelectableYear(year: Int) : Boolean {
        return false
    }
}