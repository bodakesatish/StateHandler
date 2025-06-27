package com.bodakesatish.statehandler

sealed interface PeopleAction {
    data class OnLoadDetailsClick(val personId: Int):PeopleAction
}