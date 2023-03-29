package com.analytiks.core.formatters

import com.analytiks.core.model.Param
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BundleFormatterTest {
    private val bundleStrategy = BundleFormatStrategy()

    @Test
    fun testFormatProperties() {
        val properties = arrayOf(
            Param("property1", "value1"),
            Param("property2", "value2")
        )

        val result = bundleStrategy(*properties)

        assertEquals("value1", result.getString("property1"))
        assertEquals("value2", result.getString("property2"))
    }
}