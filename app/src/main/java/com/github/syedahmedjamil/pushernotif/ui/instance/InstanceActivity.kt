package com.github.syedahmedjamil.pushernotif.ui.instance

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.MyApplication
import com.github.syedahmedjamil.pushernotif.R
import com.github.syedahmedjamil.pushernotif.databinding.ActivityInstanceBinding
import com.google.android.material.snackbar.Snackbar

class InstanceActivity : AppCompatActivity() {

    private lateinit var viewModel: InstanceViewModel
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityInstanceBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_instance)
        binding.lifecycleOwner = this

        appContainer = (application as MyApplication).appContainer

        //manual DI
        viewModel = ViewModelProvider(
            this,
            InstanceViewModel.InstanceViewModelFactory(
                appContainer.addInterestUseCase,
                appContainer.getInterestsUseCase,
                appContainer.removeInterestUseCase
            )
        )[InstanceViewModel::class.java]

        //bindings
        binding.viewmodel = viewModel
        binding.adapter = InstanceInterestListAdapter(this, R.layout.interest_list_item, viewModel)

        //observe
        viewModel.errorMessage.observe(this) {
            Snackbar.make(binding.main, it, Snackbar.LENGTH_SHORT).show()
        }
    }
}