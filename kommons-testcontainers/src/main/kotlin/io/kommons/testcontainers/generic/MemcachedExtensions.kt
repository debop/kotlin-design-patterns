package io.kommons.testcontainers.generic

import com.spotify.folsom.BinaryMemcacheClient
import com.spotify.folsom.MemcacheClientBuilder
import com.spotify.folsom.transcoder.ByteArrayTranscoder

@JvmOverloads
fun newFolsomClient(host: String = "localhost", port: Int = 11211, connect: Boolean = true): BinaryMemcacheClient<ByteArray?> {
    val client = MemcacheClientBuilder(ByteArrayTranscoder.INSTANCE)
        .withAddress(host, port)
        .connectBinary()

    if (connect) {
        client.connectFuture().toCompletableFuture().get()
    }

    return client
}