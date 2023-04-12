package com.example.bookshelf.ui.addbook

data class BookModel (
    var id:String? = null,
    var author:String? = null,
    var title:String? = null,
    var description:String? = null,
    var isRead:Boolean? = null
)