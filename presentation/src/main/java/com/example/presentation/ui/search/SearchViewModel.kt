package com.example.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.usecase.search.GetMarvelCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMarvelCharacterUseCase: GetMarvelCharacterUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow(UiState.Empty)
    val uiState = _uiState.asStateFlow()

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

}

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Success(val list: List<MarvelCharacter>) : UiState()
    object Error : UiState()
}