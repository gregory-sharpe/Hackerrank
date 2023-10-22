file:///C:/Users/grego/OneDrive/Documents/HackerRank%20Scala/Hard/MatrixLayerRotation.scala
### java.lang.AssertionError: NoDenotation.owner

occurred in the presentation compiler.

action parameters:
offset: 859
uri: file:///C:/Users/grego/OneDrive/Documents/HackerRank%20Scala/Hard/MatrixLayerRotation.scala
text:
```scala
import java.io._
import java.math._
import java.security._
import java.text._
import java.util._
import java.util.concurrent._
import java.util.function._
import java.util.regex._
import java.util.stream._
import scala.collection.immutable._
import scala.collection.mutable._
import scala.collection.concurrent._
import scala.concurrent._
import scala.io._
import scala.math._
import scala.sys._
import scala.util.matching._
import scala.reflect._
import scala.compiletime.ops.int

object Result {

    /*
     * Complete the 'matrixRotation' function below.
     *
     * The function accepts following parameters:
     *  1. 2D_INTEGER_ARRAY matrix
     *  2. INTEGER r
     */
    class matrixPointer(maxRow:Int,maxColumn:Int){
        val rowPointer = 0
        val colPointer = 0
        val movement = ((x,y):(Int,Int)=>(@@x+1,y) 
    }



    def matrixRotation(matrix: Array[Array[Int]], r: Int, dim:(Int,Int))= {
        var rowPointer = (0,0)
        
        var movement = List()

    }

}

object Solution {
    def main(args: Array[String])= {
        val firstMultipleInput = StdIn.readLine.replaceAll("\\s+$", "").split(" ")

        val m = firstMultipleInput(0).toInt

        val n = firstMultipleInput(1).toInt

        val r = firstMultipleInput(2).toInt

        val matrix = Array.ofDim[Int](m, n)

        for (i <- 0 until m) {
            matrix(i) = StdIn.readLine.replaceAll("\\s+$", "").split(" ").map(_.trim.toInt)
        }

        Result.matrixRotation(matrix, r,(m,n))
    }
}

```



#### Error stacktrace:

```
dotty.tools.dotc.core.SymDenotations$NoDenotation$.owner(SymDenotations.scala:2582)
	scala.meta.internal.pc.SignatureHelpProvider$.isValid(SignatureHelpProvider.scala:83)
	scala.meta.internal.pc.SignatureHelpProvider$.notCurrentApply(SignatureHelpProvider.scala:94)
	scala.meta.internal.pc.SignatureHelpProvider$.$anonfun$1(SignatureHelpProvider.scala:48)
	scala.collection.StrictOptimizedLinearSeqOps.loop$3(LinearSeq.scala:280)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile(LinearSeq.scala:282)
	scala.collection.StrictOptimizedLinearSeqOps.dropWhile$(LinearSeq.scala:278)
	scala.collection.immutable.List.dropWhile(List.scala:79)
	scala.meta.internal.pc.SignatureHelpProvider$.signatureHelp(SignatureHelpProvider.scala:48)
	scala.meta.internal.pc.ScalaPresentationCompiler.signatureHelp$$anonfun$1(ScalaPresentationCompiler.scala:375)
```
#### Short summary: 

java.lang.AssertionError: NoDenotation.owner