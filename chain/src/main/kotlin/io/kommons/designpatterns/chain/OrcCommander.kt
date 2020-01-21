package io.kommons.designpatterns.chain

/**
 * OrcCommander
 */
class OrcCommander(handler: RequestHandler?): RequestHandler(handler) {

    override fun handleRequest(request: Request) {
        if (request.isHandled) {
            return
        }
        when (request.requestType) {
            RequestType.DEFEND_CASTLE -> {
                printHandling(request)
                request.markHandled()
            }
            else                      -> {
                super.handleRequest(request)
            }
        }
    }

    override fun toString(): String = "Orc commander"
}