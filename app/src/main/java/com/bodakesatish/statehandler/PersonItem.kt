package com.bodakesatish.statehandler

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PersonItem(
    name: String,
    details: PersonDetails?,
    isLoadingDetails: Boolean,
    progress: () -> Float,
    onLoadDetailsClick: () -> Unit,
    modifier: Modifier  = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Row {
            Text(
                text = name,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            details != null -> {
                Text(
                    text = details.bio,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = details.phoneNumber,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right,
                    fontSize = 12.sp
                )
            }
            isLoadingDetails -> {
                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            else -> {
                TextButton(
                    onClick = onLoadDetailsClick,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Load Details")
                }
            }

        }

    }
}