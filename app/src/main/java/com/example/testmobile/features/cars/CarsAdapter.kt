package com.example.testmobile.features.cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testmobile.R
import com.example.testmobile.core.inflate
import com.example.testmobile.data.models.Car
import com.example.testmobile.databinding.ItemCarBinding
import com.example.testmobile.interactor.entities.CarEntity
import kotlin.properties.Delegates

class CarsAdapter : RecyclerView.Adapter<CarsAdapter.ViewHolder>() {

    internal var collection: List<CarEntity> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (CarEntity) -> Unit = { _ -> }

    internal var clickDeleteListener: (CarEntity) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener, clickDeleteListener)

    override fun getItemCount() = collection.size

    class ViewHolder(val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            car: CarEntity,
            clickListener: (CarEntity) -> Unit,
            clickDeleteListener: (CarEntity) -> Unit
        ) {
            binding.TextViewSeats.text = car.seats.toString()
            binding.textViewPrice.text = car.price.toString()
            binding.switchNew.isChecked = car.isNew ?: false
            binding.textViewModel.text = car.model
            binding.textViewDate.text = car.dateReleased.toString()
            binding.textViewCategory.text = car.categoryId

            binding.imageButtonDelete.setOnClickListener {
                clickDeleteListener(car)
            }

            itemView.setOnClickListener {
                clickListener(
                    car
                )
            }
        }
    }
}
