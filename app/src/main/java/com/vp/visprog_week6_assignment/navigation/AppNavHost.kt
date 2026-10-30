package com.vp.visprog_week6_assignment.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vp.visprog_week6_assignment.ui.screen.DashboardScreen
import com.vp.visprog_week6_assignment.ui.screen.CommunitiesScreen
import com.vp.visprog_week6_assignment.ui.screen.ClubProjectsScreen
import com.vp.visprog_week6_assignment.ui.screen.ProjectDetailScreen
import com.vp.visprog_week6_assignment.viewmodel.CampusViewModel

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    campusViewModel: CampusViewModel = viewModel()
) {
    val communities by campusViewModel.communities.collectAsStateWithLifecycle()
    val projects by campusViewModel.projects.collectAsStateWithLifecycle()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val joinedClubs by campusViewModel.joinedClubs.collectAsStateWithLifecycle()
    val dashboardProjects by campusViewModel.dashboardProjects.collectAsStateWithLifecycle()
    val showBottomBar = currentDestination?.hasRoute<DashboardRoute>() == true || currentDestination?.hasRoute<CommunitiesRoute>() == true
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<DashboardRoute>() == true,
                        onClick = {
                            navController.navigate(DashboardRoute) {
                                popUpTo<DashboardRoute> {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Dashboard"
                            )
                        },
                        label = {
                            Text("Dashboard")
                        }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.hasRoute<CommunitiesRoute>() == true,
                        onClick = {
                            navController.navigate(CommunitiesRoute) {
                                popUpTo<DashboardRoute> {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                Icons.Default.Groups,
                                contentDescription = "Communities"
                            )
                        },
                        label = {
                            Text("Communities")
                        }
                    )
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = DashboardRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<DashboardRoute> {
                DashboardScreen(
                    studentName = campusViewModel.studentName,
                    major = campusViewModel.major,
                    year = campusViewModel.year,
                    studentId = campusViewModel.studentId,

                    joinedClubs = joinedClubs,
                    projects = dashboardProjects,

                    onClubClick = { communityId ->
                        navController.navigate(
                            ClubProjectsRoute(communityId)
                        )
                    },
                    onProjectClick = { projectId ->
                        navController.navigate(
                            ProjectDetailRoute(projectId)
                        )
                    }
                )
            }
            composable<CommunitiesRoute> {

                CommunitiesScreen(
                    communities = communities,

                    onJoinToggle = { communityId ->
                        campusViewModel.toggleCommunityMembership(
                            communityId
                        )
                    },
                    onCommunityClick = { communityId ->
                        if (campusViewModel.hasJoinedCommunity(communityId)) {
                            navController.navigate(
                                ClubProjectsRoute(communityId)
                            )
                        }
                    }
                )
            }
            composable<ClubProjectsRoute> { backStackEntry ->

                val route = backStackEntry.toRoute<ClubProjectsRoute>()

                val community = communities.find {
                    it.id == route.communityId
                }
                val isJoined = community?.isJoined == true
                LaunchedEffect(community, isJoined) {
                    if (!isJoined) {
                        navController.popBackStack()
                    }
                }
                if (community != null && isJoined) {
                    val communityProjects = projects.filter {
                        it.communityId == route.communityId
                    }
                    ClubProjectsScreen(
                        community = community,
                        projects = communityProjects,
                        onBack = {
                            navController.popBackStack()
                        },
                        onDeleteProject = { projectId ->
                            campusViewModel.deleteProject(projectId)
                        },
                        onAddProject = { title, description, techStack ->
                            campusViewModel.addProject(
                                title = title,
                                description = description,
                                communityId = route.communityId,
                                techStack = techStack
                            )
                        },
                        onProjectClick = { projectId ->
                            navController.navigate(
                                ProjectDetailRoute(projectId)
                            )
                        }
                    )
                }
            }
            composable<ProjectDetailRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<ProjectDetailRoute>()
                val project = projects.find {
                    it.id == route.projectId
                }
                if (project != null) {

                    ProjectDetailScreen(
                        project = project,
                        onBack = {
                            navController.popBackStack()
                        },
                        onStatusToggle = {
                            campusViewModel.toggleProjectStatus(
                                project.id
                            )
                        }
                    )
                }
            }
        }
    }
}
