package io.kommons.designpatterns.singleton

class InitializingOnDemandHolderIdiomTest
    : AbstractSingletonTest<InitializingOnDemandHolderIdiom>({ InitializingOnDemandHolderIdiom.getInstance() }) 