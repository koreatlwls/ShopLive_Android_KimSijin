package com.example.presentation.model

import com.example.domain.model.MarvelCharacter

sealed class ViewObject {

    object LoadingViewObject : ViewObject()

    object EmptyViewObject : ViewObject()

    data class SuccessViewObject(
        val marvelCharacter: MarvelCharacter
    ) : ViewObject()

    object ErrorViewObject : ViewObject()

}
