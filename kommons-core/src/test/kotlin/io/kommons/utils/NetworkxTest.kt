package io.kommons.utils

import io.kommons.logging.KLogging
import io.kommons.logging.debug
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldContain
import org.amshove.kluent.shouldEqualTo
import org.amshove.kluent.shouldNotBeEmpty
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.Test
import java.net.InetAddress

class NetworkxTest {

    companion object: KLogging()

    @Test
    fun `get local host name`() {
        Networkx.LocalhostName.shouldNotBeNull()
    }

    @Test
    fun `get localhost address`() {
        val address = Networkx.Localhost.hostAddress
        log.debug { "localhost address=$address" }
        address.shouldNotBeEmpty()
        address shouldContain "."
    }

    @Test
    fun `Ipv4 형식의 address 인가`() {

        Networkx.isIpv4Address("abc").shouldBeFalse()
        Networkx.isIpv4Address("a.a.a.a").shouldBeFalse()
        Networkx.isIpv4Address("0.0.0.500").shouldBeFalse()

        Networkx.isIpv4Address("0.0.0.0").shouldBeTrue()
        Networkx.isIpv4Address("255.255.255.255").shouldBeTrue()
    }

    @Test
    fun `InetAddress 가 private인지 확인`() {
        Networkx.isPrivateAddress(InetAddress.getByName("127.0.0.1")).shouldBeFalse()

        Networkx.isPrivateAddress(InetAddress.getByName("10.0.0.1")).shouldBeTrue()
        Networkx.isPrivateAddress(InetAddress.getByName("192.168.0.1")).shouldBeTrue()
    }

    @Test
    fun `Ipv4 형식의 문자열을 Int로 변환`() {
        Networkx.ipToInt("0.0.0.0") shouldEqualTo 0
        Networkx.ipToInt("255.255.255.255") shouldEqualTo -1
        Networkx.ipToInt("127.0.0.1") shouldEqualTo 2130706433
    }
}