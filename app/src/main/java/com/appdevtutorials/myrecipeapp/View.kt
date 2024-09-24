package com.appdevtutorials.myrecipeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun RecipeScreen(
    navigateToDetailScreen: (Category) -> Unit,
    recipeState: MainViewModel.RecipeState
){
    Box(modifier = Modifier.fillMaxSize()){
        when{
            recipeState.loading -> {
                Text(
                    text = "Loading",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            recipeState.error != null -> {
                Text(text = "Error Occurred : ${recipeState.error}")
            }
            else -> {
                CategoriesScreen(recipeState.listOfCategories, navigateToDetailScreen)
            }
        }
    }
}

@Composable
fun CategoriesScreen(
    listOfCategories: List<Category>,
    navigateToDetailScreen: (Category) -> Unit
){
    LazyVerticalGrid(GridCells.Fixed(2), modifier = Modifier.fillMaxSize()){
        items(listOfCategories){
            category ->
            DisplayCategory(category = category, navigateToDetailScreen)
        }
    }
}

@Composable
fun DisplayCategory(
    category: Category,
    navigateToDetailScreen : (Category) -> Unit
){
    Column (
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize().clickable { navigateToDetailScreen(category) },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )
        Text(
            text = category.strCategory,
            color = Color.Black,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun DetailScreen(category: Category){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = category.strCategory, textAlign = TextAlign.Center)
        Image(
            painter = rememberAsyncImagePainter(category.strCategoryThumb),
            contentDescription = "${category.strCategory} Thumbnail",
            modifier = Modifier
                .wrapContentSize()
                .aspectRatio(1f)
        )
        Text(
            text = category.strCategoryDescription,
            textAlign = TextAlign.Justify,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}