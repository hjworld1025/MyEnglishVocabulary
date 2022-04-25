package org.hans.myenglishvocabulary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.hans.myenglishvocabulary.adapter.MainDayAdapter
import org.hans.myenglishvocabulary.databinding.FragmentListBinding
import org.hans.myenglishvocabulary.viewmodel.DayViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val dayViewModel: DayViewModel by lazy {
        ViewModelProvider(requireActivity())[DayViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainDayAdapter = MainDayAdapter(requireContext(), requireActivity(), requireActivity())

        dayViewModel.loadAllDay().observe(requireActivity(), Observer {
            mainDayAdapter.setData(it)
        })

        binding.dayRecyclerView.adapter = mainDayAdapter
        binding.dayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}