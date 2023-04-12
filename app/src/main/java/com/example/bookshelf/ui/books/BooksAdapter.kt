package com.example.bookshelf.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bookshelf.R
import com.example.bookshelf.ui.books.data.Book
import com.example.bookshelf.databinding.CardBookBinding
import com.example.bookshelf.ui.addbook.BookModel
import com.example.bookshelf.ui.bookinfo.BookInfoFragment

class BooksAdapter(
    private val books: List<BookModel>
) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {
    inner class BooksViewHolder(binding: CardBookBinding) : ViewHolder(binding.root) {
        val titleTV = binding.titleTV
        val authorTV = binding.authorTV
        val isRead = binding.isReadCB
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cardBookBinding = CardBookBinding.inflate(inflater, parent, false)
        return BooksViewHolder(cardBookBinding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.titleTV.text = books[position].title
        holder.authorTV.text = books[position].author
        holder.isRead.isChecked = books[position].isRead!!

        holder.itemView.setOnClickListener {
            val action = BooksFragmentDirections.actionBooksFragmentToBookInfoFragment(
                books[position].id!!,
                books[position].author!!,
                books[position].title!!,
                books[position].description!!,
                books[position].isRead!!,
            )
            it.findNavController().navigate(action)
        }

    }



    override fun getItemCount(): Int {
        return books.size
    }
}