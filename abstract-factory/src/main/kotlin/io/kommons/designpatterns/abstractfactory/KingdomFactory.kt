package io.kommons.designpatterns.abstractfactory

/**
 * KingdomFactory
 *
 * @author debop
 * @since 28/09/2019
 */
interface KingdomFactory {

    fun createCastle(): Castle

    fun createKing(): King

    fun createArmy(): Army

}