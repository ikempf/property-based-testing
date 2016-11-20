package propertytesting

import org.scalacheck.Gen

object BlockGenerators {

  def tags: Gen[String] = Gen.listOfN(10, Gen.alphaNumChar).map(_.mkString)

  def tasks: Gen[Task] = tags.map(tag => Task(tag))

  def stepBlocks: Gen[StepBlock] = Gen.nonEmptyListOf(tasks).map(taskList => StepBlock(taskList))

  def blocks: Gen[Block] = Gen.oneOf(stepBlocks, compositeBlocks)

  def wrongCompositeBlocks: Gen[CompositeBlock] = Gen.nonEmptyListOf(blocks).map(blockList => CompositeBlock(blockList))
  def compositeBlocks: Gen[CompositeBlock] = Gen.sized { size =>
    for {
      convergenceRate <- Gen.choose(0, size)
      nextIterationSize = size / Math.max(1, convergenceRate)
      convergingGenerator <- Gen.resize(nextIterationSize, blocks)
      blockList <- Gen.listOfN(size, convergingGenerator)
    } yield new CompositeBlock(blockList)
  }


}
