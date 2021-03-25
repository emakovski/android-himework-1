package by.it.academy.homework_7_rx_java.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import by.it.academy.homework_7_rx_java.R
import by.it.academy.homework_7_rx_java.createDirectory
import by.it.academy.homework_7_rx_java.data.CarInfo
import by.it.academy.homework_7_rx_java.database.CarInfoDAO
import by.it.academy.homework_7_rx_java.database.DataBaseCarInfo
import by.it.academy.homework_7_rx_java.saveImage
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

private const val REQUEST_CODE_PHOTO = 1
private const val RESULT_CODE_BUTTON_BACK = 5

class ActivityEditCar : AppCompatActivity() {
    private var carId: Long = 0

    private lateinit var textName: EditText
    private lateinit var textProducer: EditText
    private lateinit var textModel: EditText
    private lateinit var textPlate: EditText
    private lateinit var imgButtonBack: ImageButton
    private lateinit var imgButtonApply: ImageButton
    private lateinit var fab: FloatingActionButton
    private lateinit var carPhoto: ImageView
    private lateinit var toolbar: Toolbar
    private var photoWasLoaded: Boolean = false
    private lateinit var pathToPicture: String
    private lateinit var carPictureDirectory: File
    private lateinit var currentCarInfo: CarInfo
    private lateinit var database: DataBaseCarInfo
    private lateinit var carInfoDAO: CarInfoDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_car)
        toolbar = findViewById(R.id.toolbar_edit_car)
        carPhoto = findViewById(R.id.image1)
        textName = findViewById(R.id.edit_text_owner_name_car_info)
        textProducer = findViewById(R.id.edit_text_producer_car_info)
        textModel = findViewById(R.id.edit_text_model_car_info)
        textPlate = findViewById(R.id.edit_view_plate_number_car_info)
        imgButtonBack = findViewById(R.id.arrow_back_edit_car)
        imgButtonApply = findViewById(R.id.save_edit_car)
        fab = findViewById(R.id.fab_edit_photo)
        database = DataBaseCarInfo.getDataBase(applicationContext)
        carInfoDAO = database.getCarInfoDAO()
        setSupportActionBar(toolbar)
        setListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        val carInfo = intent.getParcelableExtra<CarInfo>("carInfo")
        if (carInfo != null) {
            currentCarInfo = carInfo
            carId = carInfo.id
            val path = carInfo.pathToPicture
            val file = File(path)
            if (file.exists()) {
                if (path == "") {
                    carPhoto.setImageResource(R.drawable.default_image)
                } else {
                    Glide.with(this).load(path).into(carPhoto)
                    photoWasLoaded = true
                    pathToPicture = path
                }
            }
            textName.setText(carInfo.name)
            textProducer.setText(carInfo.producer)
            textModel.setText(carInfo.model)
            textPlate.setText(carInfo.plate)
        }
    }

    private fun setListeners() {
        imgButtonBack.setOnClickListener {
            backToPreviousActivity()
        }
        imgButtonApply.setOnClickListener {
            editCarInfoAndBackToPreviousActivity()
        }
        fab.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, REQUEST_CODE_PHOTO)
        }
    }

    private fun editCarInfoAndBackToPreviousActivity() {
        val name = textName.text.toString()
        val producer = textProducer.text.toString()
        val model = textModel.text.toString()
        val plate = textPlate.text.toString()
        if (name.isNotEmpty() && producer.isNotEmpty() && model.isNotEmpty() && plate.isNotEmpty()) {
            if (!photoWasLoaded) {
                pathToPicture = ""
            }
            val carInfo = CarInfo(pathToPicture, name, producer, model, plate).also { it.id = carId }
            carInfoDAO.update(carInfo)
            setResult(Activity.RESULT_OK)
            finish()
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
        data?.extras?.get("data")?.run {
            pathToPicture = saveImage(this as Bitmap, carPhoto, carPictureDirectory)
            photoWasLoaded = true
        }
    }

    private fun createDirectoryForPictures() {
        createDirectory(applicationContext)?.run {
            carPictureDirectory = this
        }
    }

    override fun onBackPressed() {
        setResult(RESULT_CODE_BUTTON_BACK)
        finish()
        super.onBackPressed()
    }
}