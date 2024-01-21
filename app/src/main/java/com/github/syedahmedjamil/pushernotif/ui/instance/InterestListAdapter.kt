package com.github.syedahmedjamil.pushernotif.ui.instance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.github.syedahmedjamil.pushernotif.databinding.InterestListItemBinding


class InterestListAdapter(
    context: Context,
    resource: Int,
    private val viewModel: InstanceViewModel
) :
    ArrayAdapter<String>(context, resource) {

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val binding = convertView?.tag as? InterestListItemBinding ?:
        InterestListItemBinding.inflate(LayoutInflater.from(context), parent, false)

        val item = getItem(position)
        binding.viewmodel = viewModel
        binding.name = item

        return binding.root
    }
}