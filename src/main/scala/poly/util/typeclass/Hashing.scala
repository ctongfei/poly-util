package poly.util.typeclass

import poly.util.specgroup._

/**
 * Typeclass for hashing functions.
 * @author Tongfei Chen (ctongfei@gmail.com).
 * @since 0.1.0
 */
trait Hashing[@sp -X, @sp(i) +H] {

  /** Returns a user-defined hash code of an object. */
  def hash(x: X): H

}

object Hashing {

  def apply[X, H](implicit H: Hashing[X, H]) = H

  def create[@sp X, H](fHash: X => H): Hashing[X, H] = new Hashing[X, H] {
    def hash(x: X): H = fHash(x)
  }

  implicit def default[@sp X]: Hashing[X, Int] = new Hashing[X, Int] {
    def hash(x: X) = x.##
  }
}
