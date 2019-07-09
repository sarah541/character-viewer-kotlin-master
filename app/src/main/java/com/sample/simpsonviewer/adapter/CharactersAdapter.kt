package com.sample.simpsonviewer.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.sample.simpsonviewer.*
import com.sample.simpsonviewer.model.RelatedTopic
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CharactersAdapter(private val list: ArrayList<RelatedTopic>, private val isTablet: Boolean)
    : RecyclerView.Adapter<CharactersAdapter.Item>(), Filterable {
    private var filteredList: List<RelatedTopic> = list


    override fun getItemCount(): Int = filteredList.size

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(charSequence: CharSequence): FilterResults {
            val charString = charSequence.toString()
            filteredList = list.filter {
                it.characterName.contains(charString, true)
                        || it.description.contains(charString, true)
            }

            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
            filteredList = filterResults.values as ArrayList<RelatedTopic>
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = Item(LayoutInflater
            .from(viewGroup.context).inflate(R.layout.item_character, viewGroup, false))

    override fun onBindViewHolder(item: Item, position: Int) {
        val relatedTopic = filteredList[position]
        val name = relatedTopic.characterName
        val imageURL = relatedTopic.icon?.url

        item.name.text = name
        if (!isTablet) {
            item.name.setOnClickListener { view ->
                val intent = Intent(view.context, DetailActivity::class.java)
                intent.putExtra(KEY_NAME, name)
                val description = relatedTopic.description
                intent.putExtra(KEY_DESCRIPTION, description)
                intent.putExtra(KEY_IMAGE_URL, imageURL)
                view.context.startActivity(intent)
            }
        } else if (item.description != null && item.imageView != null && item.imageProgress != null) {
            item.description?.text = relatedTopic.description
            if (imageURL != null && imageURL.isNotEmpty()) {
                Picasso.get()
                        .load(imageURL)
                        .error(R.drawable.placeholder_thumb)
                        .into(item.imageView, object : Callback {
                            override fun onSuccess() {
                                item.imageProgress?.visibility = View.GONE
                                item.imageView?.visibility = View.VISIBLE
                            }

                            override fun onError(e: Exception) {
                                item.imageProgress?.visibility = View.GONE
                                item.imageView?.visibility = View.VISIBLE
                            }
                        })
            } else {
                item.imageProgress?.visibility = View.GONE
                item.imageView?.visibility = View.VISIBLE
                item.imageView?.setImageResource(R.drawable.placeholder_thumb)
            }
        }
    }

    class Item(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.name)
        lateinit var name: TextView
        @Nullable
        @JvmField
        @BindView(R.id.description)
        var description: TextView? = null
        @Nullable
        @JvmField
        @BindView(R.id.imageView)
        var imageView: ImageView? = null
        @Nullable
        @JvmField
        @BindView(R.id.imageProgress)
        var imageProgress: ProgressBar? = null

        init {
            ButterKnife.bind(this, itemView)
        }
    }
}
