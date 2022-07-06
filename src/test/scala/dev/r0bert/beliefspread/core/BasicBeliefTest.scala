package dev.r0bert.beliefspread.core

import java.util.UUID
import scala.collection.mutable.HashMap
import org.apache.commons.lang3.reflect.FieldUtils

class BasicBeliefTest extends munit.FunSuite {
  test("constructor assigns uuid") {
    val uuid = UUID.randomUUID
    val belief = BasicBelief("b", uuid)
    assertEquals(belief.uuid, uuid)
  }

  test("constructor assigns random uuid") {
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")
    assertNotEquals(b1.uuid, b2.uuid)
  }

  test("constructor assigns name") {
    val b = BasicBelief("b")
    assertEquals(b.name, "b")
  }

  test("full constructor assigns name") {
    val b = BasicBelief("b", UUID.randomUUID)
    assertEquals(b.name, "b")
  }

  test("getPerception when exists") {
    val belief = BasicBelief("b")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()
    perception.put(behaviour, 0.5)
    FieldUtils.writeField(belief, "perception", perception, true)
    assertEquals(belief.getPerception(behaviour), Some(0.5))
  }
}
