import scala.io._

object Result {

  /*
   * Complete the 'matrixRotation' function below.
   *
   * The function accepts following parameters:
   *  1. 2D_INTEGER_ARRAY matrix
   *  2. INTEGER r
   */
  case class point(maxRow: Int, maxCol: Int, row: Int = 0, col: Int = 0) {
    def down() = this.copy(row = row + 1)
    def right() = this.copy(col = col + 1)
    def up() = this.copy(row = row - 1)
    def left() = this.copy(col = col - 1)
    var movementindex = 0
    def move(maxRow: Int, maxCol: Int): point = {
      var nextPoint = point.movement(movementindex)(this)
      if (
        nextPoint.col < 0 || nextPoint.col >= maxRow || nextPoint.row < 0 || nextPoint.row >= maxCol
      ) {
        movementindex += 1
        nextPoint = point.movement(movementindex)(this)
      }
      nextPoint.movementindex = movementindex
      nextPoint
    }
  }
  object point {
    val movement: List[point => point] =
      List(_.down(), _.right(), _.up(), _.left())
  }
  def rotateRight[A](seq: Seq[A], i: Int): Seq[A] = {
    val size = seq.size
    seq.drop(size - (i % size)) ++ seq.take(size - (i % size))
  }

  def matrixRotation(matrix: Array[Array[Int]], r: Int, dim: (Int, Int)) = {
    var m = dim._1
    var n = dim._2
    var rowOffset = 0
    var colOffset = 0
    var rotatedmatrix = List.empty[List[Int]]
    val rotation = r % (n * m - 2)
    while (m - 2 * rowOffset >= 0 && n - 2 * colOffset >= 0) {
      var pointer = point(n, m)
      var matrixLine = List.empty[Int]
      var count = 2 * (m + n) - 3
      for (i <- 1 until count) {
        matrixLine = matrixLine.appended(
          matrix(pointer.row + rowOffset)(pointer.col + colOffset)
        )
        pointer = pointer.move(n, m)
      }
      rotatedmatrix = rotatedmatrix.appended(rotateRight(matrixLine, rotation))
      println(matrixLine)
      n -= 2
      m -= 2
      rowOffset += 1
      colOffset += 1
    }
    m = dim._1
    n = dim._2
    var matrix = Array.ofDim[Int](m, n)

    while (m - 2 * rowOffset >= 0 && n - 2 * colOffset >= 0) {
      var pointer = point(n, m)
      var count = 2 * (m + n) - 3
      for (i <- 1 until count) {
        matrix()
            matrixLine = matrixLine.appended(
          matrix(pointer.row + rowOffset)(pointer.col + colOffset)

        )
        pointer = pointer.move(n, m)
      }
      n -= 2
      m -= 2
      rowOffset += 1
      colOffset += 1
    }

  }

}

object Solution {
  def main(args: Array[String]) = {
    val firstMultipleInput = StdIn.readLine.replaceAll("\\s+$", "").split(" ")

    val m = firstMultipleInput(0).toInt

    val n = firstMultipleInput(1).toInt

    val r = firstMultipleInput(2).toInt

    val matrix = Array.ofDim[Int](m, n)

    for (i <- 0 until m) {
      matrix(i) =
        StdIn.readLine.replaceAll("\\s+$", "").split(" ").map(_.trim.toInt)
    }

    Result.matrixRotation(matrix, r, (m, n))
  }
}
