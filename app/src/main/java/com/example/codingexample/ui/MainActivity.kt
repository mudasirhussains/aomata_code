package com.example.codingexample.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codingexample.BR
import com.example.codingexample.R
import com.example.codingexample.adapters.ImageListingAdapter
import com.example.codingexample.databinding.ActivityMainBinding
import com.example.codingexample.interfaces.ItemClickListener
import com.example.codingexample.models.Hit
import com.example.codingexample.utils.Constants
import com.example.codingexample.viewmodels.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var mBinding: ActivityMainBinding
    private var mAdapter: ImageListingAdapter = ImageListingAdapter()

    //    private lateinit var mViewModel: MainActivityViewModel
    private var mImagesArrayList: ArrayList<Hit> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = setCoroutineObserver()
        setBindings(viewModel)
//        if (isNetworkAvailable(this)) {
//            apiCalling()
//        }
//        setCoroutineObserver()
        callBacks()
    }


    private fun callBacks() {
        mBinding.btnTwo.setOnClickListener {
            setGridAdapter(
                mBinding.btnTwo.text.toString().toInt()
            )
        }

        mBinding.btnThree.setOnClickListener {
            setGridAdapter(
                mBinding.btnThree.text.toString().toInt()
            )
        }

        mBinding.btnFour.setOnClickListener {
            setGridAdapter(
                mBinding.btnFour.text.toString().toInt()
            )
        }
    }

//    private fun apiCalling() {
//        mViewModel.getAllImagesFromApi()
//    }

    private fun setBindings(viewModel: MainActivityViewModel) {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //mViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mBinding.setVariable(BR.viewModel, viewModel)
        mBinding.executePendingBindings()
        setGridAdapter(3)
    }

    private fun setCoroutineObserver(): MainActivityViewModel {
        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getRecyclerListDataObserver().observe(this, {
            hideProgress()
            if (it != null) {
                viewModel.setAdapterData(it.hits as ArrayList<Hit>)
//                mImagesArrayList = it.hits as ArrayList<Hit>

            } else {
                Toast.makeText(this@MainActivity, "Error in fetching data", Toast.LENGTH_LONG)
                    .show()

            }
        })

        viewModel.loading.observe(this, { isError ->
            if (isError) {
                Log.d("UserLoadError", "Exceptions! Error")
                showProgress()
            } else {
                Log.d("UserLoadError", "Ok Response")
                hideProgress()
            }
        })
        viewModel.getAllImagesFromApi()
        return viewModel
    }

    private fun setGridAdapter(spam: Int) {
//        val gridLayoutManager = GridLayoutManager(applicationContext, spam)
//        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
//        mBinding.recyclerMain.layoutManager = gridLayoutManager
//        mAdapter.notifyDataSetChanged()
        mBinding.recyclerMain.apply {
            val gridLayoutManager = GridLayoutManager(applicationContext, spam)
            gridLayoutManager.orientation = LinearLayoutManager.VERTICAL
            mBinding.recyclerMain.layoutManager = gridLayoutManager
        }
    }

    private fun showProgress() {
        mBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        mBinding.progressBar.visibility = View.GONE
    }

    /*override fun onImageCLicked(imageUrl: String) {

        customAlert(imageUrl)

    }*/

    fun customAlert(url: String) {
        val view: View = LayoutInflater.from(this).inflate(R.layout.option_dialog, null)
        val dialog: Dialog
        val positiveBtn = view.findViewById<Button>(R.id.positiveBtn)
        val negativeBtn = view.findViewById<Button>(R.id.negativeBtn)
        val subTitleText = view.findViewById<TextView>(R.id.subTitleText)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setView(view)
        dialog = builder.create()
        negativeBtn.setOnClickListener {
            dialog.dismiss()
        }
        positiveBtn.setOnClickListener {
            dialog.dismiss()
            startActivityIntent(url)
        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setDimAmount(0.5f)
        subTitleText.setText(R.string.dialog_title)
        dialog.show()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }

    private fun startActivityIntent(url: String) {
        //Toast.makeText(applicationContext, url, Toast.LENGTH_SHORT).show()
        val detailIntent = Intent(applicationContext, DetailActivity::class.java)
        detailIntent.putExtra(Constants.LARGE_IMAGE_URL, url)
        startActivity(detailIntent)
    }

    override fun onImageCLicked(imageUrl: String) {
        TODO("Not yet implemented")
    }
}