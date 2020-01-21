package io.kommons.designpatterns.chain

/**
 * OrcKing makes requests that are handled by the chain.
 */
class OrcKing {

    private val chain: RequestHandler

    init {
        chain = buildChain()
    }

    private fun buildChain(): RequestHandler {
        return OrcCommander(OrcOfficer(OrcSoldier(null)))
    }

    fun makeRequest(request: Request) {
        chain.handleRequest(request)
    }
}