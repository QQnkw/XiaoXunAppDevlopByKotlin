package com.xxun.watch.picture_translate.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.xxun.watch.picture_translate.bean.Word
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
                /*Glide.with(this@PhotoPreviewFragment).load(str)
                    .into(binding.viewImageShowPhoto)*/
                val bitmap = BitmapFactory.decodeFile(str)
                LogUtils.d("${bitmap.width}---${bitmap.height}")
                binding.viewImageShowPhoto.setImageBitmap(bitmap)
                viewModel.identityPicture(str)
            }
        }
        binding.viewActionClosePreview.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.textInfoLiveData.observe(viewLifecycleOwner, Observer {
            binding.viewLoading.visibility = View.GONE
            createTextScopeView(it)
        })
    }

    private fun createTextScopeView(list: List<Word>) {
        list.forEach {
            it.coord
            val view = View(context)
            val layoutParams = FrameLayout.LayoutParams(30, 30)
            layoutParams.topMargin = 10
            layoutParams.leftMargin = 10
            /*view.tag = index
            binding.viewGroupClick.addView(view)
            view.setOnClickListener(onClickListener)*/
        }
    }
}
