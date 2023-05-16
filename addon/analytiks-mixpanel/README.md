# Mixpanel Add-on implementation
## Configuration
| Field Name            | Description                                                         | Default Value        |
|-----------------------|---------------------------------------------------------------------|----------------------|
| token                 | The token used for tracking events.                                 | -                    |
| optOutTrackingDefault | Determines whether tracking is enabled by default for new users.    | false                |
| superProperties       | Additional properties to be included with every tracked event.      | null                 |
| instanceName          | A custom name for the analytics instance (optional).                | null                 |
| trackAutomaticEvents  | Controls automatic event tracking (e.g., screen views, app opens).  | true                 |

Note: The default values mentioned above can be overridden by providing custom values during initialization.
## Create Add-on
1. Download Mixpanel add-on:
```gradle
implementation 'com.github.aminekarimii.analytiks:analytiks-mixpanel:0.1.0-beta'
```
2. Create your own mixpanel client
``` kotlin
val mixpanel = MixpanelAnalyticsClient(
   token: "YOUR_TOKEN",
   optOutTrackingDefault: = false,
   trackAutomaticEvents = true
) 
```

### Check the official documentation for more details: 
➡️ [Official documentation](https://developer.mixpanel.com/docs/android)

