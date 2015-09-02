package poly.util.fastloop

import poly.util.macroutil._
import poly.util.specgroup._
import scala.language.experimental.macros
import scala.reflect.macros.blackbox._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object MapReduceOps {

  def bySemigroup[T](n: Int, f: Int => T, op: (T, T) => T): T = macro bySemigroupImpl[T]

  def byMonoid[T](n: Int, f: Int => T, z: T, op: (T, T) => T): T = macro byMonoidImpl[T]

  def forall(n: Int)(f: Int => Boolean): Boolean = macro forallImpl

  def exists(n: Int)(f: Int => Boolean): Boolean = macro existsImpl

  def bySemigroupImpl[T](c: Context)(n: c.Expr[Int], f: c.Expr[Int => T], op: c.Expr[(T, T) => T]) = {
    import c.universe._
    val i = TermName(c.freshName("poly$i"))
    val accum = TermName(c.freshName("poly$accum"))
    val tree = q"""
        var $accum = $f(0)
        var $i = 1
        while ($i < $n) {
          $accum = $op($accum, $f($i))
          $i += 1
        }
        $accum
    """
    new InlineUtil[c.type](c).inlineAndReset[T](tree)
  }

  def byMonoidImpl[T](c: Context)(n: c.Expr[Int], f: c.Expr[Int => T], z: c.Expr[T], op: c.Expr[(T, T) => T]) = {
    import c.universe._
    val i = TermName(c.freshName("poly$i"))
    val accum = TermName(c.freshName("poly$accum"))
    val tree = q"""
        var $accum = $z
        var $i = 0
        while ($i < $n) {
          $accum = $op($accum, $f($i))
          $i += 1
        }
        $accum
    """
    new InlineUtil[c.type](c).inlineAndReset[T](tree)
  }

  def forallImpl(c: Context)(n: c.Expr[Int])(f: c.Expr[Int => Boolean]) = {
    import c.universe._
    val i = TermName(c.freshName("poly$i"))
    val res = TermName(c.freshName("poly$res"))
    val tree = q"""
      def $res: Boolean = {
        var $i = 0
        while ($i < $n) {
          if (!$f($i)) return false
          $i += 1
        }
        true
      }
      $res
    """
    new InlineUtil[c.type](c).inlineAndReset[Boolean](tree)
  }

  def existsImpl(c: Context)(n: c.Expr[Int])(f: c.Expr[Int => Boolean]) = {
    import c.universe._
    val i = TermName(c.freshName("poly$i"))
    val res = TermName(c.freshName("poly$res"))
    val tree = q"""
      def $res: Boolean = {
        var $i = 0
        while ($i < $n) {
          if ($f($i)) return true
          $i += 1
        }
        false
      }
      $res
    """
    new InlineUtil[c.type](c).inlineAndReset[Boolean](tree)
  }

}
