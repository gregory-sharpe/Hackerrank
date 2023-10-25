import scala.io._
import scala.collection.mutable.Map
import scala.collection.mutable.ListBuffer
import scala.annotation.tailrec

object Solution {
  type OptionaldnaNodeList = ListBuffer[Option[DNAHealthNode]]
  case class DNAHealthNode(var value: Int = 0) {
    val children = Map[Char, DNAHealthNode]()

    def update(geneBit: Char, healthValue: Int = 0): DNAHealthNode = {
      var childNode: DNAHealthNode = this
      children.get(geneBit) match {
        case Some(dnaMap) => {
          dnaMap.value += healthValue
          childNode = dnaMap
        }
        case None => {
          childNode = DNAHealthNode(healthValue)
          children(geneBit) = childNode
        }
      }
      childNode
    }
    def getChild(geneBit: Char) = {
      children.get(geneBit)
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

  def main(args: Array[String]) = {
    val n = StdIn.readLine.trim.toInt
    val genes = StdIn.readLine.replaceAll("\\s+$", "").split(" ")
    val health =
      StdIn.readLine.replaceAll("\\s+$", "").split(" ").map(_.trim.toInt)
    val geneOccurences = genes.zip(health) // string

    val s = StdIn.readLine.trim.toInt
    var healthPoints = List.empty[Int]
    val listOfTotalHealthPoints = ListBuffer.empty[Int]

    // creating cached Trees
    val step = List(genes.size / 100, 1).max
    val range100Parts = 0 to genes.size by step
    val allChachedRanges = range100Parts.zip(range100Parts.tail)
    val cache = allChachedRanges.zip(allChachedRanges.map { (bounds) =>
      val lBound = bounds._1
      val uBound = bounds._2
      val root = DNAHealthNode()
      val geneSection = geneOccurences.slice(lBound, uBound )
      for ((gene: String, value) <- geneSection) {
        addToHashTree(root, gene, value)
      }
      root
    })
    //throw new Exception("cache created")
    

    // val cachedRanges = allChachedRanges.filter((lBond,uBound)=>startindex<=lBond && finalindex>= uBound)

    for (sItr <- 1 to s) {
      val firstMultipleInput = StdIn.readLine.replaceAll("\\s+$", "").split(" ")
      val first = firstMultipleInput(0).toInt
      val last = firstMultipleInput(1).toInt
      val d = firstMultipleInput(2) // string to be parsed by tree

      /*
     first idea used one tree as a parser.
     new idea understands that trees can be created once and reused
      val geneSection =
        geneOccurences.slice(first, last + 1).toList //.filter(_._1.size <= d.size)
      first idea was to
      val root = DNAHealthNode()
      for ((gene: String, value) <- geneSection) {
        addToHashTree(root, gene, value)
      }
       */
      val usefulcache = cache.toList.filter { case (bounds, _) =>
        first <= bounds._1 && last >= bounds._2
      }
      println(usefulcache.map{case (range,_)=>range})
      println(first.toString()+" "+usefulcache.head._1._1)
      println(usefulcache.last._1._2.toString()+" "+last)
      val uncachedStart = geneOccurences.slice(first, usefulcache.head._1._1)
      val unCachedStartNode = DNAHealthNode()
      for ((gene: String, value) <- uncachedStart) {
        addToHashTree(unCachedStartNode, gene, value)
      }

      val uncachedEndNode = DNAHealthNode()
      val unCachedEnd = geneOccurences.slice(usefulcache.last._1._2, last+1 )
      for ((gene: String, value) <- unCachedEnd) {
        addToHashTree(uncachedEndNode, gene, value)
      }

      val treeParsers =
        unCachedStartNode :: uncachedEndNode :: (usefulcache).map {
          case (_, tree) => tree
        }
      val total = treeParsers.map { (root) =>
        var listOfTokens = new OptionaldnaNodeList()
        var treeTotal = 0
        for (geneBit <- d) {
          listOfTokens = traverseTree(root, geneBit, listOfTokens)
          treeTotal += listOfTokens.flatten.map(_.value).sum
        }
        treeTotal
      }.sum
      println(total)

      // instead will used multiple parsers. Cached parsers with the lowerpart and upperpart
      /*
      var listOfTokens = new OptionaldnaNodeList()
      var total = 0
      for (geneBit <- d) {
        listOfTokens = traverseTree(root, geneBit, listOfTokens)
        total += listOfTokens.flatten.map(_.value).sum
      }
       */
      listOfTotalHealthPoints += total
    }
    println(listOfTotalHealthPoints.min + " " + listOfTotalHealthPoints.max)
  }
}
