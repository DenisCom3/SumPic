package com.denis.sumpic.view.fragment2

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.denis.sumpic.databinding.FragmentFavoritesPicBinding
import java.io.File


class FragmentFavoritesPic : Fragment() {
    private var _binding: FragmentFavoritesPicBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: GridAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesPicBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        adapter = GridAdapter(this.requireContext(),getData())
        binding.gridView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getData(): ArrayList<UriImage>{
        val uriList = ArrayList<UriImage>()

        val downloadsFolder = File(Environment.getExternalStorageDirectory(),"SaveImage1");
        var uriImage: UriImage

        if (downloadsFolder.exists()){
            val files = downloadsFolder.listFiles()

            for (file in files){
                uriImage = UriImage(Uri.fromFile(file))
                uriList.add(uriImage)
            }
        }
        return uriList

    }


}