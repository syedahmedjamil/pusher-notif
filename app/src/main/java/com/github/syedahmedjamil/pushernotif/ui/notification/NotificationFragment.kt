package com.github.syedahmedjamil.pushernotif.ui.notification

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.syedahmedjamil.pushernotif.AppContainer
import com.github.syedahmedjamil.pushernotif.BaseApplication
import com.github.syedahmedjamil.pushernotif.R
import com.github.syedahmedjamil.pushernotif.databinding.FragmentNotificationBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var interests: List<String>
    private lateinit var instanceId: String

    private lateinit var viewModel: NotificationViewModel
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            interests = it.getStringArrayList("interests")!!
            instanceId = it.getString("instanceId").toString()
        }
        setHasOptionsMenu(true)
        appContainer = (requireContext().applicationContext as BaseApplication).appContainer

        //manual DI
        viewModel = ViewModelProvider(
            this, NotificationViewModel.NotificationViewModelFactory(
                appContainer.getNotificationsUseCase,
                appContainer.deleteNotificationsUseCase
            )
        )[NotificationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupEvents()
        setupBinding()
        setUpTabs()
        loadNotifications()
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.adapter =
            NotificationListAdapter(requireContext(), R.layout.notification_list_item, viewModel)
        binding.instanceId = instanceId
    }

    private fun setUpTabs() {
        for (interest in interests) {
            binding.tabLayout.addTab(
                binding.tabLayout.newTab().setText(interest)
            )
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                loadNotifications()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    private fun loadNotifications() {
        val position = binding.tabLayout.selectedTabPosition
        val selectedInterest = binding.tabLayout.getTabAt(position)?.text.toString()
        viewModel.selectInterest(selectedInterest)

    }

    private fun setupEvents() {
        viewModel.openLinkEvent.observe(viewLifecycleOwner) { event ->
            event.getValueIfNotHandled()?.let {
                val webpage = Uri.parse(it)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                val chooser = Intent.createChooser(intent, "Select Browser")
                ContextCompat.startActivity(requireContext(), chooser, null)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                AlertDialog.Builder(requireContext())
                    .setTitle("Hold On !")
                    .setMessage("Are you sure you want to delete all notifications?")
                    .setPositiveButton("Cancel") { _, _ ->
                    }
                    .setNegativeButton("Yes") { _, _ ->
                        viewModel.deleteNotifications()
                    }.show()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}