package com.careerproject.lifelog.ui

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.careerproject.lifelog.data.UserInfo
import com.careerproject.lifelog.databinding.ActivityRegisterBinding
import com.careerproject.lifelog.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var itemToString: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registercomplete.setOnClickListener {
            textInvisibleSet()
            val email = binding.registerEmail.text.toString()
            val pw = binding.registerPasswd.text.toString()
            val repeatPw = binding.registerRepeatPasswd.text.toString()
            val nickname = binding.nickname.text.toString()
            val name = binding.name.text.toString()

            if (registerNullableCheck(
                    binding.registerEmail, binding.registerPasswd,
                    binding.registerRepeatPasswd, binding.nickname, binding.name
                )
            ) {
                if (checkEmail(email)
                    && checkPasswd(pw)
                    && checkRepeatPasswd(pw, repeatPw)
                    && nickNameCheckFormat(nickname)
                ) {
                    createUser(email, pw, nickname, name)
                } else {
                    Toast.makeText(this, "??? ????????? ??????????????????", Toast.LENGTH_SHORT).show()
                }
            }
        else {
            Toast.makeText(this, "???????????? ?????? ?????? ???????????????.", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun textInvisibleSet() {
    binding.emailCheckText.visibility = View.INVISIBLE
    binding.passwdCheckText.visibility = View.INVISIBLE
    binding.repeatPasswdCheckText.visibility = View.INVISIBLE
    binding.nicknameCheckText.visibility = View.INVISIBLE
}

private fun registerNullableCheck(
    email: EditText?, passwd: EditText?,
    repeatPasswd: EditText?, nickname: EditText?, name: EditText?
): Boolean {
    return !(email?.text.isNullOrEmpty() || passwd?.text.isNullOrEmpty()
            || repeatPasswd?.text.isNullOrEmpty() || nickname?.text.isNullOrEmpty()
            || name?.text.isNullOrEmpty())
}

private fun checkEmail(email: String): Boolean {
    val emailFormatCheck =
        "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    return when (Pattern.matches(emailFormatCheck, email)) {
        true -> true
        false -> {
            binding.emailCheckText.visibility = View.VISIBLE
            false
        }
    }
}

private fun checkPasswd(passwd: String): Boolean {
    val passwdFormatCheck =
        "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&])[A-Za-z[0-9]\$@\$!%*#?&]{8,20}\$"

    return when (Pattern.matches(passwdFormatCheck, passwd)) {
        true -> true
        false -> {
            binding.passwdCheckText.visibility = View.VISIBLE
            false
        }
    }
}

@SuppressLint("SetTextI18n")
private fun checkRepeatPasswd(passwd: String, repeatPasswd: String): Boolean {
    binding.nicknameCheckText.visibility = View.INVISIBLE
    return when (passwd == repeatPasswd) {
        true -> true
        false -> {
            binding.nicknameCheckText.text = "????????????, ??????, ????????? ????????? 8~10????????? ???????????????"
            binding.repeatPasswdCheckText.visibility = View.VISIBLE
            false
        }
    }
}

private fun checkNickName(nickname: String): Boolean {
    var sameNickNameCheck = false

    for (element in itemToString) {
        if (element != nickname) {
            sameNickNameCheck = true
        } else {
            sameNickNameCheck = false
            break
        }
    }

    return when (sameNickNameCheck) {
        false -> {
            binding.nicknameCheckText.visibility = View.VISIBLE
            binding.nicknameCheckText.text = "?????? ???????????? ??????????????????."
            false
        }
        true -> {
            binding.nicknameCheckText.visibility = View.VISIBLE
            binding.nicknameCheckText.text = "????????? ??? ?????? ??????????????????."
            true
        }
    }
}

private fun nickNameCheckFormat(nickname: String): Boolean {
    val nicknameFormatCheck = "^[a-zA-Z0-9???-??????-???]{2,20}\$"

    return when (Pattern.matches(nicknameFormatCheck, nickname)) {
        true -> true
        false -> {
            binding.nicknameCheckText.visibility = View.VISIBLE
            false
        }
    }
}

@SuppressLint("SimpleDateFormat")
private fun createUser(email: String, pw: String, nickname: String, name: String) {
    auth?.createUserWithEmailAndPassword(
        email, pw
    )
        ?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                // 01. ?????? ????????? ???
                // ?????? ????????? ??? ?????? - ???????????? timestamp : title / place(address) / participant
                todoDataSetUpload(timestamp, nickname, email)

                // 02. ???????????? ????????????
                userInfoDataSetUpload(nickname, name, timestamp, email)

                moveMainPage(auth?.currentUser)
            } else {
                Toast.makeText(
                    this,
                    "????????? ?????????????????? ${task.exception?.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
}

private fun todoDataSetUpload(timestamp: String, nickname: String, email: String) {
    val todoDataSet =
        mutableMapOf(
            timestamp to
                    listOf(
                        timestamp,
                        nickname,
                        "LIFELOG??? ???????????????!"
                    )
        )

    // ???????????? ???????????? ??????
    val emails = auth?.currentUser?.email.toString()
    db.collection(emails).document("todo").set(
        todoDataSet
    )
        .addOnSuccessListener {
            Log.d("TodoDataSet", "TodoDataSetUpload Success")
        }
        .addOnFailureListener {
            Log.d("TodoDataSet", "TodoDataSetUpload Fail")
        }
}

private fun userInfoDataSetUpload(
    nickname: String,
    name: String,
    timestamp: String,
    email: String
) {
    val userDataSet = UserInfo(
        nickname,
        name,
        null
    )

    db.collection(email)
        .document("userinfo")
        .set(userDataSet)
        .addOnSuccessListener {
            Log.d(ContentValues.TAG, "USERINFO ???????????? ?????????????????????.")
        }
        .addOnFailureListener {
            Log.d(ContentValues.TAG, "USERINFO ?????? ???????????? ?????????????????????.")
        }
}


private fun moveMainPage(user: FirebaseUser?) {
    if (user != null) {
        Toast.makeText(this, "??????????????? ?????????????????????!", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
}