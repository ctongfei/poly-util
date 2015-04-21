package poly.util.io

import java.io._
import java.nio.charset._
import java.nio.file._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object FileIO {

  /**
   * Reads a file into a string.
   * @param fn File name
   * @param cs Charset (default value UTF-8)
   * @return
   */
  def readAll(fn: String, cs: Charset = StandardCharsets.UTF_8) = new String(Files.readAllBytes(Paths.get(fn)), cs)

  def writeAll(fn: String)(ss: Any*) = {
    val pw = new PrintWriter(fn)
    for (s ← ss) pw.write(s.toString)
    pw.close()
  }

  /**
   * Reads a file, line by line, lazily.
   * @param fn File name
   * @param cs Charset (default value UTF-8)
   * @return A non-strict list of lines.
   */
  def readLines(fn: String, cs: Charset = StandardCharsets.UTF_8): Iterable[String] = new Iterable[String] {
    def iterator = new Iterator[String] {
      val br = Files.newBufferedReader(Paths.get(fn), cs)
      var nextLine: String = null
      def hasNext = {
        if (nextLine != null) true
        else {
          nextLine = br.readLine()
          nextLine != null
        }
      }
      def next() = {
        if ((nextLine != null) || hasNext) {
          val line = nextLine
          nextLine = null
          line
        }
        else throw new NoSuchElementException
      }
    }
  }

  def writeLines(fn: String)(s: TraversableOnce[String]) = {
    val bw = Files.newBufferedWriter(Paths.get(fn))
    for (line ← s) bw.write(line + "\n")
    bw.close()
  }

}
