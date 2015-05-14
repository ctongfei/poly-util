package poly.util.fastloop

import scala.language.experimental.macros

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
class XRange(r: Range) {

  def foreach[U](f: Int => U): Unit = macro MacroImpl.xrangeForeachImpl[U]

}
