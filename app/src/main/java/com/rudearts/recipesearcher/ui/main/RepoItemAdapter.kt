package com.rudearts.recipesearcher.ui.main

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.rudearts.recipesearcher.R
import com.rudearts.recipesearcher.databinding.RecipeItemBinding
import com.rudearts.recipesearcher.domain.model.Recipe
import com.rudearts.recipesearcher.extentions.loadUrlThumb
import javax.inject.Inject


class RepoItemAdapter @Inject constructor(
        private val context: Context?) : RecyclerView.Adapter<RepoItemAdapter.RepoItemHolder>() {

    companion object {
        private const val DEFAULT_IMAGE_SIZE = 150
        private const val DEFAULT_PLACEHOLDER_ID = R.drawable.user
    }

    private val inflater by lazy { LayoutInflater.from(context) }

    private var items: List<Recipe> = ArrayList()

    internal var listener: ((Recipe?) ->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RepoItemHolder(getBinding(parent))

    override fun onBindViewHolder(holder: RepoItemHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    private fun getBinding(convertView: View?) = when (convertView) {
        null -> createViewBinding()
        else -> updateViewBinding(convertView) ?: createViewBinding()
    }

    fun updateItems(items: List<Recipe>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    private fun getItem(position: Int) = items[position]

    private fun createViewBinding(): RecipeItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.recipe_item, null, false)

    private fun updateViewBinding(convertView: View) =
            DataBindingUtil.getBinding<RecipeItemBinding>(convertView)

    inner class RepoItemHolder(private val binding: RecipeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            with(binding) {
                item = recipe
                setupListener(root, item)
                loadImageResource(avatar, item?.imageUrl)
                executePendingBindings()
            }
        }

        private fun setupListener(root: View, item: Recipe?) {
            root.setOnClickListener {
                listener?.let {
                    it(item)
                }
            }
        }

        private fun loadImageResource(avatar: ImageView, avatarUrl: String?) {
            avatar.setImageResource(DEFAULT_PLACEHOLDER_ID)
            avatarUrl?.let { url ->
                avatar.loadUrlThumb(DEFAULT_IMAGE_SIZE, DEFAULT_PLACEHOLDER_ID, url)
            }
        }
    }
}