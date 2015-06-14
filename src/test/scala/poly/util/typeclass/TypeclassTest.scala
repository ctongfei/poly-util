package poly.util.typeclass

import poly.util.formatter._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object TypeclassTest extends App {

  assert(3.str == "3")
  assert("ABCDE".str == "ABCDE")
  assert(1023.str(IntFormatter.radix(2)) == "1111111111")

}
