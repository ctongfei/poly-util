package poly.util.fastloop


/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
object FastLoopTest extends App {

  for (i ← 0 until 10 opt) {
    for (j ← 0 until 10 opt) {
      for (k ← 0 until 10 opt) {
        for (l ← 0 until 10 opt) {
          println(i + j + k + l)
        }
      }
    }
  }

}
