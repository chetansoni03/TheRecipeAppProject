package com.appdevtutorials.myrecipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _currentRecipeState = mutableStateOf(RecipeState())
    var currentRecipeState : State<RecipeState> = _currentRecipeState

    init {
        fetchCategories()
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            try {
                val response = RecipeApiService.getCategories()
                _currentRecipeState.value = _currentRecipeState.value.copy(
                    loading = false,
                    error = null,
                    listOfCategories = response.categories
                )
            }
            catch (e : Exception){
                _currentRecipeState.value = _currentRecipeState.value.copy(
                    loading = false,
                    error = "Error loading categories, ${e.message}"
                )
            }
        }
    }

    data class RecipeState(
        val loading : Boolean = true,
        val error : String? = null,
        val listOfCategories : List<Category> = emptyList()
    )
}