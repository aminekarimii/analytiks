package com.analytiks

import android.content.Context
import com.analytiks.core.CoreAddon

class CoreAddon1(base: BaseCoreAddonImpl = BaseCoreAddonImpl()) : CoreAddon by base
class CoreAddon2(base: BaseCoreAddonImpl = BaseCoreAddonImpl()) : CoreAddon by base
class CoreAddon3(base: BaseCoreAddonImpl = BaseCoreAddonImpl()) : CoreAddon by base

class BaseCoreAddonImpl : CoreAddon {
    override fun initialize(context: Context) = Unit

    override fun reset() = Unit
}