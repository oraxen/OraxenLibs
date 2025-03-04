package com.boy0000.oraxenlibs.textcomponents

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

object OraxenTextComponents {
    private val resolvers = mutableListOf(TagResolver.standard())
    var globalResolver = TagResolver.resolver(resolvers)
        private set

    fun addResolver(resolver: TagResolver) {
        resolvers.add(resolver)
        globalResolver = TagResolver.resolver(resolvers)
    }
}
