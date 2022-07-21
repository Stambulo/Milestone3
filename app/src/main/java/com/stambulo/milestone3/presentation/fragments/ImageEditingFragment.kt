package com.stambulo.milestone3.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.stambulo.milestone3.R
import com.stambulo.milestone3.databinding.FragmentImageEditingBinding
import com.stambulo.milestone3.presentation.intents.EditingIntent
import com.stambulo.milestone3.presentation.states.EditingState
import com.stambulo.milestone3.presentation.viewmodels.EditingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageEditingFragment : BaseFragment<FragmentImageEditingBinding>() {
    private val viewModel: EditingViewModel by viewModels()
    private lateinit var imageName: String

    override fun inflateMethod(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): FragmentImageEditingBinding {
        return FragmentImageEditingBinding.inflate(inflater, viewGroup, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                goToGalleryFragment()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageName = arguments?.getString("imageName") ?: "no file name"
        setupViewModel()
        observeViewModel()
        setOnClickListener()
    }

    override fun setupViewModel() {
        binding.progressBar.isVisible = true
        lifecycleScope.launch {
            viewModel.intent.send(EditingIntent.ShowImage(imageName))
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.editingState.collect{
                when (it.type) {
                    EditingState.Type.IDLE -> {}
                    EditingState.Type.Loading -> { renderLoading() }
                    EditingState.Type.ShowImage -> { showImage() }
                }
            }
        }
    }

    private fun renderLoading() {
        binding.progressBar.isVisible = true
    }

    private fun showImage() {
        binding.progressBar.isVisible = false
        imageLoader.loadInto(imageName, binding.editImage)
    }

    private fun setOnClickListener(){
        binding.backChevron.setOnClickListener {
            goToGalleryFragment()
        }
    }

    private fun goToGalleryFragment() {
        Navigation.findNavController(requireActivity(), R.id.nav_host)
            .navigate(R.id.action_editingFragment_to_galleryFragment)
    }
}