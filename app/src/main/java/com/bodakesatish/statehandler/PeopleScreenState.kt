package com.bodakesatish.statehandler

import androidx.compose.runtime.Stable

@Stable
data class PeopleScreenState(
    val isLoading: Boolean = false
)

data class PersonUi(
    val id: Int,
    val name: String,
    val isLoadingDetails: Boolean,
    val detailsLoadingProgress: Float,
    val details: PersonDetails?
)

data class PersonDetails(
    val phoneNumber: String,
    val bio: String
)