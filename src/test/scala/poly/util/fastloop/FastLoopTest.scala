package poly.util.fastloop

import org.scalameter._


/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object FastLoopTest extends App {


  for (n ← Seq(100, 200, 400, 800, 1600)) {

    val conf = config(Key.exec.benchRuns → 25)
      .withWarmer(new Warmer.Default).withMeasurer(new Measurer.IgnoringGC)

    val t1 = conf measure {
      var sum = 0.0
      var i = 0
      while (i < n) {
        var j = 0
        while (j < n) {
          sum += math.sqrt(i * j)
          j += 1
        }
        i += 1
      }
      print(sum + "\r")
    }
    println(s"While loop: $t1")

    val t2 = conf measure {
      var sum = 0.0
      FastLoop.ascending(0, n, 1) { i =>
        FastLoop.ascending(0, n, 1) { j =>
          sum += math.sqrt(i * j)
        }
      }
      print(sum + "\r")
    }
    println(s"Poly-util macro: $t2")

    val t3 = conf measure {
      var sum = 0.0
      for (i ← 0 until n)
        for (j ← 0 until n)
          sum += math.sqrt(i * j)
      print(sum + "\r")
    }
    println(s"Imperative Scala: $t3")

    val t4 = conf measure {
      val s = for (i ← (0 until n).view; j ← (0 until n).view) yield math.sqrt(i * j)
      print(s.sum + "\r")
    }
    println(s"Idiomatic Scala with view: $t4")

    val t5 = conf measure {
      val s = for (i ← 0 until n; j ← 0 until n) yield math.sqrt(i * j)
      print(s.sum + "\r")
    }
    println(s"Idiomatic Scala: $t5")

    println()
  }



}
