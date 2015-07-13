package poly.util.typeclass

import poly.util.specgroup._

/**
 * Typeclass for type-strict equivalence relation.
 * @author Tongfei Chen (ctongfei@gmail.com).
 * @since 0.1.0
 */
trait Eq[@sp -X] {

  /** Checks if two objects of the same type are equivalent under this equivalence relation. */
  def eq(x: X, y: X): Boolean

  /** Checks if two objects of the same type are not equivalent under this equivalence equation. */
  def ne(x: X, y: X): Boolean = !eq(x, y)

}


object Eq {

  def create[@sp X](fEq: (X, X) => Boolean): Eq[X] = new Eq[X] {
    def eq(x: X, y: X) = fEq(x, y)
  }

  implicit def default[@sp X]: Eq[X] = new Eq[X] {
    def eq(x: X, y: X) = x == y
    override def ne(x: X, y: X) = x != y
  }

  /** Returns the equality-by-reference relation. */
  def byRef[X <: AnyRef]: Eq[X] = new Eq[X] {
    def eq(x: X, y: X) = x eq y
    override def ne(x: X, y: X) = x ne y
  }
}
