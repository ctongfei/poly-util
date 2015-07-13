package poly.util.typeclass

import scala.language.experimental.macros

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object ops {

  implicit class withTypeclassOps[T](val x: T) extends AnyVal {

    /**
     * Returns a string representation of this object. This is the typeclass-abstracted version of `toString`.
     * @param formatter An implicit formatter that specifies how to format this object into a string.
     * @return A string representation
     */
    def str(implicit formatter: Formatter[T]) = formatter.str(x)

    def print(implicit formatter: Formatter[T]) = scala.Predef.print(formatter.str(x))

    def println(implicit formatter: Formatter[T]) = scala.Predef.println(formatter.str(x))

    /**
     * Returns a deep copy (clone) of this object. This is the typeclass-abstracted version of `clone`.
     * @param cloning An implicit cloning object that specifies how to clone this object.
     * @return A deep copy
     */
    def deepCopy(implicit cloning: Cloning[T]) = cloning.clone(x)

    def ###(implicit hashing: Hashing[T, Int]) = hashing.hash(x)

    //region Implicit Cloning objects
    //endregion

    //region Implicit
  }

}
