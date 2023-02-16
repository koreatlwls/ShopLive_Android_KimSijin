package com.example.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.usecase.favorite.DeleteFavoriteWithIdUseCase
import com.example.domain.usecase.favorite.GetAllFavoritesUseCase
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
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val deleteFavoriteWithIdUseCase: DeleteFavoriteWithIdUseCase,
    getAllFavoritesUseCase: GetAllFavoritesUseCase
) : ViewModel() {

    var currentQuery = ""

    private val _uiState = MutableStateFlow(UiState.Empty)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val favoriteItems: StateFlow<List<MarvelCharacter>> = getAllFavoritesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    private val _marvelCharacterList = MutableStateFlow(emptyList<CommonItem>())
    val marvelCharacterList = favoriteItems.combine(_marvelCharacterList) { favorites, marvelCharacters ->
        val updateList = marvelCharacters.map { commonItem ->
            if (commonItem.viewObject is ViewObject.SuccessViewObject) {
                val falseItem = commonItem.viewObject.marvelCharacter.copy(isFavorite = false)
                val trueItem = falseItem.copy(isFavorite = true)

                if (trueItem in favorites) {
                    CommonItem(
                        UiState.Success,
                        ViewObject.SuccessViewObject(trueItem)
                    )
                } else {
                    CommonItem(
                        UiState.Success,
                        ViewObject.SuccessViewObject(falseItem)
                    )
                }
            } else {
                commonItem
            }
        }

        updateList
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun getData(query: String) {
        currentQuery = query

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            if (query.length < 2) {
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

    fun insertFavorite(marvelCharacter: MarvelCharacter) {
        viewModelScope.launch {
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

