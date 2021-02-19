package com.example.testmobile.features.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testmobile.databinding.ItemCategoryBinding
import com.example.testmobile.interactor.entities.CategoryEntity
import kotlin.properties.Delegates

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    internal var collection: List<CategoryEntity> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (CategoryEntity) -> Unit = { _ -> }

    internal var clickDeleteListener: (CategoryEntity) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener, clickDeleteListener)

    override fun getItemCount() = collection.size

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            category: CategoryEntity,
            clickListener: (CategoryEntity) -> Unit,
            clickDeleteListener: (CategoryEntity) -> Unit
        ) {
            binding.categoryItemValue.text = category.categoryId

            binding.imageButtonCategoryDelete.setOnClickListener {
                clickDeleteListener(category)
            }

            itemView.setOnClickListener {
                clickListener(
                    category
                )
            }
        }
    }
}
