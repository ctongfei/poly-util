package poly.util.typeclass

import scala.language.experimental.macros

/**
 * Enriches any object with formatting, hashing and cloning functions
 * if proper typeclass instances are implicitly provided.
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object ops {

  implicit class withTypeclassOps[T](val x: T) extends AnyVal {

    /**
     * Returns a string representation of this object. This is the typeclass-abstracted version of `toString`.
     * @param formatter An implicit formatter that specifies how to format this object into a string.
     * @return A string representation of this object
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

    /**
     * Returns the hashed result of this object. This is the typeclass-abstracted version of `hashCode`/`##`.
     * @param hashing An implicit hashing object that specifies how to hash this object.
     * @return The hashed result
     */
    def ###(implicit hashing: Hashing[T, Int]) = hashing.hash(x)

  }

}
