package com.careerproject.lifelog.ui.main.todo.previous

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.careerproject.lifelog.data.Sns
import com.careerproject.lifelog.databinding.FragmentCheckBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class CheckFragment : Fragment() {
    private var _binding: FragmentCheckBinding? = null
    private val binding get() = _binding!!

    var PICK_IMAGE_FROM_ALBUM = 0
    val db = FirebaseFirestore.getInstance()
    var store: FirebaseFirestore? = null
    var auth: FirebaseAuth? = null
    var photoUrl: Uri? = null

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckBinding.inflate(inflater, container, false)

        //Initiate storage
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()

        binding.addImage.setOnClickListener { openAlbum() }
        binding.addBtn.setOnClickListener { dataUpload() }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var addPhotoImage = binding.addImage
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == PICK_IMAGE_FROM_ALBUM){
            if(resultCode == AppCompatActivity.RESULT_OK){
                photoUrl = data?.data
                addPhotoImage.setImageURI(photoUrl)
            }
            else{
                activity?.finish()
            }
        }
    }

    private fun openAlbum(){
        //Open the album
        var photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, PICK_IMAGE_FROM_ALBUM)
    }

    @SuppressLint("SimpleDateFormat")
    private fun dataUpload() {
        val storage: FirebaseStorage? = FirebaseStorage.getInstance()
        var fbstore : FirebaseFirestore? = FirebaseFirestore.getInstance()

        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMAGE_" + timestamp + "_.png"
        val stoargeRef = storage?.reference?.child("images")?.child(imageFileName)

        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val userInfo = db.collection(email).document("userinfo")
        var nickname : String

            stoargeRef?.putFile(photoUrl!!)?.addOnSuccessListener {
                stoargeRef.downloadUrl.addOnSuccessListener { uri ->
                    userInfo.get().addOnSuccessListener {
                        nickname = it.get("nickname").toString()
                        val sns = Sns(
                            nickname,
                            timestamp,
                            binding.commentEditText.text.toString(),
                            binding.esgEditText.text.toString(),
                            photoUrl.toString()
                        )

                        db.collection("post")
                            .add(sns)
                            .addOnSuccessListener { documentReference ->
                                Log.d(ContentValues.TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener{ e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                            }
                    }
                }
            }


    }
}