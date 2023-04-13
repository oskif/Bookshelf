package com.example.bookshelf.ui.books

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bookshelf.R
import com.example.bookshelf.databinding.CardBookBinding
import com.example.bookshelf.databinding.CardBookSeniorBinding
import com.example.bookshelf.ui.addbook.BookModel
import com.example.bookshelf.ui.bookinfo.BookInfoFragment

class BooksAdapterSenior(
    private val books: List<BookModel>
) : RecyclerView.Adapter<BooksAdapterSenior.BooksViewHolder>() {
    inner class BooksViewHolder(binding: CardBookSeniorBinding) : ViewHolder(binding.root) {
        val titleTV = binding.titleTV
        val isRead = binding.isReadCB
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val cardBookBinding = CardBookSeniorBinding.inflate(inflater, parent, false)
        return BooksViewHolder(cardBookBinding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.titleTV.text = books[position].title
        holder.isRead.isChecked = books[position].isRead!!
        if(holder.isRead.isChecked){
            holder.isRead.isVisible = true
        }

        holder.itemView.setOnClickListener {
            val action = BooksFragmentSeniorDirections.actionBooksFragmentSeniorToBookInfoFragmentSenior(
                books[position].id!!,
                books[position].title!!,
                books[position].isRead!!,
            )
            it.findNavController().navigate(action)
        }

    }



    override fun getItemCount(): Int {
        return books.size
    }
}