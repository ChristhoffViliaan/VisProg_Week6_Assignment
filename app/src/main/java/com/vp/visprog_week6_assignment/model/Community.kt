package com.vp.visprog_week6_assignment.model

data class Community(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val isJoined: Boolean = false
)
