package com.denis.sumpic.view.fragment1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import com.denis.sumpic.databinding.FragmentRecyclerPicBinding
import com.denis.sumpic.model.api.PicSumApi
import com.denis.sumpic.model.api.PicSumDataJson
import com.denis.sumpic.utils.BASE_URL
import com.denis.sumpic.utils.limit
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory


class FragmentRecyclerPic : Fragment() {

    private var _binding: FragmentRecyclerPicBinding? = null
    private val binding get() = _binding!!

    var dataList: ArrayList<PicSumDataJson> = ArrayList()
    lateinit var adapter: FragmentPicAdapter

    var page = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecyclerPicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter = FragmentPicAdapter(fragment = this, dataList = dataList)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        getData(page, limit)

        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight){
                page++
                binding.progressBar.visibility = View.VISIBLE
                getData(page,limit)

            }
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData(page: Int, limit: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(PicSumApi::class.java)

        val call = retrofit.getJsonData(page, limit)
        call.enqueue(object : Callback<List<PicSumDataJson>> {
            override fun onResponse(
                call: Call<List<PicSumDataJson>>,
                response: Response<List<PicSumDataJson>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    binding.progressBar.visibility = View.GONE

                    val dataListTest: List<PicSumDataJson> = response.body()!!

                    for (data in dataListTest) {
                        val responseTest = data.download_url
                        dataList.add(data)
                        adapter = FragmentPicAdapter(this@FragmentRecyclerPic, dataList)
                        binding.recyclerView.adapter = adapter
                        Log.e("TaG", responseTest)
                    }
                }
            }

            override fun onFailure(call: Call<List<PicSumDataJson>>, t: Throwable) {
                Toast.makeText(context, "Скорее всего, у вас нету подключения к интернету",
                    Toast.LENGTH_SHORT).show()
            }


        })
    }
}