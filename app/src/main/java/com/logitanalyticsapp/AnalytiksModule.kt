package com.logitanalyticsapp

import com.analytiks.Analytiks
import com.analytiks.addon.appvisor.ui.MainActivity
import com.analytiks.addon.mixpanel.MixpanelAnalyticsClient
import com.analytiks.segment.SegmentAnalyticsClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AnalytiksModule {

    @Provides
    fun provideAnalytiksBuilder() = Analytiks.Builder()
        .addClient(CustomAnalytiksAddon())
        .addClient(MixpanelAnalyticsClient(token = "YOUR_TOKEN"))
        .addClient(
            SegmentAnalyticsClient(
                token = "YOUR_TOKEN",
                flushIntervalInSeconds = 5,
                trackApplicationLifecycleEvents = true,
            )
        )
        .addInterceptor(MainActivity.initialize())

    @Provides
    @Singleton
    fun provideAnalytiks(builder: Analytiks.Builder): Analytiks {
        return builder.build()
    }

}