# Compose Date Time Picker

This is a multiplatform port of ComposeMaterialDialogs - Date Time Picker

#### [Date and Time Picker Documentation](https://vanpra.github.io/compose-material-dialogs/DateTimePicker)

![](https://raw.githubusercontent.com/vanpra/compose-material-dialogs/main/imgs/date_and_time.png)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.vanpra.compose-material-dialogs/datetime/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.vanpra.compose-material-dialogs/datetime)


### Usage


### Multiplatform Dependency

#### Step 1 : Add the Github Packages Repo

```kotlin

val githubProperties = Properties()
githubProperties.load(FileInputStream(rootProject.file("github.properties")))

allprojects {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/username/repo")
            credentials {
                username = (githubProperties["gpr.usr"] ?: System.getenv("GPR_USER")).toString()
                password = (githubProperties["gpr.key"] ?: System.getenv("GPR_API_KEY")).toString()
            }
        }
    }
}
```

#### Step 2 : Create Github Properties File

Create `github.properties` file in your project at root level and add two properties (make github personal access token)

```properties
gpr.usr=yourusername
gpr.key=yourgithubpersonalaccesstoken
```

#### Step 3 : Add The Dependency

Get the latest version , check it from releases page or better from the packages

This code is for Kotlin Gradle Script , Convert it over to groovy by removing quotes and braces if you use groovy !

```kotlin
implementation("com.wakaztahir:datetime:<current-version>")
```