package poly.util.formatter

import poly.util.typeclass._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object IntFormatter {

  class RadixFormatter(val radix: Int) extends Formatter[Int] {
    final val digits = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    final val maxlength = 64
    require(radix >= 2 && radix <= digits.length)

    def str(x: Int): String = {
      if (x == 0) return "0"
      val c = Array.ofDim[Char](maxlength)
      var i = maxlength - 1
      var n = math.abs(x)

      while (n != 0) {
        val mod = n % radix
        c(i) = digits(mod)
        i -= 1
        n = n / radix
      }

      val res = String.valueOf(c, i + 1, maxlength - i - 1)
      if (x >= 0) res else "-" + res
    }
  }

  def radix(radix: Int) = new RadixFormatter(radix)

}
