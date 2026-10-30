package com.vp.visprog_week6_assignment.data

import com.vp.visprog_week6_assignment.model.Project
import com.vp.visprog_week6_assignment.model.ProjectStatus

object ProjectData {
    val projects = listOf(
        Project(
            id = "project-1",
            title = "Smart Campus Navigation",
            description = "Navigate campus buildings, computer labs, and facilities in real time.",
            communityId = "gdsc",
            communityName = "Google Developer Student Clubs",
            techStack = listOf("Kotlin", "Jetpack Compose", "Room"),
            status = ProjectStatus.IN_PROGRESS
        ),
        Project(
            id = "project-2",
            title = "Student Attendance App",
            description = "An attendance application that allows students to record their attendance using QR codes and Firebase.",
            communityId = "gdsc",
            communityName = "Google Developer Student Clubs",
            techStack = listOf("Kotlin", "Firebase"),
            status = ProjectStatus.COMPLETED
        ),
        Project(
            id = "project-3",
            title = "Secure Login System",
            description = "A secure authentication system designed to protect user accounts and prevent common security threats.",
            communityId = "cybersecurity",
            communityName = "CyberSecurity Guild",
            techStack = listOf("Kotlin", "Firebase"),
            status = ProjectStatus.IN_PROGRESS
        ),
        Project(
            id = "project-4",
            title = "AI Study Assistant",
            description = "An AI-powered study assistant that helps students understand and review their course materials.",
            communityId = "ai-society",
            communityName = "AI Society",
            techStack = listOf("Python", "Machine Learning"),
            status = ProjectStatus.IN_PROGRESS
        ),
        Project(
            id = "project-5",
            title = "Image Recognition App",
            description = "An image recognition application that uses computer vision and machine learning to identify objects.",
            communityId = "ai-society",
            communityName = "AI Society",
            techStack = listOf("Python", "TensorFlow"),
            status = ProjectStatus.COMPLETED
        ),
        Project(
            id = "project-6",
            title = "Campus E-Commerce",
            description = "An online marketplace for students to browse products and make transactions.",
            communityId = "web-dev",
            communityName = "Web Development Club",
            techStack = listOf("Laravel", "MySQL"),
            status = ProjectStatus.IN_PROGRESS
        ),
        Project(
            id = "project-7",
            title = "2D Adventure Game",
            description = "A 2D adventure game featuring different levels, playable characters, and challenges.",
            communityId = "game-dev",
            communityName = "Game Development Club",
            techStack = listOf("Kotlin", "LibGDX"),
            status = ProjectStatus.COMPLETED
        ),
        Project(
            id = "project-8",
            title = "Campus Connect",
            description = "A mobile application that helps students communicate and share information with one another.",
            communityId = "mobile-dev",
            communityName = "Mobile Development Community",
            techStack = listOf("Kotlin", "Jetpack Compose", "Firebase"),
            status = ProjectStatus.IN_PROGRESS
        )
    )
}
