package by.it.academy.homework_7_rx_java

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Environment
import android.widget.ImageView
import android.widget.TextView
import java.io.File
import java.io.FileOutputStream

fun saveImage(photo: Bitmap, iv: ImageView, carPictureDirectory: File): String {
    val path = "photo_${System.currentTimeMillis()}.jpg"
    val pathToPicture = "${carPictureDirectory.path}/${path}"
    val file = File(carPictureDirectory, path)
    file.createNewFile()
    val stream = FileOutputStream(file)
    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    iv.setImageBitmap(photo)
    stream.flush()
    stream.close()
    return pathToPicture
}

fun setImageStatus(status: String, resources: Resources, iv: ImageView, tv: TextView) {
    when (status) {
        resources.getString(R.string.work_pending) -> {
            iv.setImageResource(R.drawable.ic_repair_pending)
            tv.setTextColor(resources.getColor(R.color.work_status_pending))
        }
        resources.getString(R.string.work_in_progress) -> {
            iv.setImageResource(R.drawable.ic_repair_in_progress)
            tv.setTextColor(resources.getColor(R.color.work_status_in_progress))
        }
        resources.getString(R.string.work_completed) -> {
            iv.setImageResource(R.drawable.ic_repair_completed)
            tv.setTextColor(resources.getColor(R.color.work_status_completed))
        }
    }
}

fun createDirectory(context: Context): File? {
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        val carPictureDirectory = File("${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/CarPictures")
        if (!carPictureDirectory.exists()) {
            carPictureDirectory.mkdir()
        }
        return carPictureDirectory
    }
    return null
}