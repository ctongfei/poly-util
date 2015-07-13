package poly.util.typeclass

import poly.util.specgroup._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 * @since 0.1.0
 */
trait Formatter[@sp -T] {

  def str(x: T): String

}

object Formatter {

  implicit def default[@sp T]: Formatter[T] = new Formatter[T] {
    def str(x: T) = x.toString
  }

  def create[@sp T](f: T => String): Formatter[T] = new Formatter[T] {
    def str(x: T) = f(x)
  }

}
