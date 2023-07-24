
<h1 align="center">Amplitude Add-on implementation</h1></br>
<p align="center">
   <img src="https://github.com/aminekarimii/analytiks/assets/20410115/1f330afd-c805-4d54-9b74-654192f019b3" width="25%" />
</p>

## Configuration
| Field Name                 | Description                                    | Default Value |
|----------------------------|------------------------------------------------|---------------|
| token                      | The token used for tracking events.            | -             |
| serverGeoZone              | The server geo zone for Amplitude server.      | ServerGeoZone.EU |
| optOut                     | Indicates whether opt-out is enabled.         | false         |
| minTimeBetweenSessionsMillis | Minimum time between sessions in milliseconds. | 10000         |

Note: The default values mentioned above can be overridden by providing custom values during initialization.
## Create Add-on
1. Download Amplitude analytics add-on:
```gradle
implementation 'io.github.aminekarimii:analytiks-amplitude:1.0.0'
```
2. Create your own client:
``` kotlin
class AmplitudeClient(
    token: "TOUR_TOKEN",
    serverGeoZone = ServerGeoZone.EU,
    optOut = false,
    minTimeBetweenSessionsMillis = 10000
)
```

### Check the official documentation for more details: 
➡️ [Official amplitude analytics](https://www.docs.developers.amplitude.com/data/sdks/sdk-quickstart/)
