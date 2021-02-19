package com.example.testmobile.features.categories

import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmobile.R
import com.example.testmobile.core.Failure
import com.example.testmobile.databinding.ActivityCategoryBinding
import com.example.testmobile.features.cars.CarsAdapter
import com.example.testmobile.interactor.entities.CategoryEntity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject

class CategoryActivity : AppCompatActivity() {
    val viewModel: ViewModelCategory by inject<ViewModelCategory>()
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.failure.observe(this, {
            handlerFailure(it)
        })

        viewModel.categories.observe(this, {
            renderCategories(it)
        })

        viewModel.wasProcessed.observe(this, {
            viewModel.loadCategories()
        })

        categoryAdapter = CategoryAdapter()
        binding.recyclerCategory.layoutManager = LinearLayoutManager(this)
        binding.recyclerCategory.adapter = categoryAdapter

        categoryAdapter.clickDeleteListener = {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete))
                .setMessage(getString(R.string.delete_question_category))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, whichButton ->
                        viewModel.deleteCategory(it.categoryId)
                    })
                .setNegativeButton(R.string.no, null).show()
        }

        categoryAdapter.clickListener = {

        }

        binding.floatingActionButtonCategory.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val inflater = layoutInflater
            builder.setTitle(getString(R.string.add_category))
            val dialogLayout = inflater.inflate(R.layout.add_category_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.editText)
            builder.setView(dialogLayout)
            builder.setPositiveButton("OK") { dialogInterface, i ->
                if (!editText.text.isNullOrEmpty())
                    viewModel.insertCategory(CategoryEntity(editText.text.toString()))
            }
            builder.show()
        }
    }

    private fun renderCategories(categories: List<CategoryEntity>?) {
        categoryAdapter.collection = categories.orEmpty()
    }

    private fun handlerFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(getString(R.string.failure_network_connection))
            is Failure.DataBaseError -> renderFailure(getString(R.string.data_base_error))
            is Failure.ServerError -> renderFailure(getString(R.string.failure_server_error))
            else -> renderFailure(getString(R.string.failure_server_error))
        }
    }

    private fun renderFailure(message: String) {
        Snackbar.make(this, binding.recyclerCategory, message, Snackbar.LENGTH_LONG)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadCategories()
    }

}