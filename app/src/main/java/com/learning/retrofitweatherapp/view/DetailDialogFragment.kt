package com.learning.retrofitweatherapp.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.learning.retrofitweatherapp.R
import com.learning.retrofitweatherapp.databinding.DialogFragmentDetailBinding
import com.learning.retrofitweatherapp.model.dto.response.SearchResponseItem

class DetailDialogFragment : DialogFragment(R.layout.dialog_fragment_detail) {

    private var _binding: DialogFragmentDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_SEARCH_ITEM = "SEARCH_ITEM"

        fun newInstance(item: SearchResponseItem): DetailDialogFragment {
            return DetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SEARCH_ITEM, item)
                }
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setBackgroundDrawableResource(R.drawable.dialog_background)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchItem: SearchResponseItem? = arguments?.getParcelable(ARG_SEARCH_ITEM)
        searchItem?.let { item ->
            binding.apply {
                tvName.text = item.name
                tvCountry.text = item.country
                tvRegion.text = item.region
                tvLatitude.text = item.lat.toString()
                tvLongitude.text = item.lon.toString()
            }
        }

        binding.btClose.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

