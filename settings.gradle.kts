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
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/xydownik/currency_lib")
            credentials {
                username = providers.gradleProperty("USERNAME").orNull ?: System.getenv("USERNAME")
                password = providers.gradleProperty("TOKEN").orNull ?: System.getenv("TOKEN")

            }
        }
    }
}
rootProject.name = "CurrencyApp"
include(":app")
 