package com.sample.simpsonviewer

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.annotations.Nullable

class DetailActivity : AppCompatActivity() {

    @BindView(R.id.imageView)
    lateinit var imageView: ImageView
    @BindView(R.id.imageProgress)
    lateinit var imageProgress: ProgressBar
    @BindView(R.id.name)
    lateinit var name: TextView
    @BindView(R.id.description)
    lateinit var description: TextView

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)

        val actionBar = supportActionBar
        if (actionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        if (intent.extras != null) {
            val name = intent.extras?.getString(KEY_NAME)
            val description = intent.extras?.getString(KEY_DESCRIPTION)
            val imageURL = intent.extras?.getString(KEY_IMAGE_URL)
            this.name.text = name
            this.description.text = description
            Log.d("DetailActivity", "Image URL: $imageURL")
            if (imageURL != null && imageURL.isNotEmpty()) {
                Picasso.get()
                        .load(imageURL)
                        .error(R.drawable.placeholder_thumb)
                        .into(imageView, object : Callback {
                            override fun onSuccess() {
                                hideImageProgress()
                            }

                            override fun onError(e: Exception) {
                                hideImageProgress()
                                Log.e("DetailActivity", "Error loading image! ", e)

                            }
                        })
            } else {
                hideImageProgress()
                this.imageView.setImageResource(R.drawable.placeholder_thumb)
            }

        }
    }

    private fun hideImageProgress() {
        imageProgress.visibility = View.GONE
        imageView.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
