package com.analytiks.addon.appvisor.ui.helper

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.util.Log
import androidx.core.content.getSystemService
import com.analytiks.addon.appvisor.R
import com.analytiks.addon.appvisor.ui.AppVisorActivity

public object AnalytiksVisor {
    private const val APP_VISOR_ID = "appVisorId"

    fun createShortcut(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
            return
        }

        val shortcutManager = context.getSystemService<ShortcutManager>() ?: return
        if (shortcutManager.dynamicShortcuts.any { it.id == APP_VISOR_ID }) {
            return
        }

        val shortcut =
            ShortcutInfo.Builder(context, "APP_VISOR_ID")
                .setShortLabel("Analytiks Visor")
                .setLongLabel("Analytiks App visor - Analyze your app events")
                .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher))
                .setIntent(getLaunchIntent(context).setAction(Intent.ACTION_VIEW))
                .build()
        try {
            shortcutManager.addDynamicShortcuts(listOf(shortcut))
        } catch (e: IllegalArgumentException) {
            Log.d("ShortcutManagerTag", "ShortcutManager addDynamicShortcuts failed $e")
        } catch (e: IllegalStateException) {
            Log.d("ShortcutManagerTag", "ShortcutManager addDynamicShortcuts failed $e")
        }
    }

    private fun getLaunchIntent(context: Context): Intent {
        return Intent(context, AppVisorActivity::class.java)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    }
}