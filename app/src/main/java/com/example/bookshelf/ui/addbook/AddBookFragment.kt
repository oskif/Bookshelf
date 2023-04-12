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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddBookFragment : Fragment() {
    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!

    private lateinit var authorET: EditText
    private lateinit var titleET: EditText
    private lateinit var descriptionET: EditText
    private lateinit var isReadCB: CheckBox

    private lateinit var dbReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.saveBookBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addBookFragment_to_BooksFragment)
        }

        dbReference = FirebaseDatabase.getInstance().getReference("Books")

        authorET = binding.inputAuthorET
        titleET = binding.inputTitleET
        descriptionET = binding.inputDescriptionET
        isReadCB = binding.isReadCB

        binding.saveBookBtn.setOnClickListener {
            saveBooksData()
        }
    }

    private fun saveBooksData() {
        val author = authorET.text.toString()
        val title = titleET.text.toString()
        val description = descriptionET.text.toString()
        val isRead = isReadCB.isChecked

        val bookId = dbReference.push().key!!

        val book = BookModel(bookId, author, title, description, isRead)
        dbReference.child(bookId).setValue(book)
            .addOnCompleteListener {
                Toast.makeText(this.context, "Nice", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { error ->
                Toast.makeText(this.context, "${error.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}