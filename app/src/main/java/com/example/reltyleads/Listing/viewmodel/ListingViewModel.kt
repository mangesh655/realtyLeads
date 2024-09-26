package com.example.reltyleads.Listing.viewmodel

import androidx.paging.PagingData
import com.example.reltyleads.Listing.domain.ListingUseCase
import com.example.reltyleads.common.BaseViewModel
import com.example.reltyleads.persistence.database.entity.BookingDb
import com.example.reltyleads.repository.model.Booking
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(
    private val listingUseCase: ListingUseCase
) : BaseViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
    }

    fun onToggleSearch() {
        _isSearching.value = !_isSearching.value
        if (!_isSearching.value) {
            onSearchTextChanged("")
        }
    }

    val pagingDataFlow: Flow<PagingData<BookingDb>> = listingUseCase.listingPagingData()
}