package com.example.testmobile.features.cars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.core.view.children
import com.example.testmobile.R
import com.example.testmobile.core.Failure
import com.example.testmobile.core.categoryNotEditable
import com.example.testmobile.databinding.ActivityCarBinding
import com.example.testmobile.interactor.entities.CarEntity
import com.example.testmobile.interactor.entities.CategoryEntity
import com.example.testmobile.interactor.entities.PropertyEntity
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class CarActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val viewModel: CarViewModel by inject<CarViewModel>()
    private lateinit var binding: ActivityCarBinding
    var carCodeSelected = 0L
    private lateinit var categories: List<CategoryEntity>
    private lateinit var selectedCar: CarEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.loadCategories()

        viewModel.failure.observe(this, {
            handlerFailure(it)
        })

        viewModel.categories.observe(this, {
            setCategories(it)
        })

        viewModel.wasProcessed.observe(this, {
            if (it) finish()
        })

        viewModel.failure.observe(this, {
            var message = "fallo"
        })

        carCodeSelected = intent.getLongExtra("CarId", -1)
        if (carCodeSelected > 0) {
            viewModel.getCar(carCodeSelected)
            binding.buttonCar.text = "Update Car"
        } else {
            binding.buttonCar.text = "Create Car"
        }

        binding.buttonCar.setOnClickListener {
            processCar()
        }

        viewModel.properties.observe(this, {
            drawExtraPropertiesCar(it)
            if (carCodeSelected > 0) {
                drawCar()
            }
        })

        viewModel.car.observe(this, {
            binding.spinnerCategory.setSelection(categories.indexOfFirst { categoryEntity -> categoryEntity.categoryId == it.categoryId })
            selectedCar = it
        })

        binding.spinnerCategory.onItemSelectedListener = this
    }

    private fun drawCar() {
        if (selectedCar != null) {
            binding.editTextSeats.setText(selectedCar.seats.toString())
            binding.editTextPrice.setText(selectedCar.price.toString())
            binding.editTextModel.setText(selectedCar.model.toString())
            binding.editTextDate.setText(selectedCar.dateReleased.toString())
            binding.switchNewOld.isChecked = selectedCar.isNew ?: false

            for (view in binding.linearLayoutExtra.children) {
                if (view is EditText) {
                    val propertyFound = selectedCar.properties.find { it.propertyId == view.tag }
                    if (propertyFound != null) {
                        view.setText(propertyFound.value)
                    }
                }
            }
        }
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
        if (isValidCar()) {
            if (carCodeSelected > 0) {
                updateCar()
            } else {
                insertCar()
            }
        }

    }

    private fun isValidCar(): Boolean {
        if (binding.editTextSeats.text.isNullOrEmpty()) {
            Toast.makeText(this, "Seats is required", Toast.LENGTH_LONG).show()
            return false
        }
        if (binding.editTextPrice.text.isNullOrEmpty()) {
            Toast.makeText(this, "Price is required", Toast.LENGTH_LONG).show()
            return false
        }
        if (binding.editTextModel.text.isNullOrEmpty()) {
            Toast.makeText(this, "Model is required", Toast.LENGTH_LONG).show()
            return false
        }
        if (binding.editTextDate.text.isNullOrEmpty()) {
            Toast.makeText(this, "Date release is required", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun getInfoFromView(): CarEntity {
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
        val carId = if(carCodeSelected>0) carCodeSelected else  0L
        return CarEntity(
            carId,
            binding.editTextSeats.text.toString().toIntOrNull() ?: 0,
            binding.editTextPrice.text.toString().toDoubleOrNull() ?: 0.0,
            binding.switchNewOld.isChecked,
            binding.editTextModel.text.toString(),
            category != categoryNotEditable,
            binding.editTextDate.text.toString(),
            category,
            properties
        )
    }

    private fun insertCar() {
        viewModel.saveCar(getInfoFromView())
    }

    private fun updateCar() {
        viewModel.updateCar(getInfoFromView())
    }

    private fun setCategories(it: List<CategoryEntity>) {
        categories = it
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
        binding.buttonCar.isEnabled = category != categoryNotEditable || carCodeSelected < 0
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

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
        Snackbar.make(this, binding.buttonCar, message, Snackbar.LENGTH_LONG)
    }
}