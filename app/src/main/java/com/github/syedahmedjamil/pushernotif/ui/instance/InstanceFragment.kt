package com.github.syedahmedjamil.pushernotif.ui.instance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.syedahmedjamil.pushernotif.R
import com.github.syedahmedjamil.pushernotif.databinding.FragmentInstanceBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InstanceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class InstanceFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentInstanceBinding
    private val viewModel: InstanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_instance, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBinding()
        setupEvents()
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.adapter =
            InterestListAdapter(requireContext(), R.layout.interest_list_item, viewModel)
    }

    private fun setupEvents() {
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(binding.instanceConstraintLayout, it, Snackbar.LENGTH_SHORT)
                .show()
        }
        viewModel.subscribeEvent.observe(viewLifecycleOwner) {
            it.getValueIfNotHandled()?.let {
                val instanceId = binding.instanceIdEditText.text.toString()
                val interests = viewModel.interests.value as ArrayList
                val bundle = Bundle()
                bundle.putStringArrayList("interests", interests)
                bundle.putString("instanceId", instanceId)
                findNavController().navigate(
                    R.id.action_instance_dest_to_notifications_dest, bundle
                )
            }
        }
    }


}