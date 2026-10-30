package com.vp.visprog_week6_assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vp.visprog_week6_assignment.data.CommunityData
import com.vp.visprog_week6_assignment.data.ProjectData
import com.vp.visprog_week6_assignment.data.StudentData
import com.vp.visprog_week6_assignment.model.Community
import com.vp.visprog_week6_assignment.model.DashboardClubItem
import com.vp.visprog_week6_assignment.model.DashboardProjectItem
import com.vp.visprog_week6_assignment.model.Project
import com.vp.visprog_week6_assignment.model.ProjectStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.UUID

class CampusViewModel : ViewModel() {

    val studentName = StudentData.name
    val major = StudentData.major
    val year = StudentData.year
    val studentId = StudentData.studentId

    private val _communities =
        MutableStateFlow(CommunityData.communities)

    val communities: StateFlow<List<Community>> =
        _communities.asStateFlow()

    private val _projects =
        MutableStateFlow(ProjectData.projects)

    val projects: StateFlow<List<Project>> =
        _projects.asStateFlow()

    val joinedClubs: StateFlow<List<DashboardClubItem>> =
        combine(_communities, _projects) { communityList, projectList ->

            communityList
                .filter { it.isJoined }
                .map { community ->
                    DashboardClubItem(
                        id = community.id,
                        name = community.name,
                        description = community.description,
                        projectCount = projectList.count {
                            it.communityId == community.id
                        }
                    )
                }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val dashboardProjects: StateFlow<List<DashboardProjectItem>> =
        combine(_communities, _projects) { communityList, projectList ->
            val joinedCommunityIds = communityList
                .filter { it.isJoined }
                .map { it.id }
                .toSet()
            projectList
                .filter { it.communityId in joinedCommunityIds }
                .map { project ->
                    DashboardProjectItem(
                        id = project.id,
                        name = project.title,
                        communityName = project.communityName,
                        techStack = project.techStack
                    )
                }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
    val joinedClubCount: StateFlow<Int> =
        joinedClubs.combine(joinedClubs) { clubs, _ ->
            clubs.size
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )
    val totalProjectCount: StateFlow<Int> =
        dashboardProjects.combine(dashboardProjects) { projects, _ ->
            projects.size
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            0
        )
    fun toggleCommunityMembership(communityId: String) {
        _communities.update { communityList ->
            communityList.map { community ->
                if (community.id == communityId) {
                    community.copy(isJoined = !community.isJoined)
                } else {
                    community
                }
            }
        }
    }

    fun hasJoinedCommunity(communityId: String): Boolean {
        return _communities.value.any {
            it.id == communityId && it.isJoined
        }
    }

    fun getCommunity(communityId: String): Community? {
        return _communities.value.find {
            it.id == communityId
        }
    }

    fun getProjectsForCommunity(communityId: String): List<Project> {
        return _projects.value.filter {
            it.communityId == communityId
        }
    }

    fun getProject(projectId: String): Project? {
        return _projects.value.find {
            it.id == projectId
        }
    }

    fun addProject(
        title: String,
        description: String,
        communityId: String,
        techStack: List<String>
    ) {
        if (
            title.isBlank() ||
            description.isBlank() ||
            techStack.isEmpty() ||
            !hasJoinedCommunity(communityId)
        ) {
            return
        }

        val community = getCommunity(communityId) ?: return

        val newProject = Project(
            id = UUID.randomUUID().toString(),
            title = title.trim(),
            description = description.trim(),
            communityId = community.id,
            communityName = community.name,
            techStack = techStack,
            status = ProjectStatus.IN_PROGRESS
        )

        _projects.update { projectList ->
            projectList + newProject
        }
    }

    fun deleteProject(projectId: String) {
        _projects.update { projectList ->
            projectList.filterNot {
                it.id == projectId
            }
        }
    }

    fun toggleProjectStatus(projectId: String) {
        _projects.update { projectList ->
            projectList.map { project ->
                if (project.id == projectId) {
                    project.copy(
                        status = if (project.status == ProjectStatus.IN_PROGRESS) {
                            ProjectStatus.COMPLETED
                        } else {
                            ProjectStatus.IN_PROGRESS
                        }
                    )
                } else {
                    project
                }
            }
        }
    }
}
