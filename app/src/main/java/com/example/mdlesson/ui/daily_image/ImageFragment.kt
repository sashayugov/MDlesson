package com.example.mdlesson.ui.daily_image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.transition.TransitionManager
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import coil.api.load
import com.example.mdlesson.R
import com.example.mdlesson.databinding.FragmentImageBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class ImageFragment : Fragment() {

    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DailyImageViewModel>()
    private var textVisible = false

    private lateinit var dailyImageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var wikiTextInputLayout: TextInputLayout
    private lateinit var inputEditTextWiki: TextInputEditText
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var titleTextView: TextView
    private lateinit var explanationTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getImageData().observe(this) { dailyImage -> renderData(dailyImage) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dailyImageView = binding.imageViewForNasa
        progressBar = binding.progressBar
        wikiTextInputLayout = binding.inputLayoutWiki
        inputEditTextWiki = binding.inputEditTextWiki

        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        titleTextView = view.findViewById(R.id.bottom_sheet_description_header)
        explanationTextView = view.findViewById(R.id.bottom_sheet_description)

        onImageClick()
        onWikipediaSearch()
    }

    private fun renderData(dailyImage: DailyImage) {
        when (dailyImage) {
            is DailyImage.Success -> {
                renderSuccessImageData(dailyImage)
                renderSuccessDescriptionData(dailyImage)
            }
            is DailyImage.Loading -> {
                progressBar.isVisible
            }
            is DailyImage.Error -> {
                Toast.makeText(requireContext(), "Error, no image", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun renderSuccessImageData(dailyImage: DailyImage.Success) {
        val serverResponseData = dailyImage.serverResponseData
        val url = serverResponseData.url
        if (url.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error, no image URL", Toast.LENGTH_LONG)
                .show()
        } else {
            progressBar.isVisible = false
            dailyImageView.load(url) {
                lifecycle(this@ImageFragment)
                error(android.R.drawable.ic_menu_report_image)
                placeholder(android.R.drawable.ic_menu_gallery)
            }
        }
    }

    private fun renderSuccessDescriptionData(dailyImage: DailyImage.Success) {
        val serverResponseData = dailyImage.serverResponseData
        val title = serverResponseData.title
        val explanation = serverResponseData.explanation
        if (explanation.isNullOrEmpty() && title.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Error, no explanation", Toast.LENGTH_LONG)
                .show()
        } else {
            titleTextView.text = title
            explanationTextView.text = explanation
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = from(bottomSheet)
        bottomSheetBehavior.state = STATE_HIDDEN

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    STATE_HIDDEN -> setAnimationImageTapHint()
                }

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })

    }

    private fun onImageClick() {
        dailyImageView.setOnClickListener {
            if (bottomSheetBehavior.state == STATE_COLLAPSED) {
                bottomSheetBehavior.state = STATE_HIDDEN
            } else {
                bottomSheetBehavior.state = STATE_COLLAPSED
                setAnimationImageTapHint()
            }
        }
    }

    private fun onWikipediaSearch() {
        wikiTextInputLayout.setEndIconOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val url = "https://ru.wikipedia.org/wiki/${inputEditTextWiki.text.toString()}"
            val uri = Uri.parse(url)
            intent.data = uri
            startActivity(intent)
        }
    }

    private fun setAnimationImageTapHint() {
        TransitionManager.beginDelayedTransition(binding.animationContainer)
        textVisible = !textVisible
        binding.imageTapHint.visibility = if (textVisible) View.GONE else View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}