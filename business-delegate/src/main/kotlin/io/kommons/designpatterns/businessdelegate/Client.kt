package io.kommons.designpatterns.businessdelegate

class Client(val delegate: BusinessDelegate) {

    fun doTask() {
        delegate.doTask()
    }

}