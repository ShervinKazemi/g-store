package com.example.gabistore.di

import android.content.Context
import androidx.room.Room
import com.example.gabistore.model.db.AppDataBase
import com.example.gabistore.model.net.createApiService
import com.example.gabistore.model.repository.cart.CartRepository
import com.example.gabistore.model.repository.cart.CartRepositoryImpl
import com.example.gabistore.model.repository.comment.CommentRepository
import com.example.gabistore.model.repository.comment.CommentRepositoryImpl
import com.example.gabistore.model.repository.product.ProductRepository
import com.example.gabistore.model.repository.product.ProductRepositoryImpl
import com.example.gabistore.model.repository.user.UserRepository
import com.example.gabistore.model.repository.user.UserRepositoryImpl
import com.example.gabistore.ui.features.cart.CartViewModel
import com.example.gabistore.ui.features.category.CategoryViewModel
import com.example.gabistore.ui.features.main.MainViewModel
import com.example.gabistore.ui.features.product.ProductViewModel
import com.example.gabistore.ui.features.profile.ProfileViewModel
import com.example.gabistore.ui.features.signIn.SignInViewModel
import com.example.gabistore.ui.features.signUp.SignUpViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { androidContext().getSharedPreferences("data", Context.MODE_PRIVATE) }
    single { createApiService() }
    single { Room.databaseBuilder(androidContext(), AppDataBase::class.java, "app_dataBase.db").build() }

    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<ProductRepository> { ProductRepositoryImpl(get(), get<AppDataBase>().productDao()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
    single<CartRepository> { CartRepositoryImpl(get() , get()) }


    viewModel { ProductViewModel(get(), get(), get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { (isNetConnected: Boolean) -> MainViewModel(get(), get() , isNetConnected) }
    viewModel { CategoryViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { CartViewModel(get() , get())}
}