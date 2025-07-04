package com.bodakesatish.statehandler

import androidx.core.app.Person
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PeopleViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(PeopleScreenState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadPeople()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PeopleScreenState()
        )

    fun onAction(action: PeopleAction) {
        when (action) {
            is PeopleAction.OnLoadDetailsClick -> {
                loadsDetails(action.personId)
            }
        }
    }

    private fun loadsDetails(personId: Int) {

        _state.update {
            it.copy(
                people = it.people.map { person ->
                    if (person.id == personId) {
                        person.copy(
                            detailsLoadingProgress = 0f,
                            isLoadingDetails = true
                        )
                    } else {
                        person
                    }
                }
            )
        }

        val progressFlow = flow<Float> {
            var currentProgress = 0f
            while (currentProgress <= 1f) {
                emit(currentProgress)

                currentProgress += 0.01f
                delay(10L)
            }
        }

        progressFlow
            .onEach { progress ->
                _state.update {
                    it.copy(
                        people = it.people.map { person ->
                            if (person.id == personId) {
                                person.copy(
                                    detailsLoadingProgress = progress
                                )
                            } else {
                                person
                            }
                        }
                    )

                }
            }.onCompletion {
                _state.update { it.copy(
                    people = it.people.map { person ->
                        if (person.id == personId) {
                            person.copy(
                                details = PersonDetails(
                                    phoneNumber = "+1 234 544 434",
                                    bio = "This is a sample bio"
                                ),
                                isLoadingDetails = false
                            )
                        } else {
                            person
                        }
                    }
                ) }
            }.launchIn(viewModelScope)

    }

    private fun loadPeople() {
        viewModelScope.launch {
            _state.update {
                _state.value.copy(
                    isLoading = true
                )
            }
            delay(3000L)
            _state.update {
                it.copy(
                    isLoading = false,
                    people = dummyPeople
                )
            }
        }
    }

    // --- Dummy Data ---
    val dummyPeople = listOf(
        PersonUi(
            id = 1,
            name = "Alice Wonderland",
            isLoadingDetails = false,
            detailsLoadingProgress = 0f,
            details = null
        ),
        PersonUi(
            id = 2,
            name = "Bob The Builder",
            isLoadingDetails = false,
            detailsLoadingProgress = 0f,
            details = null
        ),
        PersonUi(
            id = 3,
            name = "Carol Danvers",
            isLoadingDetails = false,
            detailsLoadingProgress = 0f,
            details = null
        ),
        PersonUi(
            id = 4,
            name = "David Copperfield",
            isLoadingDetails = false,
            detailsLoadingProgress = 0f,
            details = null
        ),
        PersonUi(
            id = 5,
            name = "Eve Polastri",
            isLoadingDetails = false,
            detailsLoadingProgress = 0f,
            details = null
        ),
        PersonUi(
            id = 6,
            name = "Frankenstein's Monster",
            isLoadingDetails = false, // Loading was attempted but perhaps failed, or simply not initiated
            detailsLoadingProgress = 0f,
            details = PersonDetails(phoneNumber = "855-0101", bio = "Purious adventurer")
        )
    )
}

