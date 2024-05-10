# Beanject

Beanject is a *very simple* dependency injection utility for Kotlin. It allows you to manage your application's components in an easy and decoupled manner.

## Installation

To use Beanject in your project, add the following to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("hu.tothlp:beanject:1.0")
}
```

## Usage

Here are some basic examples of how to use Beanject:

### Registering a Bean

```kotlin
data class TestBean(val testString: String)

val mockBean = TestBean("Test String")
Beanject.beans {
    bean("testBean") { mockBean }
}

// Beanject can be omitted, if the function is imported:
beans {
	bean("testBean") { mockBean }
}

```

### Retrieving a Bean by Name

```kotlin
val retrievedBean = getBeanByName("testBean")
```

### Retrieving a Bean by Type

```kotlin
val retrievedBean = getBean<String>()
```

