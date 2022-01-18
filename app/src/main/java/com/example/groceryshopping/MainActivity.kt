package com.example.groceryshopping
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: GroceryViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val groceryRepository = GroceryRepository(GroceryDatabase(this))

        val factory =
            GroceryViewModelFactory(groceryRepository)
        viewModel = ViewModelProvider(this,factory)[GroceryViewModel::class.java]
        val groceryAdapter =
            GroceryAdapter(listOf(), viewModel)

        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = groceryAdapter

        viewModel.allGroceryItems().observe(this, {
            groceryAdapter.list = it
            groceryAdapter.notifyDataSetChanged()

        })
        btnAdd.setOnClickListener {
            GroceryItemDialog(this,object : DialogListener{
                override fun onAddButtonClicked(item: GroceryItems) {
                    viewModel.insert(item)
                }
            }).show()
        }
    }
}
