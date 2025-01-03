package com.example.gabistore.model.repository.product

import com.example.gabistore.model.data.Ads
import com.example.gabistore.model.data.Product

interface ProductRepository {

    suspend fun getAllProducts(isInternetConnected: Boolean): List<Product>
    suspend fun getAllAds(isInternetConnected: Boolean): List<Ads>
    suspend fun getProductsByCategory(category: String): List<Product>
    suspend fun getProductById(productId: String) :Product

}