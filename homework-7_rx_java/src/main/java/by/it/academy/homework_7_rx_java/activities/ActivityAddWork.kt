package by.it.academy.homework_7_rx_java.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import by.it.academy.homework_7_rx_java.DataBaseRepository
import by.it.academy.homework_7_rx_java.R
import by.it.academy.homework_7_rx_java.data.WorkInfo
import java.text.SimpleDateFormat
import java.util.*

private const val RESULT_CODE_BUTTON_BACK = 6

class ActivityAddWork : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var textData: TextView
    private lateinit var etWorkName: EditText
    private lateinit var etWorkDescription: EditText
    private lateinit var etWorkCost: EditText
    private lateinit var ivPending: ImageView
    private lateinit var ivInProgress: ImageView
    private lateinit var ivCompleted: ImageView
    private lateinit var imgButtonBack: ImageButton
    private lateinit var imgButtonApply: ImageButton
    private lateinit var tvPending: TextView
    private lateinit var tvInProgress: TextView
    private lateinit var tvCompleted: TextView
    private lateinit var checkedStatus: String
    private lateinit var date: String
    private var currentCarId: Long = 0
    private lateinit var repository: DataBaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_work)
        toolbar = findViewById(R.id.toolbar_add_work)
        textData = findViewById(R.id.application_date)
        etWorkName = findViewById(R.id.work_name_edit_text)
        etWorkDescription = findViewById(R.id.description_edit_text)
        etWorkCost = findViewById(R.id.work_cost_edit_text)
        ivPending = findViewById(R.id.image_pending)
        ivInProgress = findViewById(R.id.image_in_progress)
        ivCompleted = findViewById(R.id.image_completed)
        imgButtonApply = findViewById(R.id.save_add_work)
        imgButtonBack = findViewById(R.id.arrow_back_add_work)
        tvPending = findViewById(R.id.text_pending)
        tvInProgress = findViewById(R.id.text_in_progress)
        tvCompleted = findViewById(R.id.text_completed)
        repository = DataBaseRepository()
        loadDataFromIntent()
        setSupportActionBar(toolbar)
        setImageListeners()
        setButtonsListeners()
        getCurrentDate()
        checkedStatus = resources.getString(R.string.work_pending)
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentDate() {
        val simpleDateFormat = SimpleDateFormat.getDateInstance()
        date = simpleDateFormat.format(Date())
        textData.text = "${resources.getString(R.string.application_date)} - $date"
    }

    private fun loadDataFromIntent() {
        if (intent != null) {
            currentCarId = intent.getLongExtra("currentCarId", 0)
        }
    }

    private fun setImageListeners() {
        ivPending.setOnClickListener {
            ivPending.setImageResource(R.drawable.ic_repair_pending)
            ivInProgress.setImageResource(R.drawable.ic_repair)
            ivCompleted.setImageResource(R.drawable.ic_repair)
            checkedStatus = resources.getString(R.string.work_pending)
            tvPending.setTextColor(resources.getColor(R.color.work_status_pending))
            tvInProgress.setTextColor(resources.getColor(R.color.gray))
            tvCompleted.setTextColor(resources.getColor(R.color.gray))
        }
        ivInProgress.setOnClickListener {
            ivPending.setImageResource(R.drawable.ic_repair)
            ivInProgress.setImageResource(R.drawable.ic_repair_in_progress)
            ivCompleted.setImageResource(R.drawable.ic_repair)
            checkedStatus = resources.getString(R.string.work_in_progress)
            tvPending.setTextColor(resources.getColor(R.color.gray))
            tvInProgress.setTextColor(resources.getColor(R.color.work_status_in_progress))
            tvCompleted.setTextColor(resources.getColor(R.color.gray))
        }
        ivCompleted.setOnClickListener {
            ivPending.setImageResource(R.drawable.ic_repair)
            ivInProgress.setImageResource(R.drawable.ic_repair)
            ivCompleted.setImageResource(R.drawable.ic_repair_completed)
            checkedStatus = resources.getString(R.string.work_completed)
            tvPending.setTextColor(resources.getColor(R.color.gray))
            tvInProgress.setTextColor(resources.getColor(R.color.gray))
            tvCompleted.setTextColor(resources.getColor(R.color.work_status_completed))
        }
    }

    private fun setButtonsListeners() {
        imgButtonBack.setOnClickListener {
            backToPreviousActivity()
        }
        imgButtonApply.setOnClickListener {
            editWorkAndBackToPreviousActivity()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK)
        finish()
    }

    private fun editWorkAndBackToPreviousActivity() {
        val workName = etWorkName.text.toString()
        val workDescription = etWorkDescription.text.toString()
        val workCost = etWorkCost.text.toString()
        if (workName.isNotEmpty() && workDescription.isNotEmpty() && workCost.isNotEmpty()) {
            repository.addWork(WorkInfo(date, workName, workDescription, workCost, checkedStatus, currentCarId))
                    .subscribe {
                        setResult(Activity.RESULT_OK)
                        finish()
                    }
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }
}