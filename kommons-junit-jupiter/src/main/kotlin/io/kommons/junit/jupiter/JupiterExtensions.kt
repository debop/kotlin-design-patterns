package io.kommons.junit.jupiter

import org.junit.jupiter.api.extension.ExtensionContext


internal fun ExtensionContext.namespace(clazz: Class<*>): ExtensionContext.Namespace =
    ExtensionContext.Namespace.create(clazz, this)

internal fun ExtensionContext.store(clazz: Class<*>): ExtensionContext.Store =
    getStore(namespace(clazz))