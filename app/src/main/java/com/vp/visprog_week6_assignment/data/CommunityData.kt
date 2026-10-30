package com.vp.visprog_week6_assignment.data

import com.vp.visprog_week6_assignment.model.Community

object CommunityData {
    val communities = listOf(
        Community(
            id = "gdsc",
            name = "Google Developer Student Clubs",
            description = "Mobile Development & Google Technologies",
            category = "Development"
        ),
        Community(
            id = "cybersecurity",
            name = "CyberSecurity Guild",
            description = "Cybersecurity & Penetration Testing",
            category = "Security"
        ),
        Community(
            id = "ai-society",
            name = "AI Society",
            description = "Artificial Intelligence & Machine Learning",
            category = "Artificial Intelligence"
        ),
        Community(
            id = "web-dev",
            name = "Web Development Club",
            description = "Web Development & Backend Systems",
            category = "Web Development"
        ),
        Community(
            id = "game-dev",
            name = "Game Development Club",
            description = "Game Design & Development",
            category = "Game Development"
        ),
        Community(
            id = "mobile-dev",
            name = "Mobile Development Community",
            description = "Android & Kotlin Development",
            category = "Mobile Development"
        )
    )
}
