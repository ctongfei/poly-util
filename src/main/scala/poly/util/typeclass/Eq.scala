package poly.util.typeclass

import poly.util.specgroup._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
trait Eq[@sp(fdib) T] {

  def eq(x: T, y: T): Boolean
  def ne(x: T, y: T): Boolean = !eq(x, y)

}

object Eq {

  implicit def default[T]: Eq[T] = new Eq[T] {
    def eq(x: T, y: T) = x == y
    override def ne(x: T, y: T) = x != y
  }

}