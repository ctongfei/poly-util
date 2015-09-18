package poly.util.cliconfig

import scala.collection._
import scala.collection.JavaConversions._

/**
 * Provides utilities to access command-line interface configurations.
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object CliConfig extends DefaultMap[String, String] {

  override def apply(key: String) = System.getProperties.getProperty(key)

  def get(key: String) = Option(System.getProperty(key))

  def iterator = asScalaSet(System.getProperties.entrySet()).iterator.map(e => e.getKey.toString → e.getValue.toString)

}
