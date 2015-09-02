package poly.util.typeclass

import poly.util.specgroup._

/**
 * Represents a strategy that specifies how to format an object to a string.
 * @author Tongfei Chen (ctongfei@gmail.com).
 * @since 0.1.0
 */
trait Formatter[@sp -T] {

  /** Formats the given object to a string. */
  def str(x: T): String

}

object Formatter extends ImplicitGetter[Formatter] {

  /** Creates a default instance of a `Formatter` by calling the inherent `toString` method. */
  implicit def default[@sp T]: Formatter[T] = new Formatter[T] {
    def str(x: T) = x.toString
  }

  /** Creates an instance of a `Formatter` by the function specified. */
  def create[@sp T](f: T => String): Formatter[T] = new Formatter[T] {
    def str(x: T) = f(x)
  }

}
