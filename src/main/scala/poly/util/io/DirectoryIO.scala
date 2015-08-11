package poly.util.io

import java.io._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object DirectoryIO {

  def listFiles(dir: String) = new File(dir).listFiles()

  def recursivelyListFiles(dir: String): Seq[File] = {
    val files = new File(dir).listFiles()
    files.view.filter(!_.isDirectory) ++ files.view.filter(_.isDirectory).flatMap(f => recursivelyListFiles(f.getAbsolutePath))
  }

}

