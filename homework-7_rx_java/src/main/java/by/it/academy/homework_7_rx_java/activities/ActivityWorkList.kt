package by.it.academy.homework_7_rx_java.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.homework_7_rx_java.DataBaseRepository
import by.it.academy.homework_7_rx_java.R
import by.it.academy.homework_7_rx_java.WorkInfoAdapter
import by.it.academy.homework_7_rx_java.data.CarInfo
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ADD_WORK_ACTIVITY_CODE = 3
private const val EDIT_WORK_ACTIVITY_CODE = 4
private const val RESULT_CODE_BUTTON_BACK = 6

class ActivityWorkList : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var fab: FloatingActionButton
    private lateinit var imgButtonBack: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WorkInfoAdapter
    private lateinit var textCarName: TextView
    private lateinit var textCarModel: TextView
    private lateinit var textCarPlate: TextView
    private lateinit var noWorksAddedText: TextView
    private lateinit var currentCar: CarInfo
    private var currentCarId: Long = 0
    private lateinit var searchView: SearchView
    private lateinit var repository: DataBaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_list)
        toolbar = findViewById(R.id.toolbar_work_list)
        fab = findViewById(R.id.addWorkFab)
        imgButtonBack = findViewById(R.id.arrow_back_works_list)
        recyclerView = findViewById(R.id.workListRecycler)
        textCarName = findViewById(R.id.car_producer_works_list_toolbar)
        textCarModel = findViewById(R.id.car_model_works_list_toolbar)
        textCarPlate = findViewById(R.id.car_plate_works_list_toolbar)
        noWorksAddedText = findViewById(R.id.emptyWorkList_text)
        setSupportActionBar(toolbar)
        getIntentData()
        setRecyclerSettings()
        setAdapterListener()
        setButtonListeners()
        setNoWorksTextViewVisibility()
    }

    private fun setRecyclerSettings() {
        adapter = WorkInfoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        repository.getAllWorkListForCar(currentCarId)
                .subscribe { list ->
                    adapter.updateLists(list)
                    setNoWorksTextViewVisibility()
                }
    }

    private fun setAdapterListener() {
        adapter.onWorkInfoItemClickListener = {
            val intent = Intent(this, ActivityEditWork::class.java)
            intent.putExtra("workInfo", it)
            startActivityForResult(intent, EDIT_WORK_ACTIVITY_CODE)
        }
    }

    private fun setButtonListeners() {
        imgButtonBack.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
        fab.setOnClickListener {
            val intent = Intent(this, ActivityAddWork::class.java)
            intent.putExtra("currentCarId", currentCarId)
            startActivityForResult(intent, ADD_WORK_ACTIVITY_CODE)
        }
    }

    private fun getIntentData() {
        if (intent != null) {
            currentCar = intent.getParcelableExtra("carInfo") ?: CarInfo("", "", "", "","")
            currentCarId = currentCar.id
            textCarName.text = currentCar.producer
            textCarModel.text = currentCar.model
        }
    }

    private fun setNoWorksTextViewVisibility() {
        if (adapter.itemCount != 0) noWorksAddedText.visibility = View.INVISIBLE
        else noWorksAddedText.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.works_menu, menu)
        searchView = menu?.findItem(R.id.searchWorkList)?.actionView as SearchView
        searchView.apply {
            imeOptions = EditorInfo.IME_ACTION_DONE
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?) = false

                override fun onQueryTextChange(p0: String?): Boolean {
                    adapter.filter.filter(p0)
                    return false
                }
            })
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemInProgress -> {
                adapter.showByOrder(this, resources.getString(R.string.work_in_progress))
            }
            R.id.itemInPending -> {
                adapter.showByOrder(this, resources.getString(R.string.work_pending))
            }
            R.id.itemCompleted -> {
                adapter.showByOrder(this, resources.getString(R.string.work_completed))
            }
            R.id.itemAll -> {
                adapter.showByOrder(this, "all")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CODE_BUTTON_BACK) {
            repository.getAllWorkListForCar(currentCarId)
                    .subscribe { list ->
                        adapter.updateLists(list)
                        setNoWorksTextViewVisibility()
                    }
            if (!searchView.isIconified) {
                searchView.onActionViewCollapsed()
            }
        }
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_OK)
        finish()
        super.onBackPressed()
    }
}