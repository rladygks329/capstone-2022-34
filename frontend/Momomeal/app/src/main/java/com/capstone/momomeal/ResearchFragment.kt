package com.capstone.momomeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.capstone.momomeal.databinding.FragmentResearchBinding
import com.capstone.momomeal.feature.BaseDialogFragment

class ResearchFragment : BaseDialogFragment<FragmentResearchBinding>(FragmentResearchBinding::inflate) {
    private val TAG = "ResearchFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialog)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val retView = super.onCreateView(inflater, container, savedInstanceState)
        binding.fragmentResearchTable.setOnClickListener{

        }
        binding.fragmentResearchSubmit.setOnClickListener{
            dismiss()
        }
        return retView
    }

}
