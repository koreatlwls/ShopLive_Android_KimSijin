package com.example.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.search.GetMarvelCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel  @Inject constructor(
    private val getMarvelCharacterUseCase: GetMarvelCharacterUseCase
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    fun setSearchQuery(query : String){
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

}