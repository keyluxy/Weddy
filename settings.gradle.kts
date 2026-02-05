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

rootProject.name = "Weddy"
include(":app")
include(":core")
include(":core:navigation")
include(":features")
include(":features:auth")
include(":features:auth:api")
include(":features:auth:impl")
include(":features:main")
include(":features:main:api")
include(":features:main:impl")
include(":features:contractors")
include(":core:ui")
