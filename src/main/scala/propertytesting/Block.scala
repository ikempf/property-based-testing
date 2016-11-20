package propertytesting

case class Task(tag: String)

trait Block {
  val tag: Option[String]
}

case class StepBlock(tasks: List[Task]) extends Block {
  override val tag = {
    val tags = tasks.map(_.tag).toSet
    if (tags.size > 1)
      None
    else
      tags.headOption.map(Option.apply).getOrElse(None)
  }
}

object StepBlock {
  def unapply(block: Block): Option[(List[Task], Option[String])] =
    block match {
      case stepBlock: StepBlock =>
        Some(stepBlock.tasks, stepBlock.tag)
      case _ =>
        None
    }
}

case class CompositeBlock(blocks: List[Block]) extends Block {
  override val tag = {
    val tags = blocks.flatMap(_.tag).toSet
    if (tags.size > 1)
      None
    else
      tags.headOption.map(Option.apply).getOrElse(None)
  }

}

object CompositeBlock {
  def unapply(block: Block): Option[(List[Block], Option[String])] =
    block match {
      case compositeBlock: CompositeBlock =>
        Some(compositeBlock.blocks, compositeBlock.tag)
      case _ =>
        None
    }
}
