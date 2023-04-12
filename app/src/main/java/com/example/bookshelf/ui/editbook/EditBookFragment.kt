package com.example.bookshelf.ui.editbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookshelf.databinding.FragmentEditBookBinding
import com.example.bookshelf.ui.addbook.BookModel
import com.example.bookshelf.ui.bookinfo.BookInfoFragmentArgs
import com.example.bookshelf.ui.books.BooksFragmentDirections
import com.google.firebase.database.FirebaseDatabase

class EditBookFragment : Fragment() {

    private var _binding: FragmentEditBookBinding? = null
    private val binding get() = _binding!!

    private val args: EditBookFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.inputAuthorET.setText(args.bookAuthor)
        binding.inputTitleET.setText(args.bookTitle)
        binding.inputDescriptionET.setText(args.bookDescription)
        binding.isReadCB.isChecked = args.bookIsRead

        binding.saveBookBtn.setOnClickListener {
            val bookTitle = binding.inputTitleET.text.toString()
            val bookAuthor = binding.inputAuthorET.text.toString()
            val bookDescription = binding.inputDescriptionET.text.toString()
            val isRead = binding.isReadCB.isChecked
            updateBook(args.bookId, bookTitle, bookAuthor, bookDescription, isRead)

            val action = EditBookFragmentDirections.actionEditBookFragmentToBookInfoFragment(
                args.bookId, bookAuthor, bookTitle, bookDescription, isRead
            )
            it.findNavController().navigate(action)
        }
    }

    private fun updateBook(
        bookId: String,
        bookTitle: String,
        bookAuthor: String,
        bookDescription: String,
        isRead: Boolean
    ) {
        val dbReference = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val book = BookModel(bookId, bookAuthor, bookTitle, bookDescription, isRead)
        dbReference.setValue(book)
    }
}