package poly.util.io

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object IOTest extends App {

  val files = DirectoryIO.recursivelyListFiles("D:/acad")
  files foreach println

}
