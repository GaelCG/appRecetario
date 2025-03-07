package com.example.apprecetario2
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apprecetario.ui.theme.AppRecetarioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRecetarioTheme {
                RecipeApp()
            }
        }
    }
}

@Composable
fun RecipeApp() {
    var showDetails by remember { mutableStateOf(false) }
    var selectedRecipe by remember { mutableStateOf<Recipe?>(null) }

    if (showDetails && selectedRecipe != null) {
        DetailScreen(recipe = selectedRecipe!!) {
            showDetails = false
        }
    } else {
        RecipeList { recipe ->
            selectedRecipe = recipe
            showDetails = true
        }
    }
}

@Composable
fun RecipeList(onRecipeClick: (Recipe) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        recipes.forEach { recipe ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onRecipeClick(recipe) }
                    .padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column {
                    Image(
                        painter = painterResource(id = recipe.imageRes),
                        contentDescription = recipe.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = recipe.title,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = recipe.description,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(recipe: Recipe, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = recipe.imageRes),
            contentDescription = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = recipe.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )

        Text(
            text = recipe.fullDescription,
            fontSize = 16.sp,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onBack) {
            Text("Volver al menú principal")
        }
    }
}

data class Recipe(
    val title: String,
    val description: String,
    val fullDescription: String,
    val imageRes: Int
)

val recipes = listOf(
    Recipe(
        title = "Pasta Carbonara",
        description = "Clásica pasta italiana con huevo y panceta",
        fullDescription = "Ingredientes:\n- 400g spaghetti\n- 200g panceta\n- 3 huevos\n...\n\nPreparación:\n1. Cocinar la pasta...",
        imageRes = R.drawable.pasta_carbonara
    ),
    Recipe(
        title = "Ensalada César",
        description = "Ensalada fresca con pollo y aderezo especial",
        fullDescription = "Ingredientes:\n- Lechuga\n- Pollo\n- Queso parmesano\n...\n\nPreparación:\n1. Cortar los ingredientes...",
        imageRes = R.drawable.ensalada_cesar
    ),
    // Agregar más recetas
    Recipe(
        title = "Sopa de Tomate",
        description = "Sopa cremosa con tomates frescos",
        fullDescription = "Ingredientes:\n- Tomates frescos\n- Cebolla\n- Ajo\n...\n\nPreparación:\n1. Cocinar los tomates...",
        imageRes = R.drawable.sopa_tomate
    )
)
