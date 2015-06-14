package poly.util

/**
 * This package abstracts over common methods in `Any` (e.g. `toString`, `equals`, `hashCode`) using the typeclass
 * pattern.
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
package object typeclass {

  implicit class withTypeclassesOps[T](val x: T) extends AnyVal {

    /**
     * Returns a string representation of this object.
     * @param formatter An implicit formatter that specifies how to format this object into a string.
     * @return A string representation
     */
    def str(implicit formatter: Formatter[T]) = formatter.str(x)

    def print(implicit formatter: Formatter[T]) = scala.Predef.print(formatter.str(x))

    def println(implicit formatter: Formatter[T]) = scala.Predef.println(formatter.str(x))

    /**
     * Returns a deep copy (clone) of this object.
     * @param cloning An implicit cloning object that specifies how to clone this object.
     * @return A deep copy
     */
    def deepCopy(implicit cloning: Cloning[T]) = cloning.clone(x)

    /**
     * Checks if two objects are equivalent (typesafe equality checking).
     * @param y Another object
     * @param eq An implicit equivalence relation.
     * @return Whether ''x'' is equivalent to ''y''
     */
    def ===(y: T)(implicit eq: Eq[T]) = eq.eq(x, y)

    def !==(y: T)(implicit eq: Eq[T]) = eq.ne(x, y)

    def ###(implicit hash: Hashing[T]) = hash.hash(x)
  }

}
