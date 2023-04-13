package com.example.bookshelf.ui.addbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentAddBookBinding
import com.example.bookshelf.databinding.FragmentAddBookSeniorBinding
import com.example.bookshelf.ui.bookinfo.BookInfoFragmentDirections
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBookSeniorFragment : Fragment() {
    private var _binding: FragmentAddBookSeniorBinding? = null
    private val binding get() = _binding!!

    private lateinit var titleET: EditText
    private lateinit var isReadCB: CheckBox

    private lateinit var dbReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddBookSeniorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveBookBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addBookFragment_to_BooksFragment)
        }

        dbReference = FirebaseDatabase.getInstance().getReference("Books")

        titleET = binding.bookTitleET
        isReadCB = binding.bookIsRead

        binding.saveBookBtn.setOnClickListener {
            if(titleET.text.isNotEmpty()){
                saveBooksData()
            }
            else{
                Toast.makeText(this.context,"Tytuł nie może być pusty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBooksData() {
        val title = titleET.text.toString()
        val isRead = isReadCB.isChecked

        val bookId = dbReference.push().key!!

        val book = BookModel(bookId, title, isRead)
        dbReference.child(bookId).setValue(book)
            .addOnCompleteListener {
                val action = AddBookSeniorFragmentDirections.actionAddBookSeniorFragmentToBooksFragmentSenior()
                findNavController().navigate(action)
            }.addOnFailureListener { error ->
                Toast.makeText(this.context, "${error.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}