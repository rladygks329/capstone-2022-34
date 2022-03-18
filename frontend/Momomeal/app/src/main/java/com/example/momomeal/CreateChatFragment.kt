package com.example.momomeal

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import com.example.momomeal.databinding.FragmentCreateChatBinding

class CreateChatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentCreateChatBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toolbar = binding.toolbarCreateChat

        // Spinner Setting
        setupCategorySpinner()
        binding.spnChatCategory.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        setupMaxCapacitySpinner()
        binding.spnChatMaxCapacity.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_chat, container, false)
    }

    private fun setupCategorySpinner() {
        val categoryItem = resources.getStringArray(R.array.spn_categoty_array)
        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryItem)
        binding.spnChatCategory.adapter = categoryAdapter
    }

    private fun setupMaxCapacitySpinner() {
        val maxCapacityItem = resources.getStringArray(R.array.spn_max_capacity_array)
        val maxCapacityAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, maxCapacityItem)
        binding.spnChatMaxCapacity.adapter = maxCapacityAdapter
    }
}