import scala.util.matching.Regex
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec
import scala.util.Random

type OptionaldnaNodeList = ListBuffer[Option[DNAHealthNode]]
case class OccurencesOfDNA(gene: String, value: Int) {
  // obviously too slow but works for small inputs
  def findOccurences(DNASection: String) = {
    var geneRegex: Regex = gene.r
    var total = 0
    // using lookahead for overlapping tokens
    if (DNASection.size > 1) {
      geneRegex = (gene.head.toString() + "(?=" + gene.tail + ")").r
    }
    for (geneMatch <- geneRegex.findAllMatchIn(DNASection)) {
      total += value
    }
    total
  }
}
case class DNAHealthNode(var value: Int = 0) {
  val geneMap = Map[Char, DNAHealthNode]()

  def update(geneBit: Char, healthValue: Int = 0): DNAHealthNode = {
    var childNode: DNAHealthNode = this
    geneMap.get(geneBit) match {
      case Some(dnaMap) => {
        dnaMap.value += healthValue
        childNode = dnaMap
      }
      case None => {
        childNode = DNAHealthNode(healthValue)
        geneMap(geneBit) = childNode
      }
    }
    childNode
  }
  def getChild(geneBit: Char) = {
    geneMap.get(geneBit)
  }
}
@tailrec
def addToHashTree(
    root: DNAHealthNode,
    gene: String,
    value: Int
): DNAHealthNode = {
  if (gene.size == 0) {
    root.value += value
    root
  } else {
    addToHashTree(root.update(gene.head), gene.tail, value)
  }
}
def traverseTree(
    root: DNAHealthNode,
    geneBit: Char,
    listOfNodes: OptionaldnaNodeList
) = {
  listOfNodes += Some(root)
  val newListOfNodes = listOfNodes.flatten.map(_.getChild(geneBit))
  newListOfNodes
}

object test {
  @main def main(args: String*) = {
    type number = Int
    val input = "caaab"

    val root = DNAHealthNode()
    addToHashTree(root, "b", 2)
    addToHashTree(root, "c", 3)
    addToHashTree(root, "aa", 4)
    addToHashTree(root, "d", 5)
    addToHashTree(root, "b", 6)

    var listOfTokens = new OptionaldnaNodeList()
    var total:number = 0
    for (geneBit <- input) {
      listOfTokens = traverseTree(root, geneBit, listOfTokens)
      total += listOfTokens.flatten.map(_.value).sum
    }
    val last = 1000
    val startindex = 50
    val finalindex = 134

    val range = 0 to last  by last / 100
    val allChachedRanges = range.zip(range.tail).toList
    val cachedRanges = allChachedRanges.filter((lBond,uBound)=>startindex<=lBond && finalindex>= uBound)
    val cache = allChachedRanges.zip( allChachedRanges.map{(lBound,uBound)=>
        val root = DNAHealthNode()
        
        addToHashTree(root, "a", Random.nextInt(10))
        root
    })
    val usefulCaches = cache.filter( (bounds,_) => startindex<=bounds._1&& finalindex>= bounds._2)
    val uncachedFirstpart = 
    
    println(cachedRanges)
    println(startindex to cachedRanges.head._1)
    println(cachedRanges.last._2 to finalindex)
  }
}
