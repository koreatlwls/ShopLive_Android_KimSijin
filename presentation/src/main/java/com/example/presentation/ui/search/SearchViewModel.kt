package com.example.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.usecase.favorite.DeleteFavoriteWithIdUseCase
import com.example.domain.usecase.favorite.InsertFavoriteUseCase
import com.example.domain.usecase.search.GetMarvelCharacterUseCase
import com.example.presentation.model.CommonItem
import com.example.presentation.model.UiState
import com.example.presentation.model.ViewObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getMarvelCharacterUseCase: GetMarvelCharacterUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteWithIdUseCase: DeleteFavoriteWithIdUseCase,
) : ViewModel() {

    private var currentQuery = ""

    private val _uiState = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _marvelCharacterList = MutableStateFlow(emptyList<CommonItem>())
    val marvelCharacterList = _marvelCharacterList.asStateFlow()

    fun getData(query: String) {
        currentQuery = query

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            if (query.isBlank()) {
                _uiState.value = UiState.Empty
            } else {
                getMarvelCharacterUseCase(
                    query,
                    DEFAULT_OFFSET
                ).onSuccess { marvelCharacters ->
                    if (marvelCharacters.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success

                        val commonItems = marvelCharacters.map { marvelCharacter ->
                            CommonItem(
                                viewType = UiState.Success,
                                viewObject = ViewObject.SuccessViewObject(marvelCharacter)
                            )
                        }
                        _marvelCharacterList.value = commonItems
                    }
                }.onFailure {
                    _uiState.value = UiState.Error
                }
            }
        }
    }

    fun retryGetData() {
        viewModelScope.launch {
            getData(currentQuery)
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
                currentQuery,
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

    fun insertFavorite(marvelCharacter: MarvelCharacter, position: Int) {
        viewModelScope.launch {
            val updateList = _marvelCharacterList.value.toMutableList().apply {
                val commonItem = this[position]

                if (commonItem.viewObject is ViewObject.SuccessViewObject) {
                    this[position] = CommonItem(
                        UiState.Success,
                        ViewObject.SuccessViewObject(
                            commonItem.viewObject.marvelCharacter.copy(
                                isFavorite = marvelCharacter.isFavorite.not()
                            )
                        )
                    )
                }
            }
            _marvelCharacterList.value = updateList

            if (marvelCharacter.isFavorite) {
                deleteFavoriteWithIdUseCase(marvelCharacter.id)
            } else {
                insertFavoriteUseCase(marvelCharacter)
            }
        }
    }

    companion object {
        private const val DEFAULT_OFFSET = 0
    }

}

