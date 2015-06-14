package poly.util.typeclass

import poly.util.specgroup._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
trait Formatter[@sp(fdib) T] {

  def str(x: T): String

}

object Formatter {

  implicit def default[T]: Formatter[T] = new Formatter[T] {
    def str(x: T) = x.toString
  }

  def create[T](f: T => String): Formatter[T] = new Formatter[T] {
    def str(x: T) = f(x)
  }

}
