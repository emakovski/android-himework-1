package by.it.academy.homework_7_rx_java.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.it.academy.homework_7_rx_java.DataBaseRepository
import by.it.academy.homework_7_rx_java.R
import by.it.academy.homework_7_rx_java.data.CarInfo
import by.it.academy.homework_7_rx_java.saveImage
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

private const val REQUEST_CODE_PHOTO = 1
private const val RESULT_CODE_BUTTON_BACK = 5

class ActivityAddCar : AppCompatActivity() {

    private lateinit var textName: EditText
    private lateinit var textProducer: EditText
    private lateinit var textModel: EditText
    private lateinit var textPlate: EditText
    private lateinit var imgButtonBack: ImageButton
    private lateinit var imgButtonApply: ImageButton
    private lateinit var fab: FloatingActionButton
    private lateinit var carPhoto: ImageView
    private lateinit var noCarPhoto: TextView
    private lateinit var toolbar: Toolbar
    private var photoWasLoaded: Boolean = false
    private lateinit var carPictureDirectory: File
    private lateinit var pathToPicture: String
    private lateinit var repository: DataBaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        toolbar = findViewById(R.id.toolbar_add_car)
        carPhoto = findViewById(R.id.image_car_photo)
        textName = findViewById(R.id.edit_text_owner_name)
        textProducer = findViewById(R.id.edit_text_producer_add_car)
        textModel = findViewById(R.id.edit_text_model_add_car)
        textPlate = findViewById(R.id.edit_view_plate_number_add_car)
        imgButtonBack = findViewById(R.id.arrow_back_add_car)
        imgButtonApply = findViewById(R.id.save_add_car)
        fab = findViewById(R.id.fab_load_photo)
        noCarPhoto = findViewById(R.id.no_photo)
        repository = DataBaseRepository()
        createDirectoryForPictures()
        setSupportActionBar(toolbar)
        setListeners()
    }

    private fun setListeners() {
        imgButtonBack.setOnClickListener {
            backToPreviousActivity()
        }
        imgButtonApply.setOnClickListener {
            addCarInfoAndBackToPreviousActivity()
        }
        fab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
    }

    private fun addCarInfoAndBackToPreviousActivity() {
        if (!photoWasLoaded) {
            pathToPicture = ""
        }
        val name = textName.text.toString()
        val producer = textProducer.text.toString()
        val model = textModel.text.toString()
        val plate = textPlate.text.toString()
        if (name.isNotEmpty() && producer.isNotEmpty() && model.isNotEmpty() && plate.isNotEmpty()) {
            val carInfo = CarInfo(pathToPicture, name, producer, model, plate)
            repository.addCar(carInfo).subscribe {
                setResult(Activity.RESULT_OK)
                finish()
            }
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.extras != null) {
                if (data.extras?.get("data") != null) {
                    val photo = data.extras?.get("data") as Bitmap?
                    if (photo != null) {
                        pathToPicture = saveImage(photo, carPhoto, carPictureDirectory)
                        photoWasLoaded = true
                        noCarPhoto.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun createDirectoryForPictures() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            carPictureDirectory = File("${getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/CarPictures")
            if (!carPictureDirectory.exists()) {
                carPictureDirectory.mkdir()
            }
        }
    }
}