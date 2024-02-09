package com.analytiks.addon.appvisor.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.analytiks.addon.appvisor.R
import com.analytiks.addon.appvisor.databinding.ActivityVisorBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVisorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisorBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    companion object {

        /**
         * Create a shortcut to launch Chucker UI.
         * @param context An Android [Context].
         */
        fun createShortcut(context: Context) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                return
            }

            val shortcutManager = context.getSystemService<ShortcutManager>() ?: return
            if (shortcutManager.dynamicShortcuts.any { it.id == "SHORTCUT_ID" }) {
                return
            }


            val shortcut =
                ShortcutInfo.Builder(context, "SHORTCUT_ID")
                    .setShortLabel("chucker_shortcut_label")
                    .setLongLabel("chucker_shortcut_label")
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

        fun getLaunchIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        }
    }
}