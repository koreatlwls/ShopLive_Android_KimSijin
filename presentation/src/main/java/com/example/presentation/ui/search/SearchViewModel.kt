package com.example.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(

): ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    fun setSearchQuery(query : String){
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

}