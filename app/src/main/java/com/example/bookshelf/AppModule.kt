package com.example.bookshelf

import com.example.bookshelf.ui.books.BooksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        BooksViewModel()
    }
}