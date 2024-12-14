pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Task Scribe"
include(":app")
include(":shared:shared-ui")
include(":shared:shared-data")
include(":home:home-presentation")
include(":notes:notes-data")
include(":notes:notes-domain")
include(":shared:shared-domain")
include(":notes:notes-presentation")
include(":tasks:tasks-data")
include(":tasks:tasks-domain")
include(":tasks:tasks-presentation")
include(":bookmarks")
include(":bookmarks:bookmarks-data")
include(":bookmarks:bookmarks-domain")
include(":bookmarks:bookmarks-presentation")
include(":home:home-data")
include(":home:home-domain")
include(":onboarding:onboarding-presentation")
include(":onboarding:onboarding-domain")
include(":onboarding:onboarding-data")
