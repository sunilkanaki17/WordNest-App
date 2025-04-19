package com.appdevelopment.dictionary.features_dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdevelopment.dictionary.features_dictionary.domain.use_case.GetWordInfo
import com.plcoding.dictionary.core.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


//Let’s say user searches for "apple":
//
//onSearch("apple") is called
//
//_searchQuery becomes "apple"
//
//We cancel previous search
//
//We wait 500ms
//
//We call getWordInfo("apple") → emits:
//
//Loading → Show spinner
//
//Success → Show meanings like 🍎 or tech company
//
//or Error → Show snackbar: "No internet"



//main logic class whenever user types something
@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    //Keeps track of what user is typing,
    //User types apple, we store "apple" here.
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    //Holds word list and loading info
    //Are we loading?
    //What word definitions are we showing?
    private val _state = mutableStateOf(WordInfoState())
    val state: State<WordInfoState> = _state

    //Used to show Snackbar (error messages)
    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch(){
            delay(500L)
            getWordInfo(query)
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        is Resource.Error -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            )
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }
}