package com.analytiks.addon.mixpanel

import com.analytiks.core.ConfigurationFile

class MixpanelConfigurationProps(
    token: String,
    val trackAutomaticEvents: Boolean = true
) : ConfigurationFile(token)