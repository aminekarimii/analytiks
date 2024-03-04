package com.logitanalyticsapp

import com.analytiks.Analytiks
import com.analytiks.addon.appvisor.ui.AppVisorActivity
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
        .addInterceptor(AppVisorActivity.initialize())
        .addClient(CustomAnalytiksAddon())
        .addClient(MixpanelAnalyticsClient(token = "YOUR_TOKEN"))
        .addClient(
            SegmentAnalyticsClient(
                token = "YOUR_TOKEN",
                flushIntervalInSeconds = 5,
                trackApplicationLifecycleEvents = true,
            )
        )

    @Provides
    @Singleton
    fun provideAnalytiks(builder: Analytiks.Builder): Analytiks {
        return builder.build()
    }

}