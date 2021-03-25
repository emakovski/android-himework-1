package by.it.academy.homework_7_rx_java.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.homework_7_rx_java.CarInfoAdapter
import by.it.academy.homework_7_rx_java.DataBaseRepository
import by.it.academy.homework_7_rx_java.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val ADD_ACTIVITY_CODE = 1
private const val EDIT_ACTIVITY_CODE = 2
private const val WORK_LIST_ACTIVITY = 10
private const val RESULT_CODE_BUTTON_BACK = 5

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarInfoAdapter
    private lateinit var noCarsAddedText: TextView
    private lateinit var repository: DataBaseRepository
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_car_list)
        fab = findViewById(R.id.addCarFab)
        recyclerView = findViewById(R.id.carListRecycler)
        noCarsAddedText = findViewById(R.id.emptyCarList_text)
        DataBaseRepository.initDatabase(applicationContext)
        repository = DataBaseRepository()
        setRecyclerSettings()
        setFabListener()
        setAdapterListeners()
        writeLogToFile()
        setNoCarsTextViewVisibility()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        searchView = menu?.findItem(R.id.search)?.actionView as SearchView
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

    private fun setFabListener() {
        fab.setOnClickListener {
            val intent = Intent(this, ActivityAddCar::class.java)
            startActivityForResult(intent, ADD_ACTIVITY_CODE)
        }
    }

    private fun setRecyclerSettings() {
        adapter = CarInfoAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        repository.getAllList()
                .subscribe { list ->
                    adapter.updateList(list.sortedBy { it.producer.toLowerCase(Locale.ROOT) })
                    setNoCarsTextViewVisibility()
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CODE_BUTTON_BACK) {
            repository.getAllList()
                    .subscribe { list ->
                        adapter.updateList(list.sortedBy { it.producer.toLowerCase(Locale.ROOT) })
                        setNoCarsTextViewVisibility()
                    }
            if (!searchView.isIconified) {
                searchView.onActionViewCollapsed()
            }
        }
    }

    private fun writeLogToFile() {
        val logList = StringBuilder()
        val file = File(filesDir, "Logs.txt")
        if (!file.exists()) {
            file.createNewFile()
        }
        logList.append("${file.readText()} \n")
        val simpleDateFormat = SimpleDateFormat.getDateTimeInstance()
        val date = simpleDateFormat.format(Date())
        logList.append(date)
        file.writeText(logList.toString())
    }

    private fun setNoCarsTextViewVisibility() {
        if (adapter.itemCount != 0) noCarsAddedText.visibility = View.INVISIBLE
        else noCarsAddedText.visibility = View.VISIBLE
    }

    private fun setAdapterListeners() {
        adapter.onEditIconClickListener = {
            val intent = Intent(this, ActivityEditCar::class.java)
            intent.putExtra("carInfo", it)
            startActivityForResult(intent, EDIT_ACTIVITY_CODE)
        }
        adapter.onCarInfoShowWorkListClickListener = {
            val intent = Intent(this, ActivityWorkList::class.java)
            intent.putExtra("carInfo", it)
            startActivityForResult(intent, WORK_LIST_ACTIVITY)
        }
    }
}