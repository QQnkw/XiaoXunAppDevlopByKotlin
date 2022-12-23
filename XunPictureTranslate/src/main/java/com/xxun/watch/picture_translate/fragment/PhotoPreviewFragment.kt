package com.xxun.watch.picture_translate.fragment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.*
import com.xxun.watch.picture_translate.R
import com.xxun.watch.picture_translate.bean.Word
import com.xxun.watch.picture_translate.databinding.FragmentMainBinding
import com.xxun.watch.picture_translate.databinding.FragmentPhotoPreviewBinding
import com.xxun.watch.picture_translate.view_model.MainViewModel
import com.xxun.watch.picture_translate.view_model.PhotoPreviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PhotoPreviewFragment : Fragment() {
    private lateinit var viewModel: PhotoPreviewViewModel
    private var _binding: FragmentPhotoPreviewBinding? = null
    private val binding get() = _binding!!
    private var path: String? = null

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
            path = bundle.getString("ImagePath")
            path?.let { str ->
                val bitmap = BitmapFactory.decodeFile(str)
                binding.viewImageShowPhoto.setImageBitmap(bitmap)
                viewModel.identityPicture(str)
            }
        }
        binding.viewActionClosePreview.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.viewImageShowPhoto.setOnClickTextScopeListener {
            val text = viewModel.getClickText(it)
            findNavController().navigate(
                R.id.action_photoPreviewFragment_to_translateResultFragment,
                bundleOf().apply {
                    putString("text", text)
                })
        }
        viewModel.textInfoLiveData.observe(viewLifecycleOwner, Observer {
            binding.viewLoading.visibility = View.GONE
            if (it.isEmpty()) {
                ToastUtils.showLong("识别失败请水平对准文字")
            } else {
                binding.viewImageShowPhoto.setTextScopeData(it)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch(Dispatchers.IO) {
            FileUtils.delete(path)
        }
    }
}
