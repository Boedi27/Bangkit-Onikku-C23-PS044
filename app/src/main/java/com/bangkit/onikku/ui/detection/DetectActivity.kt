package com.bangkit.onikku.ui.detection

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.bangkit.onikku.databinding.ActivityDetectBinding
import com.bangkit.onikku.ui.recomendation.RecomendationActivity
import com.bangkit.onikku.utils.createCustomTempFile
import com.bangkit.onikku.utils.uriToFile
import com.bangkit.onikku.vmFactory.DetectViewModelFactory
import okhttp3.MultipartBody
import java.io.File

class DetectActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectBinding
    private lateinit var detectViewModel: DetectViewModel
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    //Permissions
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, "Tidak Mendapatkan Permissions", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        setupViewModel()
        setupAction()
    }

    private fun setupAction() {
        binding.btnCamera.setOnClickListener {
            startTakePhoto()
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnUpload.setOnClickListener {
            if (getFile!=null){
                loadingAnimation(true)
                val imageFile = getFile
                detectViewModel.makePrediction(imageFile!!)
                detectViewModel.predictionResult.observe(this, {result->
                    val prediction = result.prediction
                    if (prediction != null) {
                        // Tampilkan nilai prediksi
                        var hasilPrediksi = ""
                        when(prediction){
                            0 -> hasilPrediksi = "Buah"
                            1 -> hasilPrediksi = "Cangkang Telur"
                            2 -> hasilPrediksi = "Daun"
                            3 -> hasilPrediksi = "Ranting"
                            4 -> hasilPrediksi = "Sayur"
                            5 -> hasilPrediksi = "Tulang"
                        }
                        val intent = Intent(this@DetectActivity, RecomendationActivity::class.java)
                        intent.putExtra(RecomendationActivity.EXTRA_PREDICT, hasilPrediksi)
                        startActivity(intent)
                        finish()
                        loadingAnimation(false)
                    }
                })

            } else {
                Toast.makeText(this, "Please choose an Image", Toast.LENGTH_SHORT).show()
            }
           
        }
    }


    // Camera Section
    private val launcherIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val myFile = File(currentPhotoPath)

                myFile.let { file ->
                    getFile = file
                    binding.previewImage.setImageBitmap(BitmapFactory.decodeFile(file.path))
                }
            }
        }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                this@DetectActivity,
                "com.bangkit.onikku.ui.detection",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)
        }
    }

    // Gallery Section
    private val launcherIntentGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImg = result.data?.data as Uri
                selectedImg.let { uri ->
                    val myFile = uriToFile(uri, this@DetectActivity)
                    getFile = myFile
                    binding.previewImage.setImageURI(uri)
                }
            }
        }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    //Viewmodel section
    private fun setupViewModel() {
        val factory = DetectViewModelFactory.getInstance(this)
        detectViewModel = ViewModelProvider(this, factory)[DetectViewModel::class.java]

    }

    private fun loadingAnimation(state: Boolean) {
        if (state) {
            binding.previewImage.visibility = View.GONE
            binding.btnGallery.visibility = View.GONE
            binding.btnUpload.visibility = View.GONE
            binding.btnCamera.visibility = View.GONE
            binding.animationLoading.visibility = View.VISIBLE
            binding.tvLoadingPrediction.visibility = View.VISIBLE
        } else {
            binding.previewImage.visibility = View.VISIBLE
            binding.btnGallery.visibility = View.VISIBLE
            binding.btnUpload.visibility = View.VISIBLE
            binding.btnCamera.visibility = View.VISIBLE
            binding.animationLoading.visibility = View.GONE
            binding.tvLoadingPrediction.visibility = View.GONE
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}