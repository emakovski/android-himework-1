package by.it.academy.homework_5

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.it.academy.homework_5.data.WorkInfo


class WorkInfoAdapter() : RecyclerView.Adapter<WorkInfoAdapter.WorkInfoViewHolder>(), Filterable {
    constructor(savedInfo: List<WorkInfo>) : this() {
        workInfoList = savedInfo as ArrayList<WorkInfo>
        workInfoListForFilter = ArrayList(workInfoList)
        workInfoListCopyForOrder = ArrayList(workInfoList)
    }

    private var workInfoList: ArrayList<WorkInfo> = arrayListOf()
    private var workInfoListForFilter: ArrayList<WorkInfo> = arrayListOf()
    private var workInfoListCopyForOrder: ArrayList<WorkInfo> = arrayListOf()
    lateinit var onWorkInfoItemClickListener: (workInfo: WorkInfo) -> Unit

    class WorkInfoViewHolder(itemView: View,
                             private val listener: (workInfo: WorkInfo) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageWork: ImageView = itemView.findViewById(R.id.working_progress)
        private val workStatus: TextView = itemView.findViewById(R.id.working_progress_text_item)
        private val workName: TextView = itemView.findViewById(R.id.work_name_item)
        private val workDate: TextView = itemView.findViewById(R.id.work_date_item)
        private val workCost: TextView = itemView.findViewById(R.id.work_price_item)
        fun bind(workInfo: WorkInfo) {
            setImageStatus(workInfo.status, itemView.resources, imageWork, workStatus)
            workStatus.text = workInfo.status
            workName.text = workInfo.workName
            workDate.text = workInfo.date
            workCost.text = "$${workInfo.cost}"
            itemView.setOnClickListener {
                listener.invoke(workInfo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_work, parent, false)
        return WorkInfoViewHolder(view, onWorkInfoItemClickListener)
    }

    override fun onBindViewHolder(holder: WorkInfoViewHolder, position: Int) {
        holder.bind(workInfoList[position])
    }

    override fun getItemCount() = workInfoList.size

    private val filter: Filter = object : Filter() {
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = arrayListOf<WorkInfo>()
            if (p0 == null || p0.isEmpty()) {
                filteredList.addAll(workInfoListForFilter)
            } else {
                val filterPattern = p0.toString().toLowerCase().trim()
                workInfoListForFilter.forEach {
                    if (it.workName?.toLowerCase()?.contains(filterPattern)!!) {
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            workInfoList.clear()
            workInfoList.addAll(p1?.values as ArrayList<WorkInfo>);
            notifyDataSetChanged()
        }
    }

    override fun getFilter() = filter
    fun updateLists(list: List<WorkInfo>) {
        workInfoList = ArrayList(list as ArrayList<WorkInfo>)
        workInfoListForFilter = ArrayList(list)
        workInfoListCopyForOrder = ArrayList(list)
        notifyDataSetChanged()
    }

    fun showByOrder(context: Context, order: String) {
        var list = arrayListOf<WorkInfo>()
        when (order) {
            context.resources.getString(R.string.work_pending) -> {
                workInfoList = workInfoListCopyForOrder
                list = getListByOrder(order)
            }
            context.resources.getString(R.string.work_in_progress) -> {
                workInfoList = workInfoListCopyForOrder
                list = getListByOrder(order)
            }
            context.resources.getString(R.string.work_completed) -> {
                workInfoList = workInfoListCopyForOrder
                list = getListByOrder(order)
            }
            "all" -> {
                list = workInfoListCopyForOrder
            }
        }
        workInfoList = ArrayList(list)
        workInfoListForFilter = ArrayList(list)
        notifyDataSetChanged()
    }

    private fun getListByOrder(order: String): ArrayList<WorkInfo> {
        val list = arrayListOf<WorkInfo>()
        workInfoList.forEach {
            if (it.status.equals(order)) list.add(it)
        }
        return list
    }
}