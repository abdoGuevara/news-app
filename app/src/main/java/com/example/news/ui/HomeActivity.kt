package com.example.news.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView

import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.news.R
import com.example.news.ui.News.NewsFragment
import com.example.news.ui.category.CategoryFragment
import com.example.news.ui.category.Catigory_Item

class HomeActivity : AppCompatActivity() {

    lateinit var category: View
    lateinit var setting: View
    lateinit var imageViewDrawerButton: ImageView
    lateinit var drawerLayout: DrawerLayout
    val categoryFragment = CategoryFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        pushFragment(categoryFragment)
        categoryFragment.onCatigoryClickListener =
            object : CategoryFragment.OnCatigoryClickListener {
                override fun onCategoryClicked(category: Catigory_Item) {
                    pushFragment(NewsFragment.getInstance(category),true)
                }
            }
    }

    private fun initView() {
        setting = findViewById(R.id.setting)
        category = findViewById(R.id.catigory)
        drawerLayout = findViewById(R.id.drawer_layout)
        imageViewDrawerButton = findViewById(R.id.menuButton)



        imageViewDrawerButton.setOnClickListener {
            drawerLayout.open()
        }

        category.setOnClickListener {
            pushFragment(categoryFragment)
            drawerLayout.close()
        }
        setting.setOnClickListener {
            pushFragment(SettingFragment())
            drawerLayout.close()
        }
    }

    fun pushFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val fragmentTransaction =
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
        if (addToBackStack)
            fragmentTransaction.addToBackStack("")
        fragmentTransaction.commit()

    }
}



