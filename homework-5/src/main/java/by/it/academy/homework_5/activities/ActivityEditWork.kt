package by.it.academy.homework_5.activities

import android.annotation.SuppressLint
import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import by.it.academy.homework_5.R
import by.it.academy.homework_5.data.WorkInfo
import by.it.academy.homework_5.database.DataBaseCarInfo
import by.it.academy.homework_5.database.WorkInfoDAO
import by.it.academy.homework_5.setImageStatus

private const val RESULT_CODE_BUTTON_BACK = 6
private const val RESULT_CODE_BUTTON_REMOVE = 7

class ActivityEditWork : AppCompatActivity() {
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
    private lateinit var imgButtonRemove: ImageButton
    private lateinit var tvPending: TextView
    private lateinit var tvInProgress: TextView
    private lateinit var tvCompleted: TextView
    private lateinit var tvCurrentWorkName: TextView
    private lateinit var checkedStatus: String
    private var currentCarId: Long = 0
    private lateinit var currentWorkInfo: WorkInfo
    private lateinit var database: DataBaseCarInfo
    private lateinit var workInfoDAO: WorkInfoDAO

//    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_work)
        toolbar = findViewById(R.id.toolbar_edit_work)
        textData = findViewById(R.id.application_date0)
        etWorkName = findViewById(R.id.work_name_edit_text0)
        etWorkDescription = findViewById(R.id.description_edit_text0)
        etWorkCost = findViewById(R.id.work_cost_edit_text0)
        ivPending = findViewById(R.id.image_pending0)
        ivInProgress = findViewById(R.id.image_in_progress0)
        ivCompleted = findViewById(R.id.image_completed0)
        imgButtonApply = findViewById(R.id.save_edit_work)
        imgButtonBack = findViewById(R.id.arrow_back_edit_work)
        imgButtonRemove = findViewById(R.id.delete_edit_work)
        tvPending = findViewById(R.id.text_pending0)
        tvInProgress = findViewById(R.id.text_in_progress0)
        tvCompleted = findViewById(R.id.text_completed0)
        tvCurrentWorkName = findViewById(R.id.work_name_edit_work)
        database = DataBaseCarInfo.getDataBase(applicationContext)
        workInfoDAO = database.getWorkInfoDAO()
        setSupportActionBar(toolbar)
        setImageListeners()
        setButtonsListeners()
        loadDataFromIntent()
    }

    private fun loadDataFromIntent() {
        if (intent != null) {
            currentWorkInfo = intent.getParcelableExtra("workInfo")
                    ?: WorkInfo()
            currentCarId = currentWorkInfo.carInfoId
            val date = currentWorkInfo.date
            textData.text = "${resources.getString(R.string.application_date)} - $date"
            etWorkName.setText(currentWorkInfo.workName)
            etWorkDescription.setText(currentWorkInfo.description)
            etWorkCost.setText(currentWorkInfo.cost)
            setIconStatus(currentWorkInfo.status)
            tvCurrentWorkName.text = currentWorkInfo.workName
        }
    }

    private fun setIconStatus(status: String?) {
        when (status) {
            resources.getString(R.string.work_pending) -> {
                setImageStatus(status, resources, ivPending, tvPending)
                checkedStatus = resources.getString(R.string.work_pending)
            }
            resources.getString(R.string.work_in_progress) -> {
                setImageStatus(status, resources, ivInProgress, tvInProgress)
                checkedStatus = resources.getString(R.string.work_in_progress)
            }
            resources.getString(R.string.work_completed) -> {
                setImageStatus(status, resources, ivCompleted, tvCompleted)
                checkedStatus = resources.getString(R.string.work_completed)
            }
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
        imgButtonRemove.setOnClickListener {
            removeWorkAndBackToPreviousActivity()
        }
    }

    private fun backToPreviousActivity() {
        setResult(RESULT_CODE_BUTTON_BACK, intent)
        finish()
    }

    private fun removeWorkAndBackToPreviousActivity() {
        createDialog()
    }

    private fun editWorkAndBackToPreviousActivity() {
        val workName = etWorkName.text.toString()
        val workDescription = etWorkDescription.text.toString()
        val workCost = etWorkCost.text.toString()
        if (workName.isNotEmpty() && workDescription.isNotEmpty() && workCost.isNotEmpty()) {
            val workInfo = WorkInfo(currentWorkInfo.date, workName, workDescription, workCost, checkedStatus, currentCarId).also { it.id = currentWorkInfo.id }
            workInfoDAO.update(workInfo)
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Fields can't be empty", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createDialog() {
        AlertDialog.Builder(this)
                .setTitle(getString(R.string.remove_work))
                .setMessage(getString(R.string.warning))
                .setPositiveButton("Apply"
                ) { dialogInterface, i ->
                    workInfoDAO.delete(currentWorkInfo)
                    setResult(RESULT_CODE_BUTTON_REMOVE)
                    finish()
                }
                .setNegativeButton("Cancel") { dialogInterface, i -> dialogInterface.cancel() }
                .setCancelable(false)
                .create()
                .show()
    }
}