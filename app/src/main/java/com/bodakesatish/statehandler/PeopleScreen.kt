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
        onAction = viewModel::onAction
    )

}

@Composable
fun PeopleScreen(state: PeopleScreenState,
                 onAction: (PeopleAction) -> Unit) {
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
                        items = state.people,
                        key = { it.id }
                    ) { person ->
                        PersonItem(
                            personUi = person,
                            onLoadDetailsClick = {
                               onAction(PeopleAction.OnLoadDetailsClick(person.id))
                            }
                        )
                    }
                }
            }
        }
    }
}