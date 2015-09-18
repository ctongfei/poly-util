package poly.util.cliconfig

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object Test extends App {

  val a = CliConfig("abc")
  println(CliConfig.getOrElse("11", "123"))
  println(a)


}
