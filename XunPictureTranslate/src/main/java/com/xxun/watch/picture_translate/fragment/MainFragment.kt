package com.xxun.watch.picture_translate.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.os.FileUtils
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.*
import com.xxun.watch.picture_translate.R
import com.xxun.watch.picture_translate.camera.CameraView
import com.xxun.watch.picture_translate.databinding.FragmentMainBinding
import com.xxun.watch.picture_translate.view_model.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.lang.ref.SoftReference


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.actionTakePhoto.setOnClickListener {
            binding.camera.takePicture()
        }
        binding.camera.addCallback(object : CameraView.Callback() {
            override fun onCameraOpened(cameraView: CameraView?) {
                super.onCameraOpened(cameraView)
                LogUtils.d("onCameraOpened")
            }

            override fun onCameraClosed(cameraView: CameraView?) {
                super.onCameraClosed(cameraView)
                LogUtils.d("onCameraClosed")
            }

            override fun onPictureTaken(cameraView: CameraView?, data: ByteArray?) {
                super.onPictureTaken(cameraView, data)
                binding.actionTakePhoto.isEnabled = false
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    val result = viewModel.saveBitmapByteArrToFile(data)
                    if (result) {
                        withContext(Dispatchers.Main) {
                            findNavController().navigate(R.id.action_mainFragment_to_photoPreviewFragment,
                                bundleOf().apply {
                                    putString("ImagePath", viewModel.createImagePath())
                                })
                        }
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        binding.camera.start()
    }

    override fun onPause() {
        super.onPause()
        binding.camera.stop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}