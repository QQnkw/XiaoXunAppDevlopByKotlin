package com.xxun.watch.picture_translate.fragment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ImageUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.xxun.watch.picture_translate.databinding.FragmentMainBinding
import com.xxun.watch.picture_translate.databinding.FragmentPhotoPreviewBinding
import com.xxun.watch.picture_translate.view_model.MainViewModel
import com.xxun.watch.picture_translate.view_model.PhotoPreviewViewModel

class PhotoPreviewFragment : Fragment() {
    private lateinit var viewModel: PhotoPreviewViewModel
    private var _binding: FragmentPhotoPreviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PhotoPreviewViewModel::class.java]
        arguments?.let { bundle ->
            val path = bundle.getString("ImagePath")
            path?.let { str ->
                Glide.with(this@PhotoPreviewFragment).load(str)
                    .into(binding.viewImageShowPhoto)
                viewModel.identityPicture(str)
            }
        }
        binding.viewActionClosePreview.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.createView(requireContext(),binding.viewGroupClick, listOf()){
            val index = it.tag

        }
    }
}
