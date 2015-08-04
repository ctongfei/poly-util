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

  /** Retrieves the implicit [[poly.util.typeclass.Hashing]] object in the current scope. */
  def apply[X, H](implicit H: Hashing[X, H]) = H

  /** Creates a `Hashing` object from the specific hash function. */
  def create[@sp X, H](fHash: X => H): Hashing[X, H] = new Hashing[X, H] {
    def hash(x: X): H = fHash(x)
  }

  /** Creates a `Hashing` object from a type's inherent `hashCode`/`##` method. */
  implicit def default[@sp X]: Hashing[X, Int] = new Hashing[X, Int] {
    def hash(x: X) = x.##
  }
}
