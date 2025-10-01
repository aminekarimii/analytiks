<h1 align="center">Segment Add-on implementation</h1></br>
<p align="center">
   <img src="https://github.com/aminekarimii/analytiks/assets/20410115/6f8072f2-ca92-43a9-a974-dd0627c554c3" width="20%" />
</p>

## Configuration
| Field Name                      | Description                                                                     | Default Value |
|---------------------------------|---------------------------------------------------------------------------------|---------------|
| token                           | The token used for tracking events.                                             | -             |
| collectDeviceId                 | Determines whether to collect the device ID.                                    | true          |
| trackApplicationLifecycleEvents | Controls automatic tracking of application lifecycle events.                    | false         |
| useLifecycleObserver            | Determines whether to use the Lifecycle Observer for event tracking.            | false         |
| flushIntervalInSeconds          | The time interval (in seconds) for flushing queued events to the server.        | null          |
| flushAt                         | The maximum number of events to be queued before flushing to the server.        | null          |
| tag                             | A custom tag to identify the analytics instance (optional).                     | null          |

Note: The default values mentioned above can be overridden by providing custom values during initialization.

## Installation

### Using BOM (Recommended)
```gradle
dependencies {
    // Import the BOM - automatically includes analytiks-core and analytiks
    implementation platform('io.github.aminekarimii:analytiks-bom:1.4.0')

    // Add Segment addon (version managed by BOM)
    implementation 'io.github.aminekarimii:analytiks-addon-segment'
}
```

### Manual Version Management
```gradle
implementation 'io.github.aminekarimii:analytiks-addon-segment:1.4.0'
```

## Create Add-on
Create your own Segment client:
``` kotlin
val segment = SegmentAnalyticsClient(
    token = "YOUR_TOKEN",
    collectDeviceId = true,
    trackApplicationLifecycleEvents = false,
    useLifecycleObserver = false,
    flushIntervalInSeconds = null,
    flushAt = null,
    tag = null
) 
```

### Check the official documentation for more details: 
➡️ [Official documentation](https://segment.com/docs/sources/mobile/android/)

Feel free to customize the content and formatting as per your preferences.
