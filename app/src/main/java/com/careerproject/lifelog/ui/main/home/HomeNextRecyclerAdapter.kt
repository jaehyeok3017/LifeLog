package com.careerproject.lifelog.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.careerproject.lifelog.data.Sns
import com.careerproject.lifelog.data.Todo
import com.careerproject.lifelog.databinding.ItemHomeNextBinding
import com.google.firebase.firestore.FirebaseFirestore

class HomeNextRecyclerAdapter(): RecyclerView.Adapter<HomeNextRecyclerAdapter.ViewHolder>() {
    private val todo: ArrayList<Todo> = arrayListOf()
    private var firestore : FirebaseFirestore? = FirebaseFirestore.getInstance()
    private var uid : String? = null

    init {
        firestore?.collection("todo")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            todo.clear()
            if (querySnapshot == null) return@addSnapshotListener

            for (snapshot in querySnapshot!!.documents) {
                var item = snapshot.toObject(Todo::class.java)
                todo.add(item!!)
            }
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHomeNextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemHomeNextBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Todo){
            binding.esg.text = data.esg
            binding.nickname.text = data.nickname
            binding.time.text = data.timestamp
            binding.comment.text = data.todo
        }
    }

    override fun getItemCount(): Int {
        return todo.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todo[position])
    }

}