package com.vp.visprog_week6_assignment.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vp.visprog_week6_assignment.model.Project
import com.vp.visprog_week6_assignment.model.ProjectStatus

@OptIn(androidx.compose.foundation.layout.ExperimentalLayoutApi::class)
@Composable
fun ClubProjectCard(
    project: Project,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = project.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = project.communityName,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }
                IconButton(
                    onClick = onDelete
                ) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFCE4E4)
                    ) {
                        Text(
                            text = "−",
                            color = Color(0xFFDC4545),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(
                                horizontal = 10.dp,
                                vertical = 3.dp
                            )
                        )
                    }
                }
            }
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                project.techStack.forEach { technology ->
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = Color(0xFFF1F3F5)
                    ) {
                        Text(
                            text = technology,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val isCompleted =
                    project.status == ProjectStatus.COMPLETED

                Surface(
                    shape = CircleShape,
                    color = if (isCompleted) {
                        Color(0xFFE0F7E9)
                    } else {
                        Color(0xFFFFF1CC)
                    }
                ) {
                    Text(
                        text = if (isCompleted) {
                            "● Completed"
                        } else {
                            "● In Progress"
                        },
                        color = if (isCompleted) {
                            Color(0xFF168447)
                        } else {
                            Color(0xFFB7791F)
                        },
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        )
                    )
                }
            }
        }
    }
}