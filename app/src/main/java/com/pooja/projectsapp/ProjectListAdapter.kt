package com.pooja.projectsapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pooja.projectsapp.databinding.ItemMainProjectListBinding
import com.pooja.projectsapp.room.entity.ProjectEntity
import java.time.Duration
import java.time.Instant
import kotlin.time.toKotlinDuration


class ProjectListAdapter(context: Context) : RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder>() {

    private var ctx = context
    private var projectList = emptyList<ProjectEntity>()
    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnProjectRemoveClickListener: OnProjectRemoveClickListener? = null
    var totalDay: Int = 0
    var totalHour: Int = 0
    var totalMinute: Int = 0

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener?) {
        mOnItemClickListener = mItemClickListener
    }

    fun setOnProjectRemoveClickListener(mProjectRemoveClickListener: OnProjectRemoveClickListener?) {
        mOnProjectRemoveClickListener = mProjectRemoveClickListener
    }

    class ProjectViewHolder(itemViews: View) : RecyclerView.ViewHolder(itemViews) {
        val binding = ItemMainProjectListBinding.bind(itemViews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_project_list, parent, false))
    }

    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val currentItem = projectList[position]

        var totalD = 0
        var totalH = 0
        var totalM = 0
        var i = 0
        while (i < projectList.size) {
            val sum = projectList[i]
            Duration.between(
                Instant.parse("${sum.starDate}T${sum.startTime}:00.0Z"),
                Instant.parse("${sum.endDate}T${sum.endTime}:00.0Z")
            ).toKotlinDuration().toComponents { days, hours, minutes, _, _ ->
                totalD = (totalD + days).toInt()
                totalH = (totalH + hours)
                totalM = (totalM + minutes)
            }
            i++
        }
        totalDay = totalD
        totalHour = totalH
        totalMinute = totalM

        Duration.between(
            Instant.parse("${currentItem.starDate}T${currentItem.startTime}:00.0Z"),
            Instant.parse("${currentItem.endDate}T${currentItem.endTime}:00.0Z")
        ).toKotlinDuration().toComponents { days, hours, minutes, _, _ ->
            holder.binding.day.text = days.toString()
            holder.binding.hour.text = hours.toString()
            holder.binding.minute.text = minutes.toString()
            holder.binding.name.text = currentItem.name
        }

        /*holder.binding.day.text = currentItem.starDate
        holder.binding.hour.text = currentItem.startTime
        holder.binding.minute.text = currentItem.endDate
        holder.binding.name.text = currentItem.name*/

        holder.binding.name.setOnClickListener { view ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(view, currentItem, position)
            }
            //view.findNavController().navigate(ProjectFragmentDirections.actionProjectFragmentToWebFragment(currentItem.url, currentItem.position, "project"))
        }
        /*holder.binding.preview.setOnClickListener { view ->
            if (mOnItemClickListener != null) {
                mOnItemClickListener!!.onItemClick(view, currentItem, position)
            }
            //view.findNavController().navigate(ProjectFragmentDirections.actionProjectFragmentToWebFragment(currentItem.url, currentItem.position, "project"))
        }
        holder.binding.close.setOnClickListener { view ->
            if (mOnProjectRemoveClickListener != null) {
                mOnProjectRemoveClickListener!!.onProjectRemoveClick(view, currentItem, position)
            }
        }*/

        return
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    fun setProject(projectList: List<ProjectEntity>) {
        this.projectList = projectList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, obj: ProjectEntity, position: Int)
    }

    interface OnProjectRemoveClickListener {
        fun onProjectRemoveClick(view: View?, obj: ProjectEntity, position: Int)
    }
}