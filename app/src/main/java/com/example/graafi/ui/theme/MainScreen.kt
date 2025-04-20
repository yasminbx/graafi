package com.example.graafi.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(viewModel: HeartRateViewModel, onNavigateToGraph: () -> Unit) {
    val measurements by viewModel.heartRateList.observeAsState(emptyList())

    LazyColumn {
        itemsIndexed(measurements) { index, value ->
            Text(
                text = "Measurement $index: $value",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToGraph() }
                    .padding(16.dp)
            )
        }
    }
}
