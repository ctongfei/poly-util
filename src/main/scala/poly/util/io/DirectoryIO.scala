package poly.util.io

import java.io._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object DirectoryIO {

  def listFiles(dir: String) = new File(dir).listFiles()

}

