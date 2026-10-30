package com.vp.visprog_week6_assignment.navigation

import kotlinx.serialization.Serializable

@Serializable
data object DashboardRoute

@Serializable
data object CommunitiesRoute

@Serializable
data class ClubProjectsRoute(
    val communityId: String
)

@Serializable
data class ProjectDetailRoute(
    val projectId: String
)
