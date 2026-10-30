package com.vp.visprog_week6_assignment.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vp.visprog_week6_assignment.model.Community
import com.vp.visprog_week6_assignment.model.Project
import com.vp.visprog_week6_assignment.ui.widget.ClubProjectCard
import com.vp.visprog_week6_assignment.ui.widget.AddProjectDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClubProjectsScreen(
    community: Community,
    projects: List<Project>,
    onBack: () -> Unit,
    onDeleteProject: (String) -> Unit,
    onAddProject: (String, String, List<String>) -> Unit,
    onProjectClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }

    val activeProjects = projects.count {
        it.status == com.vp.visprog_week6_assignment.model.ProjectStatus.IN_PROGRESS
    }

    Scaffold(
        modifier = modifier,
        containerColor = Color(0xFFF7F8FC),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "${community.name} Projects",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = community.category,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                },
                navigationIcon = {
                    androidx.compose.material3.IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFF2563EB),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Project"
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 12.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color(0xFFE8F0FF),
                                shape = MaterialTheme.shapes.large
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = community.name,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E40AF),
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${projects.size} Proyek • $activeProjects Proyek Aktif",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF2563EB)
                        )
                    }
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 4.dp
                        )
                    ) {
                        Text(
                            text = "Daftar Proyek Mahasiswa (${projects.size})",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                if (projects.isEmpty()) {
                    item {
                        Text(
                            text = "Belum ada proyek di komunitas ini.",
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        )
                    }
                } else {
                    items(
                        items = projects,
                        key = { it.id }
                    ) { project ->

                        ClubProjectCard(
                            project = project,
                            onDelete = {
                                onDeleteProject(project.id)
                            }
                        )
                    }
                }
            }
        }
    }
    if (showAddDialog) {
        AddProjectDialog(
            onDismiss = {
                showAddDialog = false
            },
            onSave = { title, description, techStack ->
                onAddProject(title, description, techStack)
                showAddDialog = false
            }
        )
    }
}