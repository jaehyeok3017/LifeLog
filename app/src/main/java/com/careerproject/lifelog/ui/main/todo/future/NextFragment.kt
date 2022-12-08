package com.careerproject.lifelog.ui.main.todo.future

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.careerproject.lifelog.data.Todo
import com.careerproject.lifelog.databinding.FragmentNextBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class NextFragment : Fragment() {
    private var _binding: FragmentNextBinding? = null
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()
    var store: FirebaseFirestore? = null
    var auth: FirebaseAuth? = null

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNextBinding.inflate(inflater, container, false)

        //Initiate storage
        auth = FirebaseAuth.getInstance()
        store = FirebaseFirestore.getInstance()

        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val userInfo = db.collection(email).document("userinfo")
        var nickname : String

        binding.addBtn.setOnClickListener {
            val comment = binding.commentEditText
            val esg = binding.esgEditText

            userInfo.get().addOnSuccessListener {
                nickname = it.get("nickname").toString()
                when(nullCheck(comment, esg)) {
                    true -> dataUpload(nickname, comment, esg)
                    else -> {
                        Toast.makeText(activity, "입력되지 않은 칸이 존재합니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return binding.root
    }

    private fun nullCheck(comment : EditText?, esg : EditText?) : Boolean {
        return !(comment?.text.isNullOrEmpty() || esg?.text.isNullOrEmpty())
    }

    @SuppressLint("SimpleDateFormat")
    private fun dataUpload(nickname : String, comment : EditText, esg : EditText) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

        val todo = Todo(
            nickname,
            timestamp,
            comment.text.toString(),
            esg.text.toString()
        )

        db.collection("todo")
            .add(todo)
    }
}