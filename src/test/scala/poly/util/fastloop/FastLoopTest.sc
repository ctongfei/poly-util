import poly.util.fastloop._

MapReduceOps.exists(100)(i => i * i > 9801)

MapReduceOps.bySemigroup(101, i => math.sqrt(i), (x: Double, y: Double) => math.max(x, y))

FastLoop.descending(10, 1, -1)(println)

