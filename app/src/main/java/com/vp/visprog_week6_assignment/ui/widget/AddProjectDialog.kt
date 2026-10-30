package com.vp.visprog_week6_assignment.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

private val techOptions = listOf(
    "Kotlin",
    "Jetpack Compose",
    "Firebase",
    "Ktor",
    "Room",
    "Python",
    "MySQL",
    "Google Maps SDK"
)
@Composable
fun AddProjectDialog(
    onDismiss: () -> Unit,
    onSave: (String, String, List<String>) -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var selectedTech by rememberSaveable {
        mutableStateOf(setOf<String>())
    }
    val isValid =
        title.isNotBlank() &&
                description.isNotBlank() &&
                selectedTech.isNotEmpty()
    AlertDialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        title = {
            Text(
                text = "Add Project",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Judul Proyek *",
                    style = MaterialTheme.typography.labelMedium
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Masukkan judul proyek")
                    },
                    singleLine = true
                )
                Text(
                    text = "Deskripsi Proyek (Minimal 3 Baris) *",
                    style = MaterialTheme.typography.labelMedium
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Jelaskan proyek Anda...")
                    },
                    minLines = 3,
                    maxLines = 5
                )
                Text(
                    text = "Tech Stack (Pilih Multi-Selection) *",
                    style = MaterialTheme.typography.labelMedium
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    techOptions.chunked(2).forEach { rowOptions ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            rowOptions.forEach { technology ->

                                FilterChip(
                                    selected = technology in selectedTech,
                                    onClick = {
                                        selectedTech =
                                            if (technology in selectedTech) {
                                                selectedTech - technology
                                            } else {
                                                selectedTech + technology
                                            }
                                    },
                                    label = {
                                        Text(technology)
                                    }
                                )
                            }
                        }
                    }
                }
                Text(
                    text = "${selectedTech.size} teknologi dipilih",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        title.trim(),
                        description.trim(),
                        selectedTech.toList()
                    )
                },
                enabled = isValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}