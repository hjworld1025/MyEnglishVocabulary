package org.hans.myenglishvocabulary.fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.hans.myenglishvocabulary.R
import org.hans.myenglishvocabulary.adapter.DayAdapter
import org.hans.myenglishvocabulary.databinding.FragmentFabDialogAddBinding
import org.hans.myenglishvocabulary.db.DayMemo
import org.hans.myenglishvocabulary.db.ListMemo
import org.hans.myenglishvocabulary.viewmodel.DayViewModel
import org.hans.myenglishvocabulary.viewmodel.ListViewModel
import java.util.*
import kotlin.collections.ArrayList

class FabDialogAddFragment : DialogFragment() {
    private var _binding: FragmentFabDialogAddBinding? = null
    private val binding get() = _binding!!

    private val dayData = ArrayList<DayMemo>()
    private val dayAdapter: DayAdapter by lazy {
        DayAdapter(requireContext(), dayData)
    }
    private val dayViewModel: DayViewModel by lazy {
        ViewModelProvider(requireActivity())[DayViewModel::class.java]
    }
    private val listViewModel: ListViewModel by lazy {
        ViewModelProvider(requireActivity())[ListViewModel::class.java]
    }

    private var dayId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //false로 설정해주면 화면밖 or 뒤로가기버튼 클릭시 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onStart() {
        super.onStart()

        // 단어장추가 다이얼로그에 추가 버튼 설정
        val dialog = dialog as AlertDialog
        dialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val getEnglish = binding.editTextEnglish.text.toString()
                val getKorean = binding.editTextKorean.text.toString()

                if (getEnglish.isEmpty() || getKorean.isEmpty()) {
                    Toast.makeText(context, "값을 입력하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val listMemo = ListMemo(0, getEnglish, getKorean, dayId)
                    listViewModel.insertList(listMemo) {}
                    dismiss()
                }
            }

        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentFabDialogAddBinding.inflate(layoutInflater)

        dayViewModel.loadAllDay().observe(requireActivity(), Observer {
            dayData.clear()
            dayData.addAll(it)
            dayAdapter.notifyDataSetChanged()
        })

        // 단어장추가 다이얼로그 내 DAY추가 다이얼로그 버튼 설정
        binding.dayButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())

            builder.setTitle("DAY 추가")
                .setPositiveButton("추가") { dialogInterface: DialogInterface, i: Int -> }
                .setNegativeButton("닫기") { dialogInterface: DialogInterface, i: Int -> }
                .setCancelable(false)
                .setAdapter(dayAdapter) { dialogInterface: DialogInterface, i: Int ->
                    dayId = dayData[i].id
                    binding.dayButton.text = "Day ${dayId}"
                }

            val dialog = builder.create()
            dialog.show()

            // setPositiveButton에서 기능설정하면 추가버튼 눌렀을 때 DAY추가 다이얼로그가 dismiss되기 때문에 여기서 설정한다.
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val calendar = Calendar.getInstance()
                    val year = calendar.get(Calendar.YEAR)
                    val month = calendar.get(Calendar.MONTH)
                    val day = calendar.get(Calendar.DAY_OF_MONTH)

                    // 날짜 선택할 수 있는 달력 다이얼로그 객체 생성
                    val datePicker = DatePickerDialog(requireContext(), object : DatePickerDialog.OnDateSetListener {
                        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                            val dayMemo = DayMemo(0, "${year}년 ${month + 1}월 ${dayOfMonth}일")

                            dayViewModel.insertDay(dayMemo) {}
                        }
                    }, year, month, day)

                    datePicker.show()
                }
            })

            // DAY추가 다이얼로그 리스트 중 LongClick시 삭제 기능 설정
            dialog.listView.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener {
                override fun onItemLongClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long): Boolean {
                    val builder = AlertDialog.Builder(requireContext())

                    builder.setTitle("DAY 삭제")
                        .setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("삭제") { dialogInterface: DialogInterface, i: Int ->
                            dayViewModel.deleteDay(dayData[position]) {}
                        }
                        .setNegativeButton("닫기") { dialogInterface: DialogInterface, i: Int -> }
                        .setCancelable(false)
                        .show()

                    // LongClickListener만 적용시키고 싶으면 true, 다른 Listener도 중복 적용시키고 싶으면 false
                    return true
                }
            })
        }

        // 단어장추가 다이얼로그 설정
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setView(binding.root)
                .setTitle("단어장 추가")
                .setIcon(R.drawable.ic_list_white)
                .setPositiveButton("추가") { dialogInterface: DialogInterface, i: Int -> }
                .setNegativeButton("닫기") { dialogInterface: DialogInterface, i: Int -> }
                .create()
        }?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}