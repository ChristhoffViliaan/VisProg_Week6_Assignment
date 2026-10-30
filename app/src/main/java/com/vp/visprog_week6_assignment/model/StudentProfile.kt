package com.vp.visprog_week6_assignment.model

data class StudentProfile(
    val name: String,
    val major: String,
    val year: String,
    val studentId: String,
    val avatarUrl: String? = null
)
