package dev.r0bert.beliefspread.core

import java.util.UUID
import org.apache.commons.lang3.reflect.FieldUtils
import scala.collection.mutable
import scala.collection.mutable.HashMap

class BasicAgentTest extends munit.FunSuite {
  test("constructor assigns uuid") {
    val uuid = UUID.randomUUID()
    val a = BasicAgent(uuid)
    assertEquals(a.uuid, uuid)
  }

  test("constructor assigns random uuid") {
    val a1 = BasicAgent()
    val a2 = BasicAgent()
    assertNotEquals(a1.uuid, a2.uuid)
  }

  test("equals when uuids equal") {
    val uuid = UUID.randomUUID
    val a1 = BasicAgent(uuid)
    val a2 = BasicAgent(uuid)

    assertEquals(a1, a2)
  }

  test("equals when same object") {
    val a = BasicAgent()
    assertEquals(a, a)
  }

  test("equals when uuids different") {
    val a1 = BasicAgent()
    val a2 = BasicAgent()
    assertNotEquals(a1, a2)
  }

  test("test hashCode") {
    val uuid = UUID.randomUUID
    val a = BasicAgent(uuid)
    assertEquals(a.hashCode, uuid.hashCode)
  }

  test("activation is initialized empty") {
    val agent = BasicAgent()
    assert(
      FieldUtils
        .readField(agent, "activation", true)
        .asInstanceOf[mutable.Map[Int, mutable.Map[Belief, Double]]]
        .isEmpty
    )
  }

  test("getActivation when exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    actAt2.put(b, 0.5)
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    assertEquals(a.getActivation(2, b), Some(0.5))
  }

  test("getActivation when time exists but belief doesn't") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    val actAt2 = HashMap[Belief, Double]()
    act.put(2, actAt2)
    FieldUtils.writeField(a, "activation", act, true)
    assertEquals(a.getActivation(2, b), None)
  }

  test("getActivation when not exists") {
    val a = BasicAgent()
    val b = BasicBelief("b")
    val act = HashMap[Int, mutable.Map[Belief, Double]]()
    FieldUtils.writeField(a, "activation", act, true)
    assertEquals(a.getActivation(2, b), None)
  }
}
