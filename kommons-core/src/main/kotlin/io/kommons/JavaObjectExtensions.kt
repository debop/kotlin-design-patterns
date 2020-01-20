package io.kommons

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
fun Any.notify() {
    synchronized(this) {
        runCatching { (this as Object).notify() }
    }
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
fun Any.notifyAll() {
    synchronized(this) {
        runCatching { (this as Object).notifyAll() }
    }
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
fun Any.wait() {
    synchronized(this) {
        runCatching { (this as Object).wait() }
    }
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
fun Any.wait(timeoutMs: Long) {
    synchronized(this) {
        runCatching { (this as Object).wait(timeoutMs) }
    }
}

@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN")
fun Any.wait(timeoutMs: Long, nanos: Int) {
    synchronized(this) {
        runCatching { (this as Object).wait(timeoutMs, nanos) }
    }
}