package com.example.testmobile.features.cars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.testmobile.databinding.ActivityCarBinding
import com.example.testmobile.interactor.entities.CategoryEntity
import org.koin.android.ext.android.inject

class CarActivity : AppCompatActivity() {
    val viewModel: CarViewModel by inject<CarViewModel>()
    private lateinit var binding: ActivityCarBinding
    var isUpdating = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadCategories()

        viewModel.categories.observe(this, {
            setCategories(it)
        })

        isUpdating = intent.getLongExtra("", -1)
        if (isUpdating > 0) {
            binding.buttonCar.text = "Create Car"
        } else {
            binding.buttonCar.text = "Update Car"
        }

        binding.buttonCar.setOnClickListener {
            processCar()
        }
    }

    private fun processCar() {
        TODO("Not yet implemented")
    }

    private fun setCategories(it: List<CategoryEntity>) {
        val adapterCategories = ArrayAdapter<CategoryEntity>(
            this,
            android.R.layout.simple_spinner_item,
            it.toList()
        )
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = adapterCategories
    }
}