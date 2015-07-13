package poly.util

import poly.util.formatter._
import poly.util.typeclass.ops._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object TypeclassTest extends App {

  assert(3.str == "3")
  assert("ABCDE".str == "ABCDE")
  assert(1023.str(IntFormatter.radix(2)) == "1111111111")

  assert(4.deepCopy == 4)
  assert("ABC".deepCopy == "ABC")

  val a = Seq(1, 2, 3, 4, 5)
  val b = Seq(1, 2, 3, 4, 5)


}
