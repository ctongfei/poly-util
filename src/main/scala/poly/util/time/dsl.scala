package poly.util.time

import java.time._
import java.time.temporal._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object dsl {

  implicit class RichInstant(val i: Instant) extends AnyVal {
    def +(d: TemporalAmount) = i.plus(d)
    def -(d: TemporalAmount) = i.minus(d)
    def -(j: Instant) = Duration.between(j, i)

    def <(j: Instant) = i.isBefore(j)
    def >(j: Instant) = i.isAfter(j)
    def <=(j: Instant) = i.isBefore(j) | i.equals(j)
    def >=(j: Instant) = i.isAfter(j) | i.equals(j)
  }

  implicit class RichDuration(val d: Duration) extends AnyVal {
    def unary_+ = d
    def unary_- = d.negated()
    def +(e: Duration) = d.plus(e)
    def -(e: Duration) = d.minus(e)
    def *(e: Long) = d.multipliedBy(e)
    def /(e: Long) = d.dividedBy(e)

    def <(e: Duration) = d.compareTo(e) < 0
    def >(e: Duration) = d.compareTo(e) > 0
    def <=(e: Duration) = d.compareTo(e) <= 0
    def >=(e: Duration) = d.compareTo(e) >= 0
  }

  implicit class LongToDuration(val x: Long) extends AnyVal {
    def days = Duration.ofDays(x)
    def hours = Duration.ofHours(x)
    def minutes = Duration.ofMinutes(x)
    def seconds = Duration.ofSeconds(x)
    def ms = Duration.ofMillis(x)
    def ns = Duration.ofNanos(x)
  }

}
