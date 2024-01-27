package com.boy0000.oraxenlibs.nms.nbt

import com.boy0000.oraxenlibs.nms.aliases.NMSItemStack
import com.boy0000.oraxenlibs.nms.aliases.toNMS
import net.minecraft.nbt.CompoundTag
import org.bukkit.inventory.ItemStack

val NMSItemStack.hasPDC: Boolean get() = tag?.contains("PublicBukkitValues") == true
val NMSItemStack.fastPDC: WrappedPDC?
    get() {
        return WrappedPDC((tag?.get("PublicBukkitValues") ?: return null) as CompoundTag)
    }

val ItemStack.fastPDC get() = toNMS()?.fastPDC
