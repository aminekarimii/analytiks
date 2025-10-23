# Sentry Addon for Analytiks

Integration of [Sentry](https://sentry.io/) error tracking and performance monitoring with the Analytiks library.

## Features

- ✅ **Event Tracking**: Track custom events as Sentry breadcrumbs
- ✅ **User Identification**: Associate events with specific users
- ✅ **User Properties**: Set user email, username, IP address, and custom data
- ✅ **Screen Tracking**: Track screen views
- ✅ **Error Context**: Enrich error reports with analytics events
- ✅ **Performance Monitoring**: Leverage Sentry's performance tracking capabilities

## Installation

### Using BOM (Recommended)

```gradle
dependencies {
    implementation platform('io.github.aminekarimii:analytiks-bom:1.4.0')
    implementation 'io.github.aminekarimii:analytiks-addon-sentry'
    
    // Sentry Android SDK (if not already included)
    implementation 'io.sentry:sentry-android:7.0.0'
}
```

### Manual Version

```gradle
dependencies {
    implementation 'io.github.aminekarimii:analytiks-core:1.4.0'
    implementation 'io.github.aminekarimii:analytiks:1.4.0'
    implementation 'io.github.aminekarimii:analytiks-addon-sentry:1.4.0'
    
    // Sentry Android SDK
    implementation 'io.sentry:sentry-android:7.0.0'
}
```

## Setup

### 1. Initialize Sentry

First, initialize Sentry in your `Application` class or `AndroidManifest.xml`:

#### Option A: In Application Class

```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        SentryAndroid.init(this) { options ->
            options.dsn = "YOUR_SENTRY_DSN"
            options.environment = if (BuildConfig.DEBUG) "development" else "production"
            options.tracesSampleRate = 1.0
            options.isEnableAutoSessionTracking = true
        }
    }
}
```

#### Option B: In AndroidManifest.xml

```xml
<application>
    <meta-data
        android:name="io.sentry.dsn"
        android:value="YOUR_SENTRY_DSN" />
    <meta-data
        android:name="io.sentry.environment"
        android:value="production" />
</application>
```

### 2. Add to Analytiks

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var analytiks: Analytiks

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        analytiks = Analytiks.Builder()
            .addClient(SentryClient(context = applicationContext))
            .addClient(/* other analytics clients */)
            .build()
            
        analytiks.initialize(applicationContext)
    }
}
```

## Usage

### Track Events

Events are tracked as Sentry breadcrumbs with INFO level:

```kotlin
// Simple event
analytiks.logEvent("button_clicked")

// Event with properties
analytiks.logEvent(
    eventName = "purchase_completed",
    properties = mapOf(
        "product_id" to "12345",
        "price" to 29.99,
        "currency" to "USD"
    )
)
```

### Identify Users

```kotlin
// Identify user
analytiks.identify(userId = "user_12345")
```

### Set User Properties

```kotlin
// Set email
analytiks.setUserProperty("email", "user@example.com")

// Set username
analytiks.setUserProperty("username", "john_doe")

// Set IP address
analytiks.setUserProperty("ip_address", "192.168.1.1")

// Set custom properties
analytiks.setUserProperty("subscription_tier", "premium")
analytiks.setUserProperty("account_age_days", 365)
```

### Track Screens

```kotlin
// Track screen view
analytiks.trackScreen(
    screenName = "HomeScreen",
    properties = mapOf(
        "section" to "main",
        "tab" to "featured"
    )
)
```

### Reset User Data

```kotlin
// Clear user data and breadcrumbs
analytiks.reset()
```

## How It Works

### Event Tracking
- Events are sent to Sentry as **breadcrumbs** with level `INFO`
- Event properties are included in the breadcrumb data
- Breadcrumbs provide context for errors and crashes

### User Management
- User ID is set using Sentry's user identification
- Special properties are mapped to Sentry's User object:
  - `email` → User.email
  - `username` → User.username
  - `ip_address` / `ipaddress` → User.ipAddress
- Custom properties are stored in User.data

### Screen Tracking
- Screen views are tracked as breadcrumbs with category "navigation"
- Screen properties are included in the breadcrumb data

## Benefits of Sentry Integration

1. **Error Context**: Analytics events appear as breadcrumbs in error reports, helping you understand what users were doing before an error occurred

2. **User Tracking**: Associate errors with specific users and their properties

3. **Performance Monitoring**: Combine analytics with Sentry's performance tracking

4. **Release Tracking**: Track analytics across different app versions

5. **Environment Separation**: Separate development and production analytics

## Example: Complete Setup

```kotlin
class MyApplication : Application() {
    private lateinit var analytiks: Analytiks
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Sentry
        SentryAndroid.init(this) { options ->
            options.dsn = "YOUR_SENTRY_DSN"
            options.environment = if (BuildConfig.DEBUG) "development" else "production"
            options.tracesSampleRate = 1.0
            options.isEnableAutoSessionTracking = true
            options.isEnableUserInteractionTracing = true
        }
        
        // Initialize Analytiks with Sentry
        analytiks = Analytiks.Builder()
            .addClient(SentryClient(context = this))
            .addClient(TimberAnalyticsClient()) // For debug logging
            .build()
            
        analytiks.initialize(this)
        
        // Identify user
        analytiks.identify("user_12345")
        analytiks.setUserProperty("email", "user@example.com")
        analytiks.setUserProperty("plan", "premium")
    }
}
```

## Advanced Configuration

### Custom Sentry Options

You can configure Sentry with additional options before initializing Analytiks:

```kotlin
SentryAndroid.init(this) { options ->
    options.dsn = "YOUR_SENTRY_DSN"
    options.environment = "production"
    options.release = "my-app@1.0.0"
    options.dist = "1"
    
    // Performance monitoring
    options.tracesSampleRate = 1.0
    options.profilesSampleRate = 1.0
    
    // Session tracking
    options.isEnableAutoSessionTracking = true
    options.sessionTrackingIntervalMillis = 30000
    
    // User interaction tracking
    options.isEnableUserInteractionTracing = true
    options.isEnableUserInteractionBreadcrumbs = true
    
    // Network tracking
    options.isEnableNetworkEventBreadcrumbs = true
    
    // Before send callback
    options.beforeSend = SentryOptions.BeforeSendCallback { event, hint ->
        // Modify or filter events before sending
        event
    }
}
```

## Resources

- [Sentry Android Documentation](https://docs.sentry.io/platforms/android/)
- [Sentry Breadcrumbs](https://docs.sentry.io/platforms/android/enriching-events/breadcrumbs/)
- [Sentry User Context](https://docs.sentry.io/platforms/android/enriching-events/identify-user/)
- [Analytiks Core Documentation](../../analytiks-core/README.md)

## Support

For issues specific to the Sentry addon, please [open an issue](https://github.com/aminekarimii/analytiks/issues) on GitHub.

For Sentry-specific questions, refer to the [Sentry documentation](https://docs.sentry.io/) or [Sentry support](https://sentry.io/support/).

