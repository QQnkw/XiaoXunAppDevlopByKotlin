package com.xxun.watch.picture_translate.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xxun.watch.picture_translate.R
import com.xxun.watch.picture_translate.databinding.FragmentMainBinding
import com.xxun.watch.picture_translate.databinding.FragmentTranslateResultBinding
import com.xxun.watch.picture_translate.view_model.TranslateResultViewModel

class TranslateResultFragment : Fragment() {

    companion object {
        fun newInstance() = TranslateResultFragment()
    }

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}