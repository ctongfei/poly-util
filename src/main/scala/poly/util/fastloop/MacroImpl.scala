package poly.util.fastloop

import scala.language.experimental.macros
import scala.reflect.macros.blackbox._

// Copyright (c) 2011-2012 Erik Osheim, Tom Switzer
//
// Permission is hereby granted, free of charge, to any person obtaining a copy of
// this software and associated documentation files (the "Software"), to deal in
// the Software without restriction, including without limitation the rights to
// use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
// of the Software, and to permit persons to whom the Software is furnished to do
// so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
object MacroImpl {

  def xrangeForeachImpl[U](c: Context)(f: c.Expr[Int => U]): c.Expr[Unit] = {
    import c.universe._

    val (range, index, end, limit, step) = (
      TermName(c.freshName("range$X")),
      TermName(c.freshName("index$X")),
      TermName(c.freshName("end$X")),
      TermName(c.freshName("limit$X")),
      TermName(c.freshName("step$X"))
    )

    def isLiteral(t: Tree): Option[Int] = t match {
      case Literal(Constant(a)) => a match {
        case n: Int => Some(n)
        case _ => None
      }
      case _ => None
    }

    def strideUpTo(fromExpr: Tree, toExpr: Tree, stride: Int): Tree =
      q"""
      var $index: Int = $fromExpr
      val $end: Int = $toExpr
      while ($index <= $end) {
        $f($index)
        $index += $stride
      }"""

    def strideUpUntil(fromExpr: Tree, untilExpr: Tree, stride: Int): Tree =
      q"""
      var $index: Int = $fromExpr
      val $limit: Int = $untilExpr
      while ($index < $limit) {
        $f($index)
        $index += $stride
      }"""

    def strideDownTo(fromExpr: Tree, toExpr: Tree, stride: Int): Tree =
      q"""
      var $index: Int = $fromExpr
      val $end: Int = $toExpr
      while ($index >= $end) {
        $f($index)
        $index -= $stride
      }"""

    def strideDownUntil(fromExpr: Tree, untilExpr: Tree, stride: Int): Tree =
      q"""
      var $index: Int = $fromExpr
      val $limit: Int = $untilExpr
      while ($index > $limit) {
        $f($index)
        $index -= $stride
      }"""

    var r: Tree = null
    c.macroApplication match {
      case q"$_.range2XRange($range).opt.foreach[$ty]($f)" => r = range
      //case _ => c.error(c.enclosingPosition, "illegal position for macro")
    }

    val tree: Tree = r match {

      case q"scala.this.Predef.intWrapper($i).until($j)" =>
        strideUpUntil(i, j, 1)

      case q"scala.this.Predef.intWrapper($i).to($j)" =>
        strideUpTo(i, j, 1)

      case r @ q"scala.this.Predef.intWrapper($i).until($j).by($step)" =>
        isLiteral(step) match {
          case Some(k) if k > 0 => strideUpUntil(i, j, k)
          case Some(k) if k < 0 => strideDownUntil(i, j, -k)
          case Some(k) if k == 0 =>
            c.error(c.enclosingPosition, "zero stride")
            q"()"
          case None =>
            c.info(c.enclosingPosition, "non-literal stride", true)
            q"$r.foreach($f)"
        }

      case r @ q"scala.this.Predef.intWrapper($i).to($j).by($step)" =>
        isLiteral(step) match {
          case Some(k) if k > 0 => strideUpTo(i, j, k)
          case Some(k) if k < 0 => strideDownTo(i, j, -k)
          case Some(k) if k == 0 =>
            c.error(c.enclosingPosition, "zero stride")
            q"()"
          case None =>
            c.info(c.enclosingPosition, "non-literal stride", true)
            q"$r.foreach($f)"
        }

      case r =>
        c.info(c.enclosingPosition, "non-literal range", true)
        q"$r.foreach($f)"
    }

    new InlineUtil[c.type](c).inlineAndReset[Unit](tree)
  }


}
