package com.example.bookshelf.ui.bookinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentBookInfoBinding
import com.example.bookshelf.ui.books.BooksFragmentDirections
import com.google.firebase.database.FirebaseDatabase

class BookInfoFragment : Fragment() {
    private var _binding: FragmentBookInfoBinding? = null
    private val binding get() = _binding!!

    private val args: BookInfoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBookInfoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.TitleTV.text = args.bookTitle
        binding.authorTV.text = args.bookAuthor
        binding.descriptionTV.text = args.bookDescription

        binding.deleteBookBtn.setOnClickListener {
            deleteBook(args.bookId)
        }

        binding.editBookBtn.setOnClickListener{
            editBook()
        }
    }

    private fun editBook() {
        val action = BookInfoFragmentDirections.actionBookInfoFragmentToEditBookFragment(
            args.bookId,
            args.bookAuthor,
            args.bookTitle,
            args.bookDescription,
            args.bookIsRead
        )
        findNavController().navigate(action)
    }

    private fun deleteBook(bookId: String) {
        val dbReference = FirebaseDatabase.getInstance().getReference("Books").child(bookId)
        val deleteTask = dbReference.removeValue()

        deleteTask.addOnSuccessListener {
            Toast.makeText(this.context, "Książka została usunięta", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_bookInfoFragment_to_BooksFragment)
        }.addOnFailureListener { error ->
            Toast.makeText(this.context, "${error.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}