package com.bodakesatish.statehandler

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PeopleScreenRoot(
    viewModel: PeopleViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PeopleScreen(
        state = state,
        people = viewModel.people,
        onAction = viewModel::onAction
    )

}

@Composable
fun PeopleScreen(
    state: PeopleScreenState,
    people: List<PersonUi>,
    onAction: (PeopleAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = people,
                        key = { it.id }
                    ) { person ->
                        val progress by rememberUpdatedState(person.detailsLoadingProgress)
                        val personId = remember { person.id }
                        PersonItem(
                            name = person.name,
                            details = person.details,
                            progress = { progress },
                            isLoadingDetails = person.isLoadingDetails,
                            onLoadDetailsClick = {
                                onAction(PeopleAction.OnLoadDetailsClick(personId))
                            }
                        )
                    }
                }
            }
        }
    }
}