package com.example.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.usecase.favorite.InsertFavoriteUseCase
import com.example.domain.usecase.search.GetMarvelCharacterUseCase
import com.example.presentation.model.CommonItem
import com.example.presentation.model.UiState
import com.example.presentation.model.ViewObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMarvelCharacterUseCase: GetMarvelCharacterUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase
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

                        val commonItems = marvelCharacters.map { marvelCharacter ->
                            CommonItem(
                                viewType = UiState.Success,
                                viewObject = ViewObject.SuccessViewObject(marvelCharacter)
                            )
                        }
                        _marvelCharacterList.value = commonItems
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

    private val _marvelCharacterList = MutableStateFlow(emptyList<CommonItem>())
    val marvelCharacterList = _marvelCharacterList.asStateFlow()

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }

    fun getMoreData(offset: Int) {
        val moreDataList = _marvelCharacterList.value.filter { it.viewType == UiState.Success }

        viewModelScope.launch {
            addState(
                moreDataList.toMutableList(),
                CommonItem(
                    UiState.Loading,
                    ViewObject.LoadingViewObject
                )
            )

            getMarvelCharacterUseCase(
                _searchQuery.value,
                offset
            ).onSuccess { marvelCharacters ->
                if (marvelCharacters.isEmpty()) {
                    addState(
                        moreDataList.toMutableList(),
                        CommonItem(
                            UiState.Empty,
                            ViewObject.EmptyViewObject
                        )
                    )
                } else {
                    val commonItems = marvelCharacters.map { marvelCharacter ->
                        CommonItem(
                            viewType = UiState.Success,
                            viewObject = ViewObject.SuccessViewObject(marvelCharacter)
                        )
                    }

                    val successList = moreDataList.toMutableList()
                    successList.addAll(commonItems)
                    _marvelCharacterList.value = successList
                }
            }.onFailure {
                addState(
                    moreDataList.toMutableList(),
                    CommonItem(
                        UiState.Error,
                        ViewObject.ErrorViewObject
                    )
                )
            }
        }
    }

    private fun addState(list: MutableList<CommonItem>, commonItem: CommonItem) {
        list.add(commonItem)
        _marvelCharacterList.value = list
    }

    fun insertFavorite(marvelCharacter: MarvelCharacter) {
        viewModelScope.launch {
            insertFavoriteUseCase(marvelCharacter)
        }
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
    }

}

