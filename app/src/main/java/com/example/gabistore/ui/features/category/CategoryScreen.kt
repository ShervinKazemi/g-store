package com.example.gabistore.ui.features.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gabistore.model.data.Product
import com.example.gabistore.ui.theme.Blue
import com.example.gabistore.util.MyScreens
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

@Composable
fun CategoryScreen(categoryName: String) {
    val viewModel = getNavViewModel<CategoryViewModel>()
    viewModel.loadDataByCategory(categoryName)

    val navigation = getNavController()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        CategoryToolBar(categoryName)

        val data = viewModel.dataProduct
        CategoryList(data.value) {
            navigation.navigate(MyScreens.ProductScreen.route + "/" + it)
        }

    }

}

@Composable
fun CategoryItem(data: Product, onProductClicked: (String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clickable { onProductClicked.invoke(data.productId) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = ShapeDefaults.Large,
        colors = CardDefaults.cardColors(Color.White)
    ) {

        Column {

            AsyncImage(
                model = data.imgUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.padding(10.dp)
                ) {

                    Text(
                        text = data.name,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Text(
                        modifier = Modifier.padding(top = 4.dp),
                        text = data.price,
                        style = TextStyle(
                            fontSize = 14.sp,
                        )
                    )

                }

                Surface(
                    modifier = Modifier
                        .padding(bottom = 8.dp, end = 8.dp)
                        .align(Alignment.Bottom)
                        .clip(ShapeDefaults.Large),
                    color = Blue
                ) {

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = data.soldItem + " Sold",
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    )

                }

            }

        }

    }

}

@Composable
fun CategoryList(value: List<Product>, onProductClicked: (String) -> Unit) {

    val context = LocalContext.current
    val navigation = getNavController()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        items(value.size) {

            CategoryItem(data = value[it], onProductClicked)

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryToolBar(categoryName: String) {

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(Color.White),
        modifier = Modifier.fillMaxWidth(),
        title = { Text(
            modifier = Modifier.fillMaxWidth(),
            text = categoryName,
            textAlign = TextAlign.Center
        ) }
    )
}
