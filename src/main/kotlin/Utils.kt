import java.security.MessageDigest
import java.util.*

/*
 * @created 30. 01. 2022
 * @project IntelliJ IDEA
 * @author Dormage
*/


private val digits = charArrayOf(
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
    'E', 'F'
)

val ByteArray.asHex
    get() :String {
        val l = size
        val out = CharArray(l shl 1)
        var i = 0
        var j = 0
        while (i < l) {
            out[j++] = digits[0xF0 and this[i].toInt() ushr 4]
            out[j++] = digits[0x0F and this[i].toInt()]
            i++
        }
        return out.joinToString("")
    }

/** Returns HEX encoded identifier as a bit set. */
val String.asBitSet: BitSet get() = BitSet.valueOf(toBigInteger(16).toByteArray().reversedArray())

/** Digests [data] using SHA-256 hashing algorithm. */
fun sha256(data: ByteArray): ByteArray {
    return MessageDigest.getInstance("SHA-256").let {
        it.update(data)
        it.digest()
    }
}

/** Digests [data] using SHA-256 hashing algorithm. */
fun sha256(data: String) = sha256(data.encodeToByteArray())
