package by.it.academy.homework_6

import android.content.res.Resources
import android.widget.ImageView
import android.widget.TextView

fun setImageStatus(status: String, resources: Resources, iv: ImageView, tv: TextView) {
    when (status) {
        resources.getString(R.string.pending) -> {
            iv.setImageResource(R.drawable.ic_repair_pending)
            tv.setTextColor(resources.getColor(R.color.work_status_pending))
        }
        resources.getString(R.string.in_progress_lowe_case) -> {
            iv.setImageResource(R.drawable.ic_repair_in_progress)
            tv.setTextColor(resources.getColor(R.color.work_status_in_progress))
        }
        resources.getString(R.string.completed_in_lower_case) -> {
            iv.setImageResource(R.drawable.ic_repair_completed)
            tv.setTextColor(resources.getColor(R.color.work_status_completed))
        }
    }
}