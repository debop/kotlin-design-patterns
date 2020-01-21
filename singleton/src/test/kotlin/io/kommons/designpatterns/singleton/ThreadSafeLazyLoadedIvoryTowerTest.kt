package io.kommons.designpatterns.singleton

class ThreadSafeLazyLoadedIvoryTowerTest
    : AbstractSingletonTest<ThreadSafeLazyLoadedIvoryTower>({ ThreadSafeLazyLoadedIvoryTower.getInstance() }) 