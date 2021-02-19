package com.example.testmobile.features.cars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.children
import com.example.testmobile.core.categoryNotEditable
import com.example.testmobile.databinding.ActivityCarBinding
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.entities.PropertyEntity
import org.koin.android.ext.android.inject

class CarActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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

        viewModel.wasProcessed.observe(this, {
            if (it) finish()
        })

        viewModel.failure.observe(this, {
            var message = "fallo"
        })

        isUpdating = intent.getLongExtra("", -1)
        if (isUpdating > 0) {
            binding.buttonCar.text = "Update Car"
        } else {
            binding.buttonCar.text = "Create Car"
        }

        binding.buttonCar.setOnClickListener {
            processCar()
        }

        viewModel.properties.observe(this, {
            drawExtraPropertiesCar(it)
        })

        binding.spinnerCategory.onItemSelectedListener = this
    }

    private fun drawExtraPropertiesCar(properties: List<PropertyEntity>?) {
        binding.linearLayoutExtra.removeAllViews()
        if (properties != null && properties.count() > 0) {
            binding.linearLayoutExtra.visibility = View.VISIBLE
            for (property in properties) {
                val editText = EditText(this)
                val lParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                editText.hint = "Please enter ${property.name}"
                editText.layoutParams = lParams
                editText.tag = property.propertyId
                if (property.isNumber != null && property.isNumber!!) {
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                } else {
                    editText.inputType = InputType.TYPE_CLASS_TEXT
                }

                binding.linearLayoutExtra.addView(editText)
            }
        } else {
            binding.linearLayoutExtra.visibility = View.GONE
        }

    }

    private fun processCar() {
        if (isUpdating > 0) {
            updateCar()
        } else {
            insertCar()
        }
    }

    private fun insertCar() {
        val category = (binding.spinnerCategory.selectedItem as CategoryEntity).categoryId
        val properties = mutableListOf<PropertyEntity>()
        for (view in binding.linearLayoutExtra.children) {
            if (view is EditText) {
                properties.add(
                    PropertyEntity(
                        view.tag as Long,
                        view.inputType == InputType.TYPE_CLASS_NUMBER,
                        view.hint.toString(),
                        view.text.toString()
                    )
                )
            }
        }

        val car = CarEntity(
            0L,
            binding.editTextSeats.text.toString().toIntOrNull() ?: 0,
            binding.editTextPrice.text.toString().toDoubleOrNull() ?: 0.0,
            binding.switchNewOld.isChecked,
            binding.editTextModel.text.toString(),
            category != categoryNotEditable,
            binding.editTextDate.text.toString(),
            category,
            properties
        )

        viewModel.saveCar(car)
    }

    private fun updateCar() {
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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val category = (binding.spinnerCategory.selectedItem as CategoryEntity).categoryId
        viewModel.loadProperties(category)
        binding.buttonCar.isEnabled = category != categoryNotEditable || isUpdating < 0
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}



