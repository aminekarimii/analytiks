
<h1 align="center">Google/Firebase Add-on implementation</h1></br>
<p align="center">
   <img src="https://github.com/aminekarimii/analytiks/assets/20410115/c16c3495-bb8e-4002-8cfe-bb68e3f82761" width="20%" />
</p>

## Configuration
| Field Name                   | Description                                        | Default Value    |
|------------------------------|----------------------------------------------------|------------------|
| isAnalyticsCollectionEnabled | Indicates whether analytics collection is enabled. | true             |
| sessionTimeoutDuration       | Duration of the session timeout in milliseconds.   | null             |
| defaultEventParameters       | Default event parameters for tracking events.      | null             |

Note: The default values mentioned above can be overridden by providing custom values during initialization.
## Create Add-on
1. Download Google analytics add-on:
```gradle
implementation 'com.github.aminekarimii.analytiks:analytiks-googleanalytics:0.1.0-beta'
```
2. Create your own client
``` kotlin
val googleAnalytics = class GoogleAnalyticsClient(
   isAnalyticsCollectionEnabled = true,
   sessionTimeoutDuration = 300L,
   defaultEventParameters = null
)
```

### Check the official documentation for more details: 
➡️ [Official firebase analytics](https://firebase.google.com/docs/analytics/get-started?platform=android)

