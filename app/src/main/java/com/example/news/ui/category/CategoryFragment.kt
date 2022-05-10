package com.example.news.ui.category

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.MainActivity
import com.example.news.R
import com.example.news.ui.HomeActivity

class CategoryFragment : Fragment() {


    val listOfCategory = listOf(
        Catigory_Item(
            "business", R.drawable.bussines,
            R.string.business, R.color.blue
        ),
        Catigory_Item(
            "entertainment", R.drawable.environment,
            R.string.environment, R.color.red
        ),
        Catigory_Item(
            "technology", R.drawable.politics,
            R.string.Politics, R.color.brown
        ),
        Catigory_Item(
            "health", R.drawable.health,
            R.string.health, R.color.orange
        ),
        Catigory_Item(
            "science", R.drawable.science,
            R.string.science, R.color.yellow
        ),
        Catigory_Item(
            "sports", R.drawable.ball,
            R.string.sports, R.color.pink
        )
    )

    val adapter = CategoriesAdapter(listOfCategory)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)

    }

    lateinit var textView: TextView
    lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        adapter.onitemclickedListener = object : CategoriesAdapter.OnitemclickedListener {
            override fun onItemClicked(pos: Int, Category: Catigory_Item) {
onCatigoryClickListener?.onCategoryClicked(Category)
            }
        }

        textView = requireActivity().findViewById(R.id.testtext)
        textView.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)


        }
    }

    var onCatigoryClickListener: OnCatigoryClickListener? = null

    interface OnCatigoryClickListener {
        fun onCategoryClicked(category: Catigory_Item)
    }

}