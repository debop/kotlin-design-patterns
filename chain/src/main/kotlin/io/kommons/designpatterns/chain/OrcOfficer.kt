package io.kommons.designpatterns.chain

/**
 * OrcOfficer
 */
class OrcOfficer(handler: RequestHandler?): RequestHandler(handler) {

    override fun handleRequest(request: Request) {
        if (request.isHandled) {
            return
        }
        when (request.requestType) {
            RequestType.TORTURE_PRISONER -> {
                printHandling(request)
                request.markHandled()
            }
            else                         -> {
                super.handleRequest(request)
            }
        }
    }

    override fun toString(): String = "Orc officer"
}