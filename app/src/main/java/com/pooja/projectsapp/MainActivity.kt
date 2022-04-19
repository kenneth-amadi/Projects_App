package com.pooja.projectsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.pooja.projectsapp.databinding.ActivityMainBinding
import com.pooja.projectsapp.room.entity.ProjectEntity
import com.pooja.projectsapp.room.viewmodel.ProjectViewModel
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabViewModel: ProjectViewModel
    private lateinit var mAdapter: ProjectListAdapter

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tabViewModel = ViewModelProvider(this)[ProjectViewModel::class.java]

        binding.recyclerView.run {
            mAdapter = ProjectListAdapter(this@MainActivity)
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(false)
            setItemViewCacheSize(50)
            isNestedScrollingEnabled = false
            ViewModelProvider(this@MainActivity)[ProjectViewModel::class.java].getAllProjects.observe(this@MainActivity) { tab ->
                mAdapter.setProject(tab)
                loadTotal()
            }
            adapter = mAdapter
        }

        binding.startDate.setOnClickListener {
            MaterialDatePicker.Builder.datePicker().build().apply {
                show(supportFragmentManager, "DATE_PICKER")
                addOnPositiveButtonClickListener {
                    val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    utc.timeInMillis = it
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val formatted: String = format.format(utc.time)
                    binding.startDate.setText(formatted)
                }
            }
        }

        binding.startTime.setOnClickListener {
            MaterialTimePicker.Builder()
                .setTitleText("SELECT YOUR TIMING").setHour(12).setMinute(10).setTimeFormat(TimeFormat.CLOCK_24H).build().apply {
                    show(supportFragmentManager, "MainActivity")
                    addOnPositiveButtonClickListener {
                        val pickedHour: Int = hour
                        val pickedMinute: Int = minute
                        if (pickedMinute.toString().length == 1) {
                            binding.startTime.setText("$pickedHour:0$pickedMinute")
                        } else binding.startTime.setText("$pickedHour:$pickedMinute")
                    }
                }
        }


        binding.endDate.setOnClickListener {
            MaterialDatePicker.Builder.datePicker().build().apply {
                show(supportFragmentManager, "DATE_PICKER")
                addOnPositiveButtonClickListener {
                    val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    utc.timeInMillis = it
                    val format = SimpleDateFormat("yyyy-MM-dd")
                    val formatted: String = format.format(utc.time)
                    binding.endDate.setText(formatted)
                }
            }
        }

        binding.endTime.setOnClickListener {
            MaterialTimePicker.Builder()
                .setTitleText("SELECT YOUR TIMING").setHour(12).setMinute(10).setTimeFormat(TimeFormat.CLOCK_24H).build().apply {
                    show(supportFragmentManager, "MainActivity")
                    addOnPositiveButtonClickListener {
                        val pickedHour: Int = hour
                        val pickedMinute: Int = minute
                        if (pickedMinute.toString().length == 1) {
                            binding.endTime.setText("$pickedHour:0$pickedMinute")
                        } else binding.endTime.setText("$pickedHour:$pickedMinute")
                    }
                }
        }


        binding.save.setOnClickListener {
            binding.name
            if (!binding.projectName.text.isNullOrBlank() && !binding.startDate.text.isNullOrBlank() && !binding.startTime.text.isNullOrBlank() && !binding.endDate.text.isNullOrBlank() && !binding.endTime.text.isNullOrBlank()) {
                tabViewModel.addProject(
                    ProjectEntity(
                        0,
                        "${binding.projectName.text}",
                        "${binding.startDate.text}",
                        "${binding.startTime.text}",
                        "${binding.endDate.text}",
                        "${binding.endTime.text}"
                    )
                )
                Toast.makeText(this, "Project saved.", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Please, fill all fields.", Toast.LENGTH_SHORT).show()
        }

        binding.add.setOnClickListener {
            binding.mainView.visibility = View.VISIBLE
            binding.displayView.visibility = View.GONE
        }

        binding.display.setOnClickListener {
            binding.displayView.visibility = View.VISIBLE
            binding.mainView.visibility = View.GONE
        }

        binding.total.setOnClickListener { loadTotal() }

        binding.exit.setOnClickListener { finish() }

        mAdapter.setOnItemClickListener(
            object : ProjectListAdapter.OnItemClickListener {
                override fun onItemClick(view: View?, obj: ProjectEntity, position: Int) {
                    tabViewModel.getProject(1).observe(this@MainActivity) {
                        Toast.makeText(this@MainActivity, obj.name, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        mAdapter.setOnProjectRemoveClickListener(object : ProjectListAdapter.OnProjectRemoveClickListener {
            override fun onProjectRemoveClick(view: View?, obj: ProjectEntity, position: Int) {
                tabViewModel.deleteProject(obj.name, obj.position)
            }
        })

        /*mAdapter.setOnProjectRemoveClickListener(object : ProjectListAdapter.OnProjectRemoveClickListener {
            override fun onProjectRemoveClick(view: View?, obj: ProjectEntity, position: Int) {
                dialogDeleteProject(obj.name, obj.position)
            }
        })*/

        /*binding.btnBack.setOnClickListener {
            onBackPressed()
        }*/

    }

    override fun onResume() {
        super.onResume()
        loadTotal()
    }

    private fun loadTotal() {
        binding.totalDay.text = mAdapter.totalDay.toString()
        binding.totalHour.text = mAdapter.totalHour.toString()
        binding.totalMinute.text = mAdapter.totalMinute.toString()
    }

    /*fun dialogDeleteProject(url: String, position: Int) {
        val dialog = Dialog(this, R.style.Dialog_Full)
        val b = ItemDeleteDialogBinding.inflate(dialog.layoutInflater, null, false)
        dialog.setContentView(b.root)
        b.btnCancelDialog.setOnClickListener { dialog.dismiss() }
        b.btnDeleteDialog.setOnClickListener { tab().deleteProject(url, position); dialog.dismiss() }
        dialog.show()
    }*/
}