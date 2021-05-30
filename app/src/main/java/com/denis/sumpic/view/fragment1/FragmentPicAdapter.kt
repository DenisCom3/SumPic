package com.denis.sumpic.view.fragment1

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.denis.sumpic.R
import com.denis.sumpic.model.api.PicSumDataJson
import com.denis.sumpic.utils.APP_ACTIVITY
import com.denis.sumpic.utils.REQUEST_CODE
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import kotlin.coroutines.CoroutineContext

class FragmentPicAdapter(private var fragment: FragmentRecyclerPic, private var dataList: List<PicSumDataJson>): RecyclerView.Adapter<FragmentPicAdapter.ViewHolder>() {
    private lateinit var outputStream: OutputStream


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_row_main,parent,false))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        Glide.with(fragment)
            .load(data.download_url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imgView)
        holder.textView.text = data.author
        holder.likeView.setOnClickListener {

                    askPermission()
                    val dir = File(Environment.getExternalStorageDirectory(),"SaveImage1");

                    if (!dir.exists()){
                        dir.mkdir()
                    }

                    val drawable: BitmapDrawable = holder.imgView.drawable as BitmapDrawable
                    val bitmap = drawable.bitmap

                    val file = File(dir,"${System.currentTimeMillis()}.jpg")
                        outputStream = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)

                        outputStream.flush()
                        outputStream.close()
                        Toast.makeText(fragment.context,"Добавлено в Избранное", Toast.LENGTH_SHORT).show()
                        holder.likeView.setImageResource(R.drawable.ic_like_red)


        }
    }
    private fun askPermission(){
        val permissions = Array(1){ Manifest.permission.WRITE_EXTERNAL_STORAGE}
        ActivityCompat.requestPermissions(APP_ACTIVITY,permissions, REQUEST_CODE)
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textView: TextView = itemView.findViewById(R.id.text_view)
        var imgView: ImageView = itemView.findViewById(R.id.image_view)
        var likeView :ImageButton = itemView.findViewById(R.id.like)
    }
}
