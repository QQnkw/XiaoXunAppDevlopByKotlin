package com.xxun.watch.picture_translate.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.xxun.watch.picture_translate.R
import com.xxun.watch.picture_translate.adapter.TranslateResultAdapter
import com.xxun.watch.picture_translate.databinding.FragmentMainBinding
import com.xxun.watch.picture_translate.databinding.FragmentTranslateResultBinding
import com.xxun.watch.picture_translate.view_model.TranslateResultViewModel
import com.xxun.watch.picture_translate.weight.SelectWordTextView

class TranslateResultFragment : Fragment() {

    private lateinit var viewModel: TranslateResultViewModel
    private var _binding: FragmentTranslateResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTranslateResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TranslateResultViewModel::class.java]
        binding.root.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = TranslateResultAdapter(listOf("1", "2", "3", "4")) { voice, text ->
                if (!TextUtils.isEmpty(text)) {
                    ToastUtils.showLong(text)
                    return@TranslateResultAdapter
                }

                if (!TextUtils.isEmpty(voice)) {
                    ToastUtils.showLong(voice)
                    return@TranslateResultAdapter
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}