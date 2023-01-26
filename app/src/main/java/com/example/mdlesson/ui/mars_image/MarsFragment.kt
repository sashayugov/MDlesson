package com.example.mdlesson.ui.mars_image

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.api.load
import com.example.mdlesson.databinding.FragmentMarsBinding
import com.squareup.picasso.Picasso


class MarsFragment : Fragment() {

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MarsImageViewModel>()

    private lateinit var marsImageView: ImageView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMarsImageData().observe(this) { marsImage -> renderMarsImageData(marsImage) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        marsImageView = binding.marsImage
        progressBar = binding.marsImageProgressBar
    }

    private fun renderMarsImageData(marsImage: MarsImage) {
        when (marsImage) {
            is MarsImage.Success -> {
                renderSuccessImageData(marsImage)
            }
            is MarsImage.Loading -> {
                progressBar.isVisible
            }
            is MarsImage.Error -> {
                Toast.makeText(requireContext(), "Error, no image", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderSuccessImageData(marsImage: MarsImage.Success) {
        val serverResponseData = marsImage.serverResponseData
        val url = serverResponseData.photos[0]?.imgSrc
        if (url.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error, no image URL", Toast.LENGTH_LONG)
                .show()
        } else {
            progressBar.isVisible = false
            marsImageView.load(url) {
                lifecycle(this@MarsFragment)
                error(android.R.drawable.ic_menu_report_image)
                placeholder(android.R.drawable.ic_menu_gallery)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}