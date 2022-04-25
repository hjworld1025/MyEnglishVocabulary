package org.hans.myenglishvocabulary.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import org.hans.myenglishvocabulary.R
import org.hans.myenglishvocabulary.adapter.CardAdapter
import org.hans.myenglishvocabulary.databinding.FragmentStudyBinding
import org.hans.myenglishvocabulary.viewmodel.ListViewModel
import java.lang.Math.abs

class StudyFragment : Fragment() {
    private var _binding: FragmentStudyBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(requireActivity())[ListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStudyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardAdapter = CardAdapter(binding.cardViewSeekBar)

        listViewModel.loadAllList().observe(requireActivity(), Observer {
            cardAdapter.setData(it)
        })

        val previewPx = resources.getDimension(R.dimen.viewpager2_preview)
        val pageMarginPx = resources.getDimension(R.dimen.viewpager2_page_margin)

        binding.cardViewPager2.apply {
            adapter = cardAdapter
            // 패딩공간을 스크롤할 때 공간으로 활용하기 위해 false로 설정
            clipToPadding = false
            offscreenPageLimit = 1

            setPageTransformer { page, position ->
                page.translationX = -(previewPx + pageMarginPx) * position
                page.scaleY = 1 - (0.25f * abs(position))
            }

            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    binding.cardViewSeekBar.progress = position
                    cardAdapter.defaultCard(position - 1)
                    cardAdapter.defaultCard(position + 1)
                }
            })
        }

        binding.cardViewSeekBar.apply{
            max = 0

            setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    binding.cardViewPager2.currentItem = progress
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })
        }

        binding.leftButton.setOnClickListener {
            binding.cardViewPager2.currentItem--
        }

        binding.openButton.setOnClickListener {
            cardAdapter.openCard(binding.cardViewPager2.currentItem)
        }

        binding.rightButton.setOnClickListener {
            binding.cardViewPager2.currentItem++
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}