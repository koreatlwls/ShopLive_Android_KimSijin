package com.example.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.MarvelCharacter
import com.example.domain.usecase.favorite.GetAllFavoritesUseCase
import com.example.presentation.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase
) : ViewModel() {

    val favoriteItems: StateFlow<List<MarvelCharacter>> = getAllFavoritesUseCase().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    val uiState : StateFlow<UiState> = favoriteItems.flatMapLatest { items ->
        flow{
           emit(UiState.Loading)

           if(items.isEmpty()){
               emit(UiState.Empty)
           }else{
               emit(UiState.Success)
           }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState.Empty
    )

}