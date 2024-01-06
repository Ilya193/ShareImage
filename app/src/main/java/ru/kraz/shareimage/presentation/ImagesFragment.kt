package ru.kraz.shareimage.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.shareimage.databinding.FragmentImagesBinding


class ImagesFragment : Fragment() {
    private var _binding: FragmentImagesBinding? = null
    private val binding: FragmentImagesBinding
        get() = _binding!!

    private val viewModel: ImagesViewModel by viewModel()

    /*private val adapter = simpleAdapter<String, ItemBinding> {
        bind {
            image.load(it)
        }
    }*/

    private val adapter = ImagesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.images.setHasFixedSize(true)
        binding.images.adapter = adapter
        binding.images.addItemDecoration(CustomItemDecoration())
        binding.btnRetry.setOnClickListener {
            viewModel.fetchImages()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchImages()
        viewModel.images.observe(viewLifecycleOwner) {
            binding.images.visibility = if (it is ImagesUiState.Success) View.VISIBLE else View.GONE
            binding.containerError.visibility = if (it is ImagesUiState.Error) View.VISIBLE else View.GONE
            binding.loading.visibility = if (it is ImagesUiState.Loading) View.VISIBLE else View.GONE
            if (it is ImagesUiState.Success) adapter.submitList(it.data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            ImagesFragment()
    }
}


