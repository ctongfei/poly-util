package poly.util

/**
 * This package contains utilities for rewriting range loops using macros.
 * It is adapted from the `cforRange` macro in Spire (https://github.com/non/spire).
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
package object fastloop {

  implicit class range2XRange(val range: Range) extends AnyVal {

    /**
     * Returns an optimized version of a range.
     * When traversing through this optimized range, it will be rewritten
     * as a while loop with the loop body inlined at compile time.
     * @return An optimized range that will be rewritten by macros at compile time
     */
    def opt = new XRange(range)

  }

  implicit class intWithXRangeOps(val l: Int) extends AnyVal {

    /**
     * Returns an optimized version of a range.
     * When traversing through this optimized range, it will be rewritten
     * as a while loop with the loop body inlined at compile time.
     * @return An optimized range that will be rewritten by macros at compile time
     */
    def ??(r: Int) = new XRange(l until r)

  }

}
