package poly.util.fastloop

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
class InlineUtil[C <: Context with Singleton](val c: C) {
  import c.universe._

  def inlineAndReset[T](tree: Tree): c.Expr[T] = {
    val inlined = inlineApplyRecursive(tree)
    c.Expr[T](c.untypecheck(inlined))
  }

  def inlineApplyRecursive(tree: Tree): Tree = {
    val ApplyName = TermName("apply")

    class InlineSymbol(symbol: Symbol, value: Tree) extends Transformer {
      override def transform(tree: Tree): Tree = tree match {
        case Ident(_) if tree.symbol == symbol =>
          value
        case tt: TypeTree if tt.original != null =>
          super.transform(internal.setOriginal(TypeTree(), transform(tt.original)))
        case _ =>
          super.transform(tree)
      }
    }

    object InlineApply extends Transformer {
      def inlineSymbol(symbol: Symbol, body: Tree, arg: Tree): Tree =
        new InlineSymbol(symbol, arg).transform(body)

      override def transform(tree: Tree): Tree = tree match {
        case Apply(Select(Function(params, body), ApplyName), args) =>
          params.zip(args).foldLeft(body) { case (b, (param, arg)) =>
            inlineSymbol(param.symbol, b, arg)
          }

        case Apply(Function(params, body), args) =>
          params.zip(args).foldLeft(body) { case (b, (param, arg)) =>
            inlineSymbol(param.symbol, b, arg)
          }

        case _ =>
          super.transform(tree)
      }
    }

    InlineApply.transform(tree)
  }
}