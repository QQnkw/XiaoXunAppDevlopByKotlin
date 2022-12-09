package com.xxun.watch.picture_translate.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.xxun.watch.picture_translate.R
import com.xxun.watch.picture_translate.camera.CameraView
import com.xxun.watch.picture_translate.databinding.FragmentMainBinding
import com.xxun.watch.picture_translate.view_model.MainViewModel
import kotlinx.coroutines.flow.Flow

class MainFragment() : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val TAG = "MainActivity"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root;
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
                Log.d(TAG, "onCameraOpened")
            }

            override fun onCameraClosed(cameraView: CameraView?) {
                super.onCameraClosed(cameraView)
                Log.d(TAG, "onCameraClosed")
            }

            override fun onPictureTaken(cameraView: CameraView?, data: ByteArray?) {
                super.onPictureTaken(cameraView, data)
                Toast.makeText(context, "拍照成功", Toast.LENGTH_SHORT)
                    .show()
                Glide.with(this@MainFragment).load(data).into(binding.viewImageShowPhoto)
//                findNavController().navigate(R.id.action_mainFragment_to_photoPreviewFragment)
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