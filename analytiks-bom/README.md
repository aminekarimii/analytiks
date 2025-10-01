# Analytiks BOM (Bill of Materials)

The Analytiks BOM (Bill of Materials) provides a centralized way to manage versions of all Analytiks modules in your project.

## What is a BOM?

A BOM is a special Maven module that provides dependency management for a set of related artifacts. By importing the Analytiks BOM, you can:

- **Manage versions centrally**: Declare the BOM version once, and all Analytiks modules will use compatible versions
- **Avoid version conflicts**: Ensure all Analytiks modules work together correctly
- **Simplify dependency declarations**: No need to specify version numbers for individual modules

## Usage

### Gradle (Kotlin DSL)

```kotlin
dependencies {
    // Import the BOM - automatically includes analytiks-core and analytiks
    implementation(platform("io.github.aminekarimii:analytiks-bom:1.3.0"))

    // Core modules (analytiks-core and analytiks) are automatically included!
    // No need to add them explicitly

    // Add addon modules as needed (without version numbers)
    implementation("io.github.aminekarimii:analytiks-appsflyer")
    implementation("io.github.aminekarimii:analytiks-addon-amplitude")
    implementation("io.github.aminekarimii:analytiks-addon-segment")
}
```

### Gradle (Groovy DSL)

```groovy
dependencies {
    // Import the BOM - automatically includes analytiks-core and analytiks
    implementation platform('io.github.aminekarimii:analytiks-bom:1.3.0')

    // Core modules (analytiks-core and analytiks) are automatically included!
    // No need to add them explicitly

    // Add addon modules as needed (without version numbers)
    implementation 'io.github.aminekarimii:analytiks-appsflyer'
    implementation 'io.github.aminekarimii:analytiks-addon-amplitude'
    implementation 'io.github.aminekarimii:analytiks-addon-segment'
}
```

### Maven

```xml
<dependencyManagement>
    <dependencies>
        <!-- Import the BOM - automatically includes analytiks-core and analytiks -->
        <dependency>
            <groupId>io.github.aminekarimii</groupId>
            <artifactId>analytiks-bom</artifactId>
            <version>1.3.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <!-- Core modules (analytiks-core and analytiks) are automatically included! -->
    <!-- No need to add them explicitly -->

    <!-- Add addon modules as needed (without version numbers) -->
    <dependency>
        <groupId>io.github.aminekarimii</groupId>
        <artifactId>analytiks-appsflyer</artifactId>
    </dependency>

    <dependency>
        <groupId>io.github.aminekarimii</groupId>
        <artifactId>analytiks-addon-amplitude</artifactId>
    </dependency>
</dependencies>
```

## Included Modules

The BOM manages versions for the following modules:

### Core Modules (Automatically Included)
When you import the BOM, these modules are **automatically included** in your project:
- `analytiks-core` - Core analytics functionality
- `analytiks` - Main Analytiks library

### Addon Modules (Optional)
These modules are available with managed versions, but you must explicitly add them:
- `analytiks-addon-amplitude` - Amplitude integration
- `analytiks-appsflyer` - AppsFlyer integration
- `analytiks-addon-appvisor` - AppVisor integration
- `analytiks-addon-azureinsight` - Azure Application Insights integration
- `analytiks-addon-googleanalytics` - Google Analytics integration
- `analytiks-addon-mixpanel` - Mixpanel integration
- `analytiks-addon-segment` - Segment integration
- `analytiks-addon-timber` - Timber logging integration

## Benefits

### Version Consistency
All modules are tested together and guaranteed to be compatible when using the BOM.

### Simplified Upgrades
To upgrade all Analytiks modules, simply change the BOM version:

```kotlin
// Before
implementation(platform("io.github.aminekarimii:analytiks-bom:1.3.0"))

// After - all modules automatically upgrade
implementation(platform("io.github.aminekarimii:analytiks-bom:1.4.0"))
```

### Reduced Configuration
No need to maintain version numbers for each individual module in your build files.

## Version Override

If you need to use a different version for a specific module, you can still specify it explicitly:

```kotlin
dependencies {
    implementation(platform("io.github.aminekarimii:analytiks-bom:1.3.0"))

    // Core modules are automatically included with BOM version

    // Override addon with specific version if needed
    implementation("io.github.aminekarimii:analytiks-appsflyer:1.2.0")
}
```

## Publishing

The BOM is published to Maven Central alongside other Analytiks modules.

To publish the BOM:

```bash
./gradlew publishModule -PmoduleName=analytiks-bom
```

Or using the batch script:

```bash
publish.bat analytiks-bom
```

## License

Apache 2.0 License - See [LICENSE](../LICENSE) for details.

