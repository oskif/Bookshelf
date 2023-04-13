package com.example.bookshelf.ui.bookinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentBookInfoBinding
import com.example.bookshelf.databinding.FragmentBookInfoSeniorBinding
import com.example.bookshelf.ui.addbook.BookModel
import com.example.bookshelf.ui.books.BooksFragmentDirections
import com.google.firebase.database.FirebaseDatabase

class BookInfoFragmentSenior : Fragment() {
    private var _binding: FragmentBookInfoSeniorBinding? = null
    private val binding get() = _binding!!

    private lateinit var titleET: EditText

    private val args: BookInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBookInfoSeniorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bookTitleET.setText(args.bookTitle)
        binding.bookIsRead.isChecked = args.bookIsRead

        titleET = binding.bookTitleET


        binding.deleteBookBtn.setOnClickListener {
            deleteBook(args.bookId)
        }

        binding.saveBookBtn.setOnClickListener{
            if(titleET.text.isNotEmpty()){
                editBook()
            }
            else{
                Toast.makeText(this.context,"Tytuł nie może być pusty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editBook() {
        val bookTitle = binding.bookTitleET.text.toString()
        val bookIsRead = binding.bookIsRead.isChecked
        updateBook(args.bookId, bookTitle, bookIsRead)

        val action = BookInfoFragmentSeniorDirections.actionBookInfoFragmentSeniorToBooksFragmentSenior()
        findNavController().navigate(action)
    }

    private fun deleteBook(bookId: String) {
        val dbReference = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val deleteTask = dbReference.removeValue()

        deleteTask.addOnSuccessListener {
            Toast.makeText(this.context, "Książka została usunięta", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_bookInfoFragmentSenior_to_booksFragmentSenior)
        }.addOnFailureListener { error ->
            Toast.makeText(this.context, "${error.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateBook(
        bookId: String,
        bookTitle: String,
        isRead: Boolean
    ) {
        val dbReference = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val book = BookModel(bookId, bookTitle, isRead)
        dbReference.setValue(book)
    }
}