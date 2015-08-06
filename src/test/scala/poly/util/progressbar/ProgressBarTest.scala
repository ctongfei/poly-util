package poly.util.progressbar

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object ProgressBarTest extends App {

    val pb = new ProgressBar("Test", 0)
    pb.start()
    for (i ← 0 until 10000) {
      val x = Array.tabulate(100, 100)((i, j) => i + 0.1324 * j)
      pb.step()
      if (i == 300) pb.maxHint(100)
    }
    pb.stop()
    println("Success!")

}