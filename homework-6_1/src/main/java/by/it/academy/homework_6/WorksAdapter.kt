package by.it.academy.homework_6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorksAdapter : RecyclerView.Adapter<WorksAdapter.WorkInfoViewHolder>() {
    private var workInfoList = arrayListOf<WorkInfo>()

    class WorkInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.work_item, parent, false)
        return WorkInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkInfoViewHolder, position: Int) {
        holder.bind(workInfoList[position])
    }

    override fun getItemCount() = workInfoList.size


    fun updateLists(list: ArrayList<WorkInfo>) {
        workInfoList = ArrayList(list)
        notifyDataSetChanged()
    }
}