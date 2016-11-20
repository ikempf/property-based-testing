package propertytesting

import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSuite, Matchers}
import propertytesting.BlockGenerators.blocks

class BlockSpec extends FunSuite with Matchers with PropertyChecks {

  test("tags rules") {
    forAll(blocks)(checkTagRecursively)
  }

  private def checkTagRecursively(block: Block): Unit =
    block match {
      case CompositeBlock(blocks, Some(tag)) =>
        val blockTags = blocks.map(_.tag).toSet
        assertCommonTag(blockTags.flatten, tag)
        blocks.foreach(checkTagRecursively)

      case CompositeBlock(blocks, None) =>
        val blockTags = blocks.flatMap(_.tag).toSet
        assertNoCommonTag(blockTags)
        blocks.foreach(checkTagRecursively)

      case StepBlock(tasks, Some(tag)) =>
        val taskTags = tasks.map(_.tag).toSet
        assertCommonTag(taskTags, tag)

      case StepBlock(tasks, None) =>
        val taskTags = tasks.map(_.tag).toSet
        assertNoCommonTag(taskTags)
    }

  private def assertCommonTag(tags: Set[String], tag: String): Unit =
    tags should be(Set(tag))

  private def assertNoCommonTag(tags: Set[String]): Unit =
    tags.size should not be 1

}