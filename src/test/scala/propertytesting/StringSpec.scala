package propertytesting

import org.scalacheck.Gen
import org.scalatest.prop.PropertyChecks
import org.scalatest.{FunSuite, Matchers}

class StringSpec extends FunSuite with Matchers with PropertyChecks {

  test("trim") {
    val s: String = "my string "
    s.trim().length should be <= s.length
  }

  test("concatenation") {
    val left = "abc"
    val right = "def"

    left + right should be("abcdef")
  }

  test("concatenation property") {
    forAll(Gen.alphaStr, Gen.alphaStr) {
      (left, right) =>
        val result = left + right

        result should startWith(left)
        result should endWith(right)
        result.length should be >= left.length
    }
  }

}