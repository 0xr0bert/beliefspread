package dev.r0bert.beliefspread.core

import java.util.UUID
import scala.collection.mutable
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

  test("getPerception when not exists") {
    val belief = BasicBelief("belief")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()
    FieldUtils.writeField(belief, "perception", perception, true)
    assertEquals(belief.getPerception(behaviour), None)
  }

  test("setPerception delete when exists") {
    val belief = BasicBelief("belief")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()
    perception.put(behaviour, 0.5)
    FieldUtils.writeField(belief, "perception", perception, true)

    belief.setPerception(behaviour, None)
    assertEquals(perception.get(behaviour), None)
  }

  test("setPerception delete when not exists") {
    val belief = BasicBelief("belief")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()
    FieldUtils.writeField(belief, "perception", perception, true)

    belief.setPerception(behaviour, None)
    assertEquals(perception.get(behaviour), None)
  }

  test("setPerception when too high") {
    val belief = BasicBelief("belief")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()
    FieldUtils.writeField(belief, "perception", perception, true)

    interceptMessage[IllegalArgumentException]("perception is greater than 1")
  }

  test("setPerception when too low") {
    val belief = BasicBelief("belief")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()

    interceptMessage[IllegalArgumentException]("perception is less than -1")
  }

  test("setPerception when exists") {
    val belief = BasicBelief("belief")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()
    perception.put(behaviour, 0.5)
    FieldUtils.writeField(belief, "perception", perception, true)

    belief.setPerception(behaviour, Some(0.2))
    assertEquals(perception.get(behaviour), Some(0.2))
  }

  test("setPerception when not exists") {
    val belief = BasicBelief("belief")
    val behaviour = BasicBehaviour("b")
    val perception = HashMap[Behaviour, Double]()
    FieldUtils.writeField(belief, "perception", perception, true)

    belief.setPerception(behaviour, Some(0.2))
    assertEquals(perception.get(behaviour), Some(0.2))
  }

  test("perception is empty on initalization") {
    val belief = BasicBelief("belief")
    assert(
      (FieldUtils
        .readField(belief, "perception", true)
        .asInstanceOf[mutable.Map[Behaviour, Double]]
        .isEmpty)
    )
  }

  test("relationship is empty on initialization") {
    val belief = BasicBelief("belief")
    assert(
      FieldUtils
        .readField(belief, "relationship", true)
        .asInstanceOf[mutable.Map[Belief, Double]]
        .isEmpty
    )
  }

  test("getRelationship when exists") {
    val b1 = BasicBelief("b1")
    val b2 = BasicBelief("b2")
    val relationship = HashMap[Belief, Double]()
    relationship.put(b2, 0.2)
    FieldUtils.writeField(b1, "relationship", relationship, true)
    assertEquals(b1.getRelationship(b2), Some(0.2))
  }
}
