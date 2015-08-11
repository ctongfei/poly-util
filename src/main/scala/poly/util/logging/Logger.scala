package poly.util.logging

/**
 * A simple logger that prints to the standard error stream `java.lang.System.err`.
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
class Logger(val prefix: String) {

  import Logger._

  var errorEnabled = true
  var warningEnabled = true
  var infoEnabled = true
  var traceEnabled = true

  @inline private[this] def createLog(prefix: String, typePrefix: String, msg: String) = {
    val timestamp = java.time.LocalDateTime.now()
    s"$timestamp\t[$prefix] $typePrefix $msg"
  }

  def error(msg: String): Unit =
    if (errorEnabled) System.err.println(createLog(prefix, errorPrefix, msg))

  def warning(msg: String): Unit =
    if (warningEnabled) System.err.println(createLog(prefix, warningPrefix, msg))

  def info(msg: String): Unit =
    if (infoEnabled) System.err.println(createLog(prefix, infoPrefix, msg))

  def trace(msg: String): Unit =
    if (traceEnabled) System.err.println(createLog(prefix, tracePrefix, msg))

}

object Logger {

  final val errorPrefix = "ERROR:"
  final val warningPrefix = "WARNING:"
  final val infoPrefix = "INFO:"
  final val tracePrefix = "TRACE:"

}
