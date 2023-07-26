pluginManagement {
    includeBuild("buildplugin")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/") }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://maven.aliyun.com/nexus/content/groups/public/") }
        maven { setUrl( "https://jitpack.io" )}
    }
}
rootProject.name = "LProject"
include(":app")

