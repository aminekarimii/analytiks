# Analytiks
A lightweight Analytics library that identify the user, log events and dispatch the data at one.

## Description
An android library that centralizes analytics services in one place can be a useful tool for developers who want to track the usage and performance of their app.
Should be easy as it sounds, a single implementation to start with the base analytics core features, and then you can add each analytic service separately (to preserve library size).
A debug analytics mode that can log the same properties in the debug console.

![Scheme of the library logic](https://user-images.githubusercontent.com/20410115/224169222-e7cc3ca6-40c8-4fe9-a01d-2e19d817a0af.png)

## Features [🚧 wip]
The list of features provided by the library  
- **initialization:** `init` Initialize the "analytiks" library, along with its sub-libraries, during the initialization process.  
- **Identify user:** `identify` Identify the current user by the given id or a random uuid in case of an empty one.  
- **Log event:** `event` send/save an event with a name and an optional set of properties.  
- **Reset:** `reset` the plugins and remove the default users configuration.  

## Supported analytics SDKs
Here's a list of the most known Analytic services that we will supoort in our library.  
- [ ] Google Analytics
- [ ] Firebase Analytics
- [ ] Mixpanel
- [ ] Flurry Analytics
- [ ] Amplitude
- [ ] App Annie
- [ ] Localytics
- [ ] AppsFlyer
- [ ] Sensor Tower
- [ ] Onesignal
- [x] Timber - For local event logging  

## License 🔖
```
    Apache 2.0 License


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
