package poly.util.logging

/**
 * Mix this trait with any class that you would like to log with.
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
trait HasLogger {

  protected lazy val logger = new Logger(this.getClass.getCanonicalName)

}
