package poly.util.stringmatch

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object StringMatchTest extends App {

  "<content>abc" match {
    case sm"<$d>" => print(d)
  }

}
