package poly.util.typeclass

import poly.util.specgroup._
import scala.reflect._

/**
 * Represents a strategy for cloning (deep copying) an object.
 * @author Tongfei Chen (ctongfei@gmail.com).
 * @since 0.1.0
 */
trait Cloning[@sp T] {

  /** Returns a clone of the given object. */
  def clone(x: T): T

}

object Cloning extends ImplicitGetter[Cloning] {

  def create[@sp T](cl: T => T): Cloning[T] = new Cloning[T] {
    def clone(x: T) = cl(x)
  }

  implicit object IntCloning extends Cloning[Int] {
    def clone(x: Int) = x
  }

  implicit object LongCloning extends Cloning[Long] {
    def clone(x: Long) = x
  }

  implicit object ShortCloning extends Cloning[Short] {
    def clone(x: Short) = x
  }

  implicit object ByteCloning extends Cloning[Byte] {
    def clone(x: Byte) = x
  }

  implicit object CharCloning extends Cloning[Char] {
    def clone(x: Char) = x
  }

  implicit object DoubleCloning extends Cloning[Double] {
    def clone(x: Double) = x
  }

  implicit object FloatCloning extends Cloning[Float] {
    def clone(x: Float) = x
  }

  implicit object BooleanCloning extends Cloning[Boolean] {
    def clone(x: Boolean) = x
  }

  implicit object StringCloning extends Cloning[String] {
    def clone(x: String) = new String(x)
  }

  implicit def ArrayCloning[T](implicit cl: Cloning[T], ct: ClassTag[T]): Cloning[Array[T]] = new Cloning[Array[T]] {
    def clone(xs: Array[T]) = Array.tabulate[T](xs.length)(xs)
  }

  implicit def SeqCloning[T](implicit cl: Cloning[T]): Cloning[Seq[T]] = new Cloning[Seq[T]] {
    def clone(xs: Seq[T]) = Seq(xs.map(cl.clone): _*)
  }


}
