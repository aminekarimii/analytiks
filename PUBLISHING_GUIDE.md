# Publishing Guide

This guide explains how to publish individual modules or all modules to Maven Central.

## Publishing Methods

There are two ways to publish modules:

1. **GitHub Actions (Recommended)** - Automated publishing via CI/CD
2. **Local Publishing** - Manual publishing from your machine

For GitHub Actions setup, see [.github/RELEASE_SETUP.md](.github/RELEASE_SETUP.md).

## GitHub Actions Publishing (Recommended)

### Quick Start

1. **Set up GitHub Secrets** (one-time setup):
   - See [.github/RELEASE_SETUP.md](.github/RELEASE_SETUP.md) for detailed instructions
   - Required secrets: `SIGNING_KEY_ID`, `SIGNING_PASSWORD`, `SIGNING_KEY_BASE64`, `CENTRAL_PORTAL_USERNAME`, `CENTRAL_PORTAL_PASSWORD`

2. **Publish via GitHub Actions**:
   - Go to **Actions** tab → **Release to Maven Central**
   - Click **Run workflow**
   - Select module to publish
   - Click **Run workflow**

3. **Or publish via Git tag**:
   ```bash
   git tag v1.2.0
   git push origin v1.2.0
   ```

See [.github/RELEASE_SETUP.md](.github/RELEASE_SETUP.md) for complete setup instructions.

---

## Local Publishing

### Prerequisites

1. **Signing Key**: Ensure you have a valid PGP signing key configured in `local.properties`:
   ```properties
   signing.keyId=YOUR_KEY_ID
   signing.password=YOUR_KEY_PASSPHRASE
   signing.key=signing.key.txt
   ```

2. **Maven Central Credentials**: Configure your credentials in `local.properties`:
   ```properties
   centralPortalUsername=YOUR_USERNAME
   centralPortalPassword=YOUR_PASSWORD
   ```

## Publishing Individual Modules

### Method 1: Using the Batch Script (Windows)

The easiest way to publish a single module:

```bash
# Publish a specific module
publish.bat analytiks-appsflyer

# List all available modules
publish.bat list

# Publish all addon modules
publish.bat all-addons

# Publish core modules
publish.bat core
```

### Method 2: Using Gradle Directly

```bash
# Publish a specific module
./gradlew publishModule -PmoduleName=analytiks-appsflyer

# List all available modules
./gradlew listModules

# Publish all addon modules
./gradlew publishAllAddons

# Publish core modules
./gradlew publishCore
```

## Available Modules

### Core Modules
- `analytiks-core` - Core analytics functionality
- `analytiks` - Main analytics library
- `analytiks-bom` - Bill of Materials for version management

### Addon Modules
- `analytiks-appsflyer` - AppsFlyer integration
- `analytiks-addon-amplitude` - Amplitude integration
- `analytiks-addon-appvisor` - AppVisor integration
- `analytiks-addon-azureinsight` - Azure Application Insights integration
- `analytiks-addon-googleanalytics` - Google Analytics integration
- `analytiks-addon-mixpanel` - Mixpanel integration
- `analytiks-addon-segment` - Segment integration
- `analytiks-addon-timber` - Timber logging integration

## Publishing Workflow

### Publishing a Single Module

1. **Build and test the module**:
   ```bash
   ./gradlew :addon:analytiks-appsflyer:build
   ./gradlew :addon:analytiks-appsflyer:test
   ```

2. **Publish the module**:
   ```bash
   publish.bat analytiks-appsflyer
   ```
   
   Or using Gradle:
   ```bash
   ./gradlew publishModule -PmoduleName=analytiks-appsflyer
   ```

3. **Verify on Maven Central**:
   - Go to https://central.sonatype.com/
   - Search for `io.github.aminekarimii:analytiks-appsflyer`
   - Check that version 1.1.0 is available

### Publishing All Addon Modules

If you want to publish all addon modules at once:

```bash
publish.bat all-addons
```

Or:

```bash
./gradlew publishAllAddons
```

### Publishing Core Modules

To publish the core modules (analytiks-core and analytiks):

```bash
publish.bat core
```

Or:

```bash
./gradlew publishCore
```

## Troubleshooting

### Issue: "Module not found"

Make sure you're using the correct module name. Run `publish.bat list` to see all available modules.

### Issue: "Signing failed"

Check that:
1. Your `signing.password` in `local.properties` is the correct PGP key passphrase (not the key ID or fingerprint)
2. The `signing.key.txt` file exists and contains your PGP private key
3. The `signing.keyId` matches your key (last 8 characters of the fingerprint)

### Issue: "Authentication failed"

Verify your Maven Central credentials in `local.properties`:
- `centralPortalUsername` should be your Central Portal username
- `centralPortalPassword` should be your Central Portal password (or token)

### Issue: "Duplicate artifacts"

This usually happens when publishing multiple modules simultaneously. Use the single module publishing approach instead:

```bash
# Instead of:
./gradlew build publishToSonatype closeAndReleaseStagingRepositories

# Do:
publish.bat analytiks-appsflyer
publish.bat analytiks-amplitude
# etc.
```

### Issue: "Validation failed"

Some modules may have validation errors. To see which modules are failing:

1. Check the Maven Central validation report
2. Fix the issues in the failing modules
3. Publish only the modules that pass validation

## Publishing Strategy

### Recommended Approach

1. **Publish core modules first**:
   ```bash
   publish.bat core
   ```

2. **Publish addon modules one by one**:
   ```bash
   publish.bat analytiks-appsflyer
   publish.bat analytiks-amplitude
   # etc.
   ```

3. **Or publish only the modules you need**:
   ```bash
   publish.bat analytiks-appsflyer
   ```

### Why Publish Individually?

- **Avoid validation errors**: Some modules may have issues that prevent publishing
- **Faster feedback**: You know immediately if a specific module fails
- **Easier debugging**: Errors are isolated to a single module
- **Selective publishing**: Only publish the modules you've updated

## Version Management

The version is defined in `gradle.properties`:

```properties
PUBLISH_VERSION=1.1.0
```

To publish a new version:

1. Update `PUBLISH_VERSION` in `gradle.properties`
2. Commit the change
3. Publish the modules
4. Tag the release in Git:
   ```bash
   git tag -a v1.1.0 -m "Release version 1.1.0"
   git push origin v1.1.0
   ```

## CI/CD Integration

You can integrate the publishing scripts into your CI/CD pipeline:

```yaml
# Example GitHub Actions workflow
- name: Publish AppsFlyer module
  run: ./gradlew publishModule -PmoduleName=analytiks-appsflyer
  env:
    SIGNING_KEY_ID: ${{ secrets.SIGNING_KEY_ID }}
    SIGNING_PASSWORD: ${{ secrets.SIGNING_PASSWORD }}
    SIGNING_KEY: ${{ secrets.SIGNING_KEY }}
    CENTRAL_PORTAL_USERNAME: ${{ secrets.CENTRAL_PORTAL_USERNAME }}
    CENTRAL_PORTAL_PASSWORD: ${{ secrets.CENTRAL_PORTAL_PASSWORD }}
```

## Quick Reference

| Command | Description |
|---------|-------------|
| `publish.bat <module>` | Publish a specific module |
| `publish.bat list` | List all available modules |
| `publish.bat all-addons` | Publish all addon modules |
| `publish.bat core` | Publish core modules |
| `./gradlew listModules` | List all available modules |
| `./gradlew publishModule -PmoduleName=<module>` | Publish a specific module |
| `./gradlew publishAllAddons` | Publish all addon modules |
| `./gradlew publishCore` | Publish core modules |

## Bill of Materials (BOM)

The Analytiks BOM provides centralized version management for all modules. See [analytiks-bom/README.md](analytiks-bom/README.md) for detailed usage instructions.

### Publishing the BOM

```bash
# Using batch script
publish.bat analytiks-bom

# Using Gradle
./gradlew publishModule -PmoduleName=analytiks-bom
```

### Using the BOM in Projects

```kotlin
dependencies {
    // Import the BOM
    implementation(platform("io.github.aminekarimii:analytiks-bom:1.2.0"))

    // Add modules without version numbers
    implementation("io.github.aminekarimii:analytiks-core")
    implementation("io.github.aminekarimii:analytiks-appsflyer")
}
```

## Support

If you encounter issues:

1. Check this guide for troubleshooting steps
2. Review the Gradle output for error messages
3. Check the Maven Central validation report
4. Ensure all prerequisites are correctly configured

