package com.careerproject.lifelog.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.careerproject.lifelog.data.Sns
import com.careerproject.lifelog.databinding.ItemHomePreviousBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomePreviousRecyclerAdapter(): RecyclerView.Adapter<HomePreviousRecyclerAdapter.ViewHolder>() {
    private val sns: ArrayList<Sns> = arrayListOf()
    private var firestore : FirebaseFirestore? = FirebaseFirestore.getInstance()
    private var uid : String? = null

    init {
        firestore?.collection("post")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            sns.clear()
            if (querySnapshot == null) return@addSnapshotListener

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(Sns::class.java)
                sns.add(item!!)
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomePreviousBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemHomePreviousBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Sns){
            binding.esg.text = data.esg
            binding.comment.text = data.todo
            binding.time.text = data.timestamp
            binding.nickname.text = data.nickname

            Glide.with(itemView)
                .load(data.imageUrl).into(binding.image)
        }
    }

    override fun getItemCount(): Int {
        return sns.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sns[position])
    }

}