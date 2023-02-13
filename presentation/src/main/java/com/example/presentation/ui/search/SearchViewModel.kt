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
                        emit(UiState.Success)
                        _marvelCharacterList.value = marvelCharacters
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

    private val _moreDataUiState = MutableStateFlow(UiState.Empty)
    val moreDataUiState = _moreDataUiState.asStateFlow()

    private val _marvelCharacterList = MutableStateFlow(emptyList<MarvelCharacter>())
    val marvelCharacterList = _marvelCharacterList.asStateFlow()

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

    fun getMoreData(offset: Int) {
        viewModelScope.launch {
            _moreDataUiState.value = UiState.Loading

            getMarvelCharacterUseCase(
                _searchQuery.value,
                offset
            ).onSuccess { marvelCharacters ->
                if (marvelCharacters.isEmpty()) {
                    _moreDataUiState.value = UiState.Empty
                } else {
                    _moreDataUiState.value = UiState.Success

                    val moreDataList = _marvelCharacterList.value.toMutableList()
                    moreDataList.addAll(marvelCharacters)
                    _marvelCharacterList.value = moreDataList
                }
            }.onFailure {
                _moreDataUiState.value = UiState.Error
            }
        }
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
    }

}

enum class UiState {
    Loading, Empty, Success, Error
}