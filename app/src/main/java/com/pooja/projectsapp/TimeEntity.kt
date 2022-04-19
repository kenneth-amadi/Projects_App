package com.pooja.projectsapp

class TimeEntity(var day: String, var hours: String, var minutes: String) {

    override fun toString(): String {
        return ""
    }

    fun toDay(): String {
        return day
    }

    fun toHour(): String {
        return hours
    }

    fun toMinute(): String {
        return minutes
    }
}