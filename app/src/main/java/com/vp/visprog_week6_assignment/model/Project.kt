package com.vp.visprog_week6_assignment.model

data class Project(
    val id: String,
    val title: String,
    val description: String,
    val communityId: String,
    val communityName: String,
    val techStack: List<String>,
    val status: ProjectStatus = ProjectStatus.IN_PROGRESS
)
