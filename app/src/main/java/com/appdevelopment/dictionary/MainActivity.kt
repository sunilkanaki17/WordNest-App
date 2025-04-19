package com.appdevelopment.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appdevelopment.dictionary.features_dictionary.presentation.WordInfoItem
import com.appdevelopment.dictionary.features_dictionary.presentation.WordInfoViewModel
import com.appdevelopment.dictionary.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryTheme {
                val viewModel: WordInfoViewModel = hiltViewModel()
                val state = viewModel.state.value
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(key1 = true) {
                    viewModel.eventFlow.collectLatest { event ->
                        when (event) {
                            is WordInfoViewModel.UIEvent.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(message = event.message)
                            }
                        }
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFFFBEF),
                                        Color(0xFFE3F2FD),
                                        Color(0xFFF1F1F1)
                                    )
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "📖 Dictionary",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A237E),
                                modifier = Modifier.padding(bottom = 12.dp,top=25.dp)
                            )

                            TextField(
                                value = viewModel.searchQuery.value,
                                onValueChange = viewModel::onSearch,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(8.dp, shape = MaterialTheme.shapes.extraLarge)
                                    .background(
                                        brush = Brush.horizontalGradient(
                                            colors = listOf(Color(0xFFB3E5FC), Color(0xFF81D4FA))
                                        ),
                                        shape = MaterialTheme.shapes.extraLarge
                                    )
                                    .clip(MaterialTheme.shapes.extraLarge)
                                    .border(1.dp, Color(0xFF90CAF9), shape = MaterialTheme.shapes.extraLarge)
                                    .padding(horizontal = 4.dp),
                                placeholder = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = "🔍 Search a word...",
                                            color = Color(0xFF424242),
                                            fontSize = 16.sp
                                        )
                                    }
                                },
                                trailingIcon = {
                                    if (viewModel.searchQuery.value.isNotEmpty()) {
                                        IconButton(onClick = { viewModel.onSearch("") }) {
                                            Icon(
                                                imageVector = Icons.Default.Close,
                                                contentDescription = "Clear",
                                                tint = Color.DarkGray
                                            )
                                        }
                                    }
                                },
                                shape = MaterialTheme.shapes.extraLarge,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(state.wordInfoItems.size) { i ->
                                    val wordInfo = state.wordInfoItems[i]
                                    if (i > 0) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }
                                    Card(
                                        shape = MaterialTheme.shapes.medium,
                                        elevation = CardDefaults.cardElevation(8.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                            .background(
                                                brush = Brush.linearGradient(
                                                    colors = listOf(
                                                        Color(0xFFE3F2FD), // Lighter Blue (complementary to search bar)
                                                        Color(0xFFBBDEFB)  // Soft Blue (complementary to search bar)
                                                    ),
                                                    start = Offset(0f, 0f),
                                                    end = Offset(1f, 1f)
                                                ),
                                                shape = MaterialTheme.shapes.medium
                                            )
                                            .shadow(6.dp, shape = MaterialTheme.shapes.medium, clip = false)
                                    ) {
                                        WordInfoItem(wordInfo = wordInfo)
                                    }
                                    if (i < state.wordInfoItems.size - 1) {
                                        Divider(
                                            modifier = Modifier.padding(vertical = 4.dp),
                                            thickness = 0.5.dp,
                                            color = Color.LightGray
                                        )
                                    }
                                }
                            }
                        }

                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
            }
        }
    }
}
