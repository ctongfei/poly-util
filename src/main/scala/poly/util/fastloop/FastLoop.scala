package poly.util.fastloop

import poly.util.macroutil._
import scala.reflect.macros.blackbox._
import scala.language.experimental.macros

/**
 * Contains macro expansions that
 *  - Rewrites a range foreach into a while loop;
 *  - Attempts to inline to loop body.
 *
 * @author Tongfei Chen (ctongfei@gmail.com).
 * @since 0.2.2
 */
object FastLoop {

  /**
   * Equivalent to {{{
   *   for (i <- a until b by step) f(i)
   * }}} (where `step` > 0) but optimized using macros.
   * It normally provides 1.5x~3x speedup.
   */
  def ascending[V](a: Int, b: Int, step: Int)(f: Int => V): Unit = macro ascendingMacroImpl[V]

  /**
   * Equivalent to {{{
   *   for (i <- a until b by step) f(i)
   * }}} (where `step` < 0) but optimized using macros.
   * It normally provides 1.5x~3x speedup.
   */
  def descending[V](a: Int, b: Int, step: Int)(f: Int => V): Unit = macro descendingMacroImpl[V]

  def ascendingMacroImpl[V](c: Context)(a: c.Expr[Int], b: c.Expr[Int], step: c.Expr[Int])(f: c.Expr[Int => V]): c.Expr[Unit] = {
    import c.universe._
    val i = TermName(c.freshName("poly$i"))
    val limit = TermName(c.freshName("poly$limit"))

    val tree = q"""
      var $i = $a
      var $limit = $b
      while ($i < $limit) {
        $f($i)
        $i += $step
      }
    """
    new InlineUtil[c.type](c).inlineAndReset[Unit](tree)
  }

  def descendingMacroImpl[V](c: Context)(a: c.Expr[Int], b: c.Expr[Int], step: c.Expr[Int])(f: c.Expr[Int => V]): c.Expr[Unit] = {
    import c.universe._
    val i = TermName(c.freshName("poly$i"))
    val limit = TermName(c.freshName("poly$limit"))

    val tree = q"""
      var $i = $a
      var $limit = $b
      while ($i > $limit) {
        $f($i)
        $i += $step
      }
    """
    new InlineUtil[c.type](c).inlineAndReset[Unit](tree)
  }

}
