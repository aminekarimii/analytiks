# Analytiks
A lightweight Analytics library that identify the user, log events and distpatch the data at one.

## Description
An android library that centralizes analytics services in one place can be a useful tool for developers who want to track the usage and performance of their app.
Should be easy as it sounds, a single implementation to start with the base analytics core features, and then you can add each analytic service separately (to preserve library size).
A debug analytics mode that can log the same properties in the debug console.

## Features [🚧 wip]
The list of features provided by the library  
- **initialization:** `init` Initialize the "analytiks" library, along with its sub-libraries, during the initialization process.  
- **Identify user:** `identify` Identify the current user by the given id or a random uuid in case of an empty one.  
- **Log event:** `event` send/save an event with a name and an optional set of properties.  
- **Reset:** `reset` the plugins and remove the default users configuration.  

## 10 mobile app analytics
1. Google Analytics
2. Firebase Analytics
3. Mixpanel
4. Flurry Analytics
5. Amplitude
6. App Annie
7. Localytics
8. AppsFlyer
9. Sensor Tower
10. Onesignal
