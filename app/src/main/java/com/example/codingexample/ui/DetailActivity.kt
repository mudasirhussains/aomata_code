package com.example.codingexample.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.codingexample.R
import com.example.codingexample.databinding.ActivityDetailBinding
import com.example.codingexample.utils.Constants

class DetailActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityDetailBinding
    private lateinit var imageUrl: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
        setAnimView()
        getIntents()
    }

    private fun setBindings() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setContentView(mBinding.root)
        supportActionBar?.setTitle(R.string.detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getIntents() {
        imageUrl = intent.getStringExtra(Constants.LARGE_IMAGE_URL).toString()
        setImage(imageUrl)
    }

    private fun setImage(imageUrl: String) {
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        Glide.with(this).load(imageUrl)
            .placeholder(circularProgressDrawable)
            .into(mBinding.detailImage)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setAnimView() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

}