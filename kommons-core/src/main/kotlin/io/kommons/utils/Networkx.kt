package io.kommons.utils

import io.kommons.logging.KLogging
import java.net.Inet4Address
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * Network 정보를 제공하는 Object
 *
 * @author debop
 */
object Networkx: KLogging() {
    const val LOCALHOST_NAME: String = "localhost"
    const val LOCALHOST_IP: String = "127.0.0.1"
    const val DEFAULT_MASK: String = "255.255.255.0"
    const val INT_VALUE_127_0_0_1: Int = 0x7F000001

    @JvmStatic
    val Localhost: InetAddress by lazy { InetAddress.getLocalHost() }

    /**
     * 컴퓨터 이름
     */
    @JvmStatic
    val LocalhostName: String by lazy {
        try {
            Localhost.hostName
        } catch (uhe: UnknownHostException) {
            uhe.message!!
        }
    }

    @JvmStatic
    fun isIpv4Address(ip: String): Boolean = ipToOptionInt(ip) != null

    /**
     * ip address 가 private 인지 여부
     * 10.*.*.*, 172.16.*.*, 192.168.*.* 인 경우가 private 이다
     */
    @JvmStatic
    fun isPrivateAddress(ip: InetAddress): Boolean {
        return when (ip) {
            is Inet4Address -> {
                val addr = ip.address
                if (addr[0] == 10.toByte()) true // 10/8
                else if (addr[0] == 172.toByte() && (addr[1].toInt() and 0xF0) == 16) true // 172/16
                else addr[0] == 192.toByte() && addr[1] == 168.toByte()
            }
            else            -> false
        }
    }

    /**
     * ip v4 문자열을 `Int`로 변환합니다.
     */
    @JvmStatic
    fun ipToInt(ip: String): Int {
        return ipToOptionInt(ip) ?: throw java.lang.IllegalArgumentException("Invalid IPv4 address: $ip")
    }

    /**
     * ip v4 문자열을 `Option[Int]`로 변환합니다.
     */
    @JvmStatic
    fun ipToOptionInt(ip: String): Int? {
        val dot1 = ip.indexOf('.')
        if (dot1 <= 0) return null

        val dot2 = ip.indexOf('.', dot1 + 1)
        if (dot2 == -1) return null

        val dot3 = ip.indexOf('.', dot2 + 1)
        if (dot3 == -1) return null

        val num1 = ipv4DecimalToInt(ip.substring(0, dot1))
        if (num1 < 0) return null

        val num2 = ipv4DecimalToInt(ip.substring(dot1 + 1, dot2))
        if (num2 < 0) return null

        val num3 = ipv4DecimalToInt(ip.substring(dot2 + 1, dot3))
        if (num3 < 0) return null

        val num4 = ipv4DecimalToInt(ip.substring(dot3 + 1))
        if (num4 < 0) return null

        return (num1 shl 24) or (num2 shl 16) or (num3 shl 8) or num4
    }

    @JvmStatic
    private fun ipv4DecimalToInt(s: String): Int {
        if (s.isBlank() || s.length > 3) return -1

        var i = 0
        var num = 0
        while (i < s.length) {
            val c = s[i]
            if (c < '0' || c > '9') return -1
            num = (num * 10) + (c - '0')
            i += 1
        }
        return if (num >= 0 && num <= 255) num else -1
    }

    @JvmStatic
    fun inetAddressToInt(inetAddress: InetAddress): Int {
        when (inetAddress) {
            is Inet4Address -> {
                val addr = inetAddress.address
                return ((addr[0].toInt() and 0xFF) shl 24) or
                ((addr[1].toInt() and 0xFF) shl 16) or
                ((addr[2].toInt() and 0xFF) shl 8) or
                ((addr[3].toInt() and 0xFF))
            }
            else            -> throw java.lang.IllegalArgumentException("non-Inet4Address cannot be converted to an Int")
        }
    }

    /**
     * 현 컬렉션의 항목수가 지정한 항목 수보다 작다면, `item` 값을 추가합니다.
     */
    private fun <T> Collection<T>.padTo(itemCount: Int, item: T): Collection<T> {
        val list = this.toMutableList()
        val count = itemCount - this.size
        if (count > 0) {
            repeat(count) {
                list.add(item)
            }
        }
        return list
    }

    /**
     * Converts either a full or partial ip, (e.g.127.0.0.1, 127.0)
     * to it's integer equivalent with mask specified by prefixlen.
     * Assume missing bits are 0s for a partial ip. Result returned as
     * (ip, netMask)
     */
    @JvmStatic
    fun ipToIpBlock(ip: String, prefixLen: Int?): Pair<Int, Int> {
        val arr: List<String> = ip.split('.')

        val pLen =
            if (arr.size != 4 && prefixLen == null) arr.size * 8
            else prefixLen!!

        val netIp = ipToInt(arr.padTo(4, "0").joinToString(separator = "."))
        val mask = (1 shl 31) shr (pLen - 1)

        return Pair(netIp, mask)
    }

    @JvmStatic
    fun cidrToIpBlock(cidr: String): Pair<Int, Int> {
        val arr = cidr.split('/')

        return when (arr.size) {
            1    -> ipToIpBlock(arr[0], null)
            2    -> ipToIpBlock(arr[0], arr[1].toInt())
            else -> throw java.lang.IllegalArgumentException("Invalid cidr. cidr=$cidr")
        }
    }

    @JvmStatic
    fun isIpInBlock(ip: Int, ipBlock: Pair<Int, Int>): Boolean {
        return (ipBlock.second and ip) == ipBlock.first
    }

    @JvmStatic
    fun isIpInBlocks(ip: Int, ipBlocks: Iterable<Pair<Int, Int>>): Boolean = ipBlocks.any { isIpInBlock(ip, it) }

    @JvmStatic
    fun isIpInBlocks(ip: String, ipBlocks: Iterable<Pair<Int, Int>>): Boolean = isIpInBlocks(ipToInt(ip), ipBlocks)

    @JvmStatic
    fun isInetAddressInBlocks(inetAddress: InetAddress, ipBlocks: Iterable<Pair<Int, Int>>): Boolean = isIpInBlocks(inetAddressToInt(inetAddress), ipBlocks)
}