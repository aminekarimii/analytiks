# <p align="center">📊 Analytiks</p>

<p align="center">
  <strong>A unified Android analytics library that centralizes multiple analytics services</strong>
</p>

<p align="center">
  <a href="https://github.com/aminekarimii/analytiks/actions">
    <img alt="Build Status" src="https://github.com/aminekarimii/analytiks/workflows/Android%20CI/badge.svg"/>
  </a>
  <a href="https://maven-badges.herokuapp.com/maven-central/io.github.aminekarimii/analytiks">
    <img alt="Maven Central" src="https://img.shields.io/maven-central/v/io.github.aminekarimii/analytiks">
  </a>
  <a href="https://github.com/aminekarimii/analytiks/blob/main/LICENSE">
    <img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg">
  </a>
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/20410115/228402805-3309d17a-0bc5-4404-90f8-20c9b30e33a9.png" alt="Analytiks Banner" width="80%">
</p>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Installation](#-installation)
- [Quick Start](#-quick-start)
- [Core Features](#-core-features)
- [Supported Analytics SDKs](#-supported-analytics-sdks)
- [AnalytiksVisor - Event Monitoring](#-analytiksvisor---event-monitoring)
- [Advanced Usage](#-advanced-usage)
- [Contributing](#-contributing)
- [Contact](#-contact)
- [License](#-license)

---

## 💭 Overview

Analytiks is a powerful Android library designed to simplify analytics integration by centralizing multiple analytics services into a single, unified interface. Instead of managing multiple SDKs and their different APIs, Analytiks provides a consistent way to track events, identify users, and manage analytics across your entire application.

### Why Analytiks?

- **Single Implementation**: Write once, use with multiple analytics providers
- **Modular Architecture**: Add only the analytics services you need
- **Debug-Friendly**: Built-in logging for development and testing
- **Lightweight**: Minimal impact on your app's size and performance
- **Easy Migration**: Switch between analytics providers without code changes

<p align="center">
  <img src="https://user-images.githubusercontent.com/20410115/225161402-d3a7d24f-da0d-4360-abab-fe86c68f0214.png" alt="Library Architecture" width="70%">
</p>

---

## ✨ Key Features

- 🔧 **Easy Integration** - Single API for multiple analytics services
- 📦 **Modular Design** - Add only the providers you need
- 🐛 **Debug Mode** - Console logging for development
- 🚀 **Performance Optimized** - Minimal overhead and smart batching
- 🔄 **Provider Agnostic** - Switch providers without changing your code
- 📱 **Real-time Monitoring** - Built-in event viewer with AnalytiksVisor

### Option 1: Using BOM (Recommended)

The BOM (Bill of Materials) automatically includes core modules and manages versions:

```gradle
dependencies {
    // Import the BOM - automatically includes analytiks-core and analytiks
    implementation platform('io.github.aminekarimii:analytiks-bom:1.4.0')

    // Add addons as needed (without version numbers)
    implementation 'io.github.aminekarimii:analytiks-appsflyer'
    implementation 'io.github.aminekarimii:analytiks-addon-amplitude'
    implementation 'io.github.aminekarimii:analytiks-addon-googleanalytics'
    implementation 'io.github.aminekarimii:analytiks-addon-mixpanel'
    implementation 'io.github.aminekarimii:analytiks-addon-timber'
    implementation 'io.github.aminekarimii:analytiks-addon-segment'
}
```

### Option 2: Manual Version Management

If you prefer to manage versions manually:

```gradle
dependencies {
    // Core libraries
    implementation 'io.github.aminekarimii:analytiks:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-core:1.4.0'

    // Analytics Providers (Add as needed)
    implementation 'io.github.aminekarimii:analytiks-addon-googleanalytics:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-addon-mixpanel:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-addon-segment:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-addon-amplitude:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-appsflyer:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-addon-timber:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-addon-appvisor:1.4.0'
}
```

> **Note**: Check [Maven Central](https://search.maven.org/search?q=g:io.github.aminekarimii) for the latest version.

---

## 🚀 Quick Start

### 1. Initialize Analytiks

In your `Activity` or `Application` class:

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var analytiks: Analytiks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Build your analytics configuration
        analytiks = Analytiks.Builder()
            .addClient(GoogleAnalyticsClient()) // Firebase Analytics
            .addClient(MixpanelAnalyticsClient(token = "YOUR_MIXPANEL_TOKEN"))
            .addClient(
                SegmentAnalyticsClient(
                    token = "YOUR_SEGMENT_TOKEN",
                    flushIntervalInSeconds = 5,
                    trackApplicationLifecycleEvents = true
                )
            )
            .addClient(TimberAnalyticsClient()) // For debug logging
            .build()
    }
}
```

### 2. Initialize the Library

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // ... configuration above
    
    // Initialize all configured analytics providers
    analytiks.initialize(applicationContext)
}
```

### 3. Start Tracking Events

```kotlin
// Track a simple event
analytiks.logEvent("user_signup")

// Track an event with properties
analytiks.logEvent(
    eventName = "purchase_completed",
    properties = mapOf(
        "product_id" to "12345",
        "price" to 29.99,
        "currency" to "USD"
    )
)

// Identify a user
analytiks.identify(userId = "user_12345")

// Set user properties
analytiks.setUserProperty("subscription_type", "premium")

// Send all queued events immediately
analytiks.pushAll()
```

---

## 🔧 Core Features

### Event Tracking
```kotlin
// Simple event
analytiks.logEvent("button_clicked")

// Event with custom properties
analytiks.logEvent("video_played", mapOf(
    "video_id" to "abc123",
    "duration" to 120,
    "quality" to "HD"
))
```

### User Management
```kotlin
// Identify user with custom ID
analytiks.identify("user_12345")

// Identify with auto-generated UUID
analytiks.identify()

// Set user properties
analytiks.setUserProperty("age", 25)
analytiks.setUserProperty("plan", "premium")
```

### Data Management
```kotlin
// Force send all queued events
analytiks.flush()

// Reset user data and clear queue
analytiks.reset()
```

---

## 🗃 Supported Analytics SDKs

| Service | Status | Implementation Guide | Official Documentation |
|---------|--------|---------------------|------------------------|
| **Google Analytics/Firebase** | ✅ Available | [Setup Guide](./addon/analytiks-googleanalytics/README.md) | [Firebase Docs](https://firebase.google.com/docs/analytics/get-started?platform=android) |
| **Segment** | ✅ Available | [Setup Guide](./addon/analytiks-segment/README.md) | [Segment Docs](https://segment.com/docs/connections/sources/catalog/libraries/mobile/kotlin-android/) |
| **Mixpanel** | ✅ Available | [Setup Guide](./addon/analytiks-mixpanel/README.md) | [Mixpanel Docs](https://developer.mixpanel.com/docs/android) |
| **Amplitude** | ✅ Available | [Setup Guide](./addon/analytiks-amplitude/README.md) | [Amplitude Docs](https://www.docs.developers.amplitude.com/data/sdks/sdk-quickstart/) |
| **Timber (Local Logging)** | ✅ Available | Built-in | [Timber GitHub](https://github.com/JakeWharton/timber) |
| **Custom Analytics** | ✅ Available | [Create Custom Addon](./analytiks-core) | - |
| **AppsFlyer**  | ✅ | <a href="./addon/analytiks-appsflyer/README.md">AppsFlyer Addon doc</a> | <a href="https://dev.appsflyer.com/hc/docs/android-sdk">AppsFlyer doc</a> |
| **Flurry Analytics** | 🚧 Coming Soon | - | - |
| **CleverTap** | 🚧 Coming Soon | - | - |
| **MoEngage** | 🚧 Coming Soon | - | - |
| **Adjust** | 🚧 Coming Soon | - | - |
| **AppsFlyer** | 🚧 Coming Soon | - | - |

### Request New Integrations
Can't find your analytics service? [Open an issue](https://github.com/aminekarimii/analytiks/issues/new) with the service name and documentation link.

---

## 🔍 AnalytiksVisor - Event Monitoring

AnalytiksVisor provides real-time event monitoring and debugging capabilities, allowing you to see exactly what analytics events are being tracked in your application.

### Features
- **📊 Real-time Event Visualization** - Monitor events as they happen
- **🕐 Timestamp Tracking** - Precise event timing information
- **📋 Event Details** - Complete event properties and metadata
- **🚧 Coming Soon**: Event sharing and push notifications

### Setup

1. **Add the dependency**:

Using BOM (Recommended):
```gradle
dependencies {
    implementation platform('io.github.aminekarimii:analytiks-bom:1.4.0')
    implementation 'io.github.aminekarimii:analytiks-addon-appvisor'
}
```

Or with manual version:
```gradle
implementation 'io.github.aminekarimii:analytiks-addon-appvisor:1.4.0'
```

2. **Initialize with interceptor**:
```kotlin
analytiks = Analytiks.Builder()
    .addInterceptor(AppVisorActivity.initialize())
    .addClient(/* your analytics clients */)
    .build()
```

3. **Create app shortcut** (optional):
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AnalytiksVisor.createShortcut(this)
    }
}
```

### Demo
<div align="center">
  <video src="https://github.com/aminekarimii/analytiks/assets/20410115/944a0f7e-da56-4f64-907a-8df0d803e3b7" controls width="70%"></video>
</div>

---

## 🔨 Advanced Usage


You can create custom analytics providers by implementing the `AnalyticsClient` interface:

```kotlin
class CustomAnalyticsClient : AnalyticsClient {
    override fun initialize(context: Context) {
        // Initialize your custom analytics SDK
    }
    
    override fun logEvent(eventName: String, properties: Map<String, Any>?) {
        // Implement event logging
    }
    
    override fun identify(userId: String) {
        // Implement user identification
    }
    
    override fun setUserProperty(key: String, value: Any) {
        // Implement user property setting
    }
    
    override fun flush() {
        // Implement force flush
    }
    
    override fun reset() {
        // Implement reset functionality
    }
}
```

### Debug Mode

Enable debug logging in development builds:

```kotlin
analytiks = Analytiks.Builder()
    .addClient(TimberAnalyticsClient()) // Logs to console
    .build()
```

---

## 🤝 Contributing

We welcome contributions! Here's how you can help:

1. **Fork the repository**
2. **Create a feature branch** (`git checkout -b feature/amazing-feature`)
3. **Commit your changes** (`git commit -m 'Add amazing feature'`)
4. **Push to the branch** (`git push origin feature/amazing-feature`)
5. **Open a Pull Request**

### Development Setup
```bash
git clone https://github.com/aminekarimii/analytiks.git
cd analytiks
./gradlew build
```

---

## 📫 Contact

**Amine Karimi** - Library Creator & Maintainer

- 📧 **Email**: [aminekarimi1998@gmail.com](mailto:aminekarimi1998@gmail.com)
- 💼 **LinkedIn**: [aminekarimi](https://www.linkedin.com/in/aminekarimi)
- 🐦 **Twitter**: [@aminekarimii](https://twitter.com/aminekarimii)
- 🐙 **GitHub**: [aminekarimii](https://github.com/aminekarimii)

---

## 📄 License

```
Apache License 2.0

Copyright 2022 KARIMI Amine

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

---

<p align="center">
  <strong>⭐ If you find Analytiks helpful, please consider giving it a star on GitHub! ⭐</strong>
</p>
