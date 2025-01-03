package com.example.gabistore.ui.features.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gabistore.model.data.Product
import com.example.gabistore.model.repository.product.ProductRepository
import com.example.gabistore.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val productRepository: ProductRepository
) :ViewModel() {

    val dataProduct = mutableStateOf<List<Product>>(listOf())

    fun loadDataByCategory(category: String) {

        viewModelScope.launch(coroutineExceptionHandler) {

            val dataFromLocal = productRepository.getProductsByCategory(category)
            dataProduct.value = dataFromLocal

        }

    }

}