package com.vp.visprog_week6_assignment.model

data class DashboardProjectItem(
    val id: String,
    val name: String,
    val communityName: String,
    val techStack: List<String>
)