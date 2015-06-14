package poly.util.typeclass

import poly.util.specgroup._

/**
 * Typeclass for hashing functions.
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
trait Hashing[@sp(fdi) X] extends Eq[X] {

  /** Returns a user-defined hash code of an object. */
  def hash(x: X): Int

}

object Hashing {

  def apply[X](implicit H: Hashing[X]) = H

  def create[@sp(fdi) X](fHash: X => Int)(implicit E: Eq[X]): Hashing[X] = new Hashing[X] {
    def eq(x: X, y: X) = E.eq(x, y)
    override def ne(x: X, y: X) = E.ne(x, y)
    def hash(x: X): Int = fHash(x)
  }

  implicit def default[@sp(fdi) X]: Hashing[X] = new Hashing[X] {
    def eq(x: X, y: X) = x == y
    override def ne(x: X, y: X) = x != y
    def hash(x: X) = x.##
  }
}
