package com.example.mdlesson.ui.moon_image

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
import com.example.mdlesson.databinding.FragmentMoonBinding
import com.squareup.picasso.Picasso


class MoonFragment : Fragment() {

    private var _binding: FragmentMoonBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MoonImageViewModel>()

    private lateinit var moonImageView: ImageView
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getMoonImageData().observe(this) { moonImage -> renderMoonImageData(moonImage) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moonImageView = binding.moonImage
        progressBar = binding.moonImageProgressBar
    }

    private fun renderMoonImageData(moonImage: MoonImage) {
        when (moonImage) {
            is MoonImage.Success -> {
                renderSuccessImageData(moonImage)
            }
            is MoonImage.Loading -> {
                progressBar.isVisible
            }
            is MoonImage.Error -> {
                Toast.makeText(requireContext(), "Error, no image", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderSuccessImageData(moonImage: MoonImage.Success) {
        val serverResponseData = moonImage.serverResponseData
        val url = serverResponseData.url
        if (url.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error, no image URL", Toast.LENGTH_LONG)
                .show()
        } else {
            progressBar.isVisible = false
            moonImageView.load(url) {
                lifecycle(this@MoonFragment)
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