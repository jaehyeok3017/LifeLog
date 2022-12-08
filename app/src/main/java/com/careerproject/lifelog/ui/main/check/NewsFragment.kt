package com.careerproject.lifelog.ui.main.check

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.careerproject.lifelog.databinding.FragmentHomeBinding
import com.careerproject.lifelog.databinding.FragmentNewsBinding
import com.careerproject.lifelog.ui.main.home.HomeNextRecyclerAdapter
import com.careerproject.lifelog.ui.main.home.HomePreviousRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private var firestore : FirebaseFirestore? = null
    private var uid : String? = null

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        return binding.root
    }
}