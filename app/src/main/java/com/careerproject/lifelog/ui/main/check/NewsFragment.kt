package com.careerproject.lifelog.ui.main.check

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.careerproject.lifelog.data.news.Data
import com.careerproject.lifelog.data.news.NewsData
import com.careerproject.lifelog.databinding.FragmentNewsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : NewsAdapter
    private lateinit var recyclerView: RecyclerView

    private val token = "ZHbzgvx/R19R31NwnbHvRgIVKo6J3lpY1UH7l2jHz8BqVgYYjFOY+OJphQE/pWNFLI3Fu9QEV2cJE64vBorxGA=="
    companion object {
        private val retrofitClient: NewsFragment = NewsFragment()

        fun getInstance(): NewsFragment {
            return retrofitClient
        }
    }

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)

        val newsRetrofit = NewsRetrofitClient.getApiService().getNewsData(token)
        newsRetrofit.enqueue(object: Callback<NewsData> {
            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Log.d(ContentValues.TAG, t.message!!)
            }

            override fun onResponse(
                call: Call<NewsData>,
                response: Response<NewsData>
            ) {
                try{
                    val newsResult = response.body()?.data!!
                    adapter = context?.let { NewsAdapter(newsResult as ArrayList<Data>) }!!

                    recyclerView = binding.recycler
                    recyclerView.adapter = adapter
                } catch(e : Exception){
                    e.printStackTrace();
                    Toast.makeText(activity, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return binding.root
    }
}