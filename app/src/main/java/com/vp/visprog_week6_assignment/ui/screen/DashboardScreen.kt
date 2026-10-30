package com.vp.visprog_week6_assignment.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vp.visprog_week6_assignment.model.DashboardClubItem
import com.vp.visprog_week6_assignment.model.DashboardProjectItem
import com.vp.visprog_week6_assignment.ui.theme.VisProg_Week6_AssignmentTheme
import com.vp.visprog_week6_assignment.ui.widget.ClubCard
import com.vp.visprog_week6_assignment.ui.widget.EmptyState
import com.vp.visprog_week6_assignment.ui.widget.ProfileCard
import com.vp.visprog_week6_assignment.ui.widget.ProjectCard
import com.vp.visprog_week6_assignment.ui.widget.SectionHeader
import com.vp.visprog_week6_assignment.ui.widget.StatCard

@Composable
fun DashboardScreen(
    studentName: String,
    major: String,
    year: String,
    studentId: String,
    joinedClubs: List<DashboardClubItem>,
    projects: List<DashboardProjectItem>,
    onClubClick: (String) -> Unit,
    onProjectClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8FC)),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ProfileCard(
                studentName = studentName,
                major = major,
                year = year,
                studentId = studentId
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    title = "Joined Clubs",
                    count = joinedClubs.size,
                    description = "Komunitas yang diikuti",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = null,
                            tint = Color(0xFF2456C4)
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Total Projects",
                    count = projects.size,
                    description = "Proyek terdaftar",
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = null,
                            tint = Color(0xFF8054C7)
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
        item {
            SectionHeader(
                title = "Komunitas Saya (${joinedClubs.size})",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFF2456C4)
                    )
                }
            )
        }
        if (joinedClubs.isEmpty()) {
            item {
                EmptyState(
                    message = "Belum bergabung dengan komunitas"
                )
            }
        } else {
            items(
                items = joinedClubs,
                key = { "club-${it.id}" }
            ) { club ->
                ClubCard(
                    club = club,
                    onClick = {
                        onClubClick(club.id)
                    }
                )
            }
        }
        item {
            SectionHeader(
                title = "Proyek Mahasiswa (${projects.size})",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = null,
                        tint = Color(0xFF8054C7)
                    )
                }
            )
        }
        if (projects.isEmpty()) {
            item {
                EmptyState(
                    message = "Belum ada proyek yang dicatat"
                )
            }
        } else {
            items(
                items = projects,
                key = { "project-${it.id}" }
            ) { project ->
                ProjectCard(
                    project = project,
                    onClick = {
                        onProjectClick(project.id)
                    }
                )
            }
        }
    }
}