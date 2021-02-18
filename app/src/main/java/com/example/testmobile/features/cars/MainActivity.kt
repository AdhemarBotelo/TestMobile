package com.example.testmobile.features.cars

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testmobile.R
import com.example.testmobile.core.Failure
import com.example.testmobile.databinding.ActivityMainBinding
import com.example.testmobile.interactor.entities.CarEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    val viewModel: CarsViewModel by inject<CarsViewModel>()
    private lateinit var binding: ActivityMainBinding
    private lateinit var carsAdapter: CarsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel.initDataBase()
        viewModel.cars.observe(this, {
            renderCarList(it)
        })

        viewModel.failure.observe(this, {
            handlerFailure(it)
        })

        binding.content.recyclerCars.layoutManager = LinearLayoutManager(this)
        carsAdapter = CarsAdapter()
        binding.content.recyclerCars.adapter = carsAdapter
        carsAdapter.clickListener = {
            startActivity(Intent(this, CarActivity::class.java).apply {
                putExtra(
                    "CarId",
                    it.carId
                )
            })
        }

        loadCars()
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            startActivity(Intent(this, CarActivity::class.java).apply { putExtra("CarId", -1) })
        }

    }

    private fun loadCars() {
        showProgress()
        viewModel.loadCars()
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
        hideProgress()
        Snackbar.make(this, binding.content.recyclerCars, message, Snackbar.LENGTH_LONG)

    }

    private fun renderCarList(cars: List<CarEntity>?) {
        carsAdapter.collection = cars.orEmpty()
        hideProgress()
    }

    private fun showProgress() {
        binding.content.progressBarCars.visibility = View.VISIBLE
        binding.content.recyclerCars.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.content.progressBarCars.visibility = View.GONE
        binding.content.recyclerCars.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_category -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


}