package com.example.bookshelf.ui.books

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookshelf.R
import com.example.bookshelf.databinding.FragmentBooksBinding
import com.example.bookshelf.ui.addbook.BookModel
import com.google.firebase.database.*

class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    private lateinit var books: ArrayList<BookModel>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BooksAdapter

    private lateinit var dbReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addBookBtn.setOnClickListener {
            findNavController().navigate(R.id.action_BooksFragment_to_addBookFragment)
        }
        books = arrayListOf()
        initData()
        recyclerView = binding.recyclerViewBooks
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initData() {
        dbReference = FirebaseDatabase.getInstance().getReference("Books")

        val valueListener = object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (bookSnapshot in snapshot.children){
                        val bookData = bookSnapshot.getValue(BookModel::class.java)
                        books.add(bookData!!)
                    }
                    adapter = BooksAdapter(books.sortedBy { it.author })
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        dbReference.addValueEventListener(valueListener)
    }
}
