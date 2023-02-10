package com.example.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.usecase.search.GetMarvelCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMarvelCharacterUseCase: GetMarvelCharacterUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    val uiState: StateFlow<UiState> = _searchQuery.flatMapLatest { query ->
        flow {
            emit(UiState.Loading)

            if (query.isBlank()) {
                emit(UiState.Empty)
            } else {
                getMarvelCharacterUseCase(
                    query,
                    DEFAULT_OFFSET
                ).onSuccess { marvelCharacters ->
                    if (marvelCharacters.isEmpty()) {
                        emit(UiState.Empty)
                    } else {
                        emit(UiState.Success(marvelCharacters))
                    }
                }.onFailure {
                    emit(UiState.Error)
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000),
        initialValue = UiState.Empty
    )

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
    }

}

sealed class UiState {
    object Loading : UiState()
    object Empty : UiState()
    data class Success(val list: List<MarvelCharacter>) : UiState()
    object Error : UiState()
}