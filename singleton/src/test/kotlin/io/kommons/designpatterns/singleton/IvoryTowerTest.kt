package io.kommons.designpatterns.singleton

import io.kommons.designpatterns.singleton.IvoryTower.Companion


class IvoryTowerTest: AbstractSingletonTest<IvoryTower>(Companion::getInstance) 