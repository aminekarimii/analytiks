package com.analytiks

import com.analytiks.core.CoreAddon
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test


class AnalytiksTest {
    lateinit var analytiks: Analytiks
    private val addon1  = CoreAddon1()
    private val addon2 = CoreAddon2()
    private val addon3 = CoreAddon3()

    @Test
    fun `excludeAddon should exclude the specified addon`() {
        // Arrange
        val addons = listOf(
            addon1,
            addon2,
            addon3,
        )
        analytiks = Analytiks(addons)

        // Act
        val result = with(analytiks) {
            addons.excludeAddon(setOf(CoreAddon2::class.java))
        }

        // Assert
        assertEquals(2, result.size)
        assertEquals(listOf(addon1, addon3), result)
    }

    @Test
    fun `excludeAddon should return the same list if excludedAddons is null`() {
        // Arrange
        val addons = listOf(
            addon1,
            addon2,
            addon3,
        )
        analytiks = Analytiks(addons)

        // Act
        val result = with(analytiks) {
            addons.excludeAddon(null)
        }

        // Assert
        assertEquals(addons, result)
    }

    @Test
    fun `excludeAddon should return an empty list if all addons are excluded`() {
        // Arrange
        val addons = listOf(
            addon1,
            addon2,
            addon3,
        )
        analytiks = Analytiks(addons)

        // Act
        val result = with(analytiks) {
            addons.excludeAddon(
                setOf(CoreAddon1::class.java, CoreAddon2::class.java, CoreAddon3::class.java)
            )
        }

        // Assert
        assertTrue(result.isEmpty())
    }
}
