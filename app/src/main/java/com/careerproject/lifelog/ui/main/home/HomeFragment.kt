package com.careerproject.lifelog.ui.main.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.careerproject.lifelog.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var firestore : FirebaseFirestore? = null
    private var uid : String? = null

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        uid = FirebaseAuth.getInstance().currentUser?.uid
        firestore = FirebaseFirestore.getInstance()

        // 과거 내용 (SNS 형식)
        binding.snsRecyclerview.adapter = HomePreviousRecyclerAdapter()
        binding.snsRecyclerview.layoutManager = LinearLayoutManager(activity)

        // 미래 내용 (투두 형식)
        binding.todoRecyclerview.adapter = HomeNextRecyclerAdapter()
        binding.todoRecyclerview.layoutManager = LinearLayoutManager(activity)

        return binding.root
    }
}