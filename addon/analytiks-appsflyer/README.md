# Analytiks AppsFlyer Addon

This addon integrates AppsFlyer SDK with the Analytiks analytics framework.

## Features

- Event tracking
- User identification
- User properties
- Automatic data transmission

## Setup

### 1. Add dependency

#### Using BOM (Recommended)
```gradle
dependencies {
    // Import the BOM - automatically includes analytiks-core and analytiks
    implementation platform('io.github.aminekarimii:analytiks-bom:1.4.0')

    // Add AppsFlyer addon (version managed by BOM)
    implementation 'io.github.aminekarimii:analytiks-appsflyer'
}
```

#### Manual Version Management
```gradle
dependencies {
    implementation 'io.github.aminekarimii:analytiks-appsflyer:1.4.0'
}
```

### 2. Initialize

```kotlin
val appsFlyerClient = AppsFlyerClient(
    devKey = "YOUR_APPSFLYER_DEV_KEY",
    enableDebugLog = BuildConfig.DEBUG
)

// Add to your analytics setup
analytiks.addClient(appsFlyerClient)
```

## Configuration

### Constructor Parameters

- `devKey`: Your AppsFlyer developer key (required)
- `enableDebugLog`: Enable debug logging (default: false)

## AppsFlyer SDK Version

This addon uses AppsFlyer Android SDK version **6.17.0**.

## Permissions

The following permissions are automatically added:

- `android.permission.INTERNET`
- `android.permission.ACCESS_NETWORK_STATE`

## Usage

Once configured, the AppsFlyer client will automatically:

- Track events logged through the Analytiks framework
- Identify users when `identify()` is called
- Set user properties when `setUserProperty()` is called
- Handle data transmission automatically

## Documentation

For more information about AppsFlyer SDK features, visit:
- [AppsFlyer Android SDK Documentation](https://dev.appsflyer.com/hc/docs/android-sdk)
