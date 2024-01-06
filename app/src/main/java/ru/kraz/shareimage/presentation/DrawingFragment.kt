package ru.kraz.shareimage.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.shareimage.R
import ru.kraz.shareimage.databinding.FragmentDrawingBinding

class DrawingFragment : Fragment() {
    private var _binding: FragmentDrawingBinding? = null
    private val binding: FragmentDrawingBinding
        get() = _binding!!

    private val viewModel: DrawingViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDrawingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.palette.setOnClickListener {
            ColorPickerDialog
                .Builder(requireContext())
                .setTitle(getString(R.string.get_color))
                .setColorListener { _, colorHex ->
                    binding.drawingView.setColor(colorHex)
                }
                .show()
        }

        binding.clear.setOnClickListener {
            binding.drawingView.clear()
        }

        binding.undo.setOnClickListener {
            binding.drawingView.undo()
        }

        binding.save.setOnClickListener {
            binding.save.isEnabled = false
            binding.drawingView.save { file ->
                viewModel.uploadFile(file)
            }
        }

        viewModel.uploadFile.observe(viewLifecycleOwner) {
            it.getContentOrNot { uiState ->
                binding.save.isEnabled = true
                when (uiState) {
                    is DrawingUiState.Success -> renderResult(getString(R.string.success_upload_result))
                    is DrawingUiState.Error -> renderResult(getString(R.string.error_upload_result))
                }
            }
        }
    }

    private fun renderResult(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            DrawingFragment()
    }
}