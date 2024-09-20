package com.example.reltyleads.navigation.details

import com.example.reltyleads.navigation.NavigationDestination

internal object DetailsDestination: NavigationDestination {

    override val route: String = "booking?bookingId={bookingId}"

    override val destination: String = "booking"
}