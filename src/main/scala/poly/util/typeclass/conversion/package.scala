package poly.util.typeclass

import scala.language.implicitConversions

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
package object conversion {

  implicit def scalaHashingToPoly[X](h: scala.util.hashing.Hashing[X]): Hashing[X, Int] = new Hashing[X, Int] {
    def hash(x: X) = h.hash(x)
  }

}
