package io.kommons.designpatterns.chain

/**
 * OrcSoldier
 */
class OrcSoldier(handler: RequestHandler?): RequestHandler(handler) {

    override fun handleRequest(request: Request) {
        if (request.isHandled) {
            return
        }
        when (request.requestType) {
            RequestType.COLLECT_TAX -> {
                printHandling(request)
                request.markHandled()
            }
            else                    -> {
                super.handleRequest(request)
            }
        }
    }

    override fun toString(): String = "Orc soldier"
}