package com.example.mdlesson.ui.earth_image

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
import com.example.mdlesson.R
import com.example.mdlesson.databinding.FragmentEarthBinding


class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<EarthImageViewModel>()

    private lateinit var earthImageView: ImageView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getEarthImageData()
            .observe(this) { earthImage -> renderEarthImageData(earthImage) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        earthImageView = binding.earthImage
        progressBar = binding.earthImageProgressBar
    }

    private fun renderEarthImageData(earthImage: EarthImage) {
        when (earthImage) {
            is EarthImage.Success -> {
                renderSuccessImageData(earthImage)
            }
            is EarthImage.Loading -> {
                progressBar.isVisible
            }
            is EarthImage.Error -> {
                Toast.makeText(requireContext(), "Error, no image", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderSuccessImageData(earthImage: EarthImage.Success) {
        val serverResponseData = earthImage.serverResponseData
        val url = serverResponseData.url
        if (url.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error, no image URL", Toast.LENGTH_LONG)
                .show()
        } else {
            progressBar.isVisible = false
            earthImageView.load(url) {
                lifecycle(this@EarthFragment)
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