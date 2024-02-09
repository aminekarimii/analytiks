# Core Analytiks 
## Create your custom Addon
1. Create a new kotlin file, and create a new class
2. Implement ```CoreAddon``` interface
3. Extend your analytics addon with the available features:

| Extention   |     Features    |
| --------- | ------------- |
| **EventsExtension**  | Provides support for custom event tracking via ```logEvent``` method.  |
| **UserProfileExtension**  | Optional extension interface that can be implemented by analytics clients to provide support for user profile tracking. it provides three extensions: ```identify```, ```setUserProperty```, and ```setUserPropertyOnce```.
| **AnalyticsDataTransmitterExtension**  | Interface for handling the communication with the analytics server. There is one single method right now, ```pushAll``` |
