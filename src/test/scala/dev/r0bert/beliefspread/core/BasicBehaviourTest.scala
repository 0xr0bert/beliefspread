package dev.r0bert.beliefspread.core

import java.util.UUID

class BasicBehaviourTest extends munit.FunSuite {
  test("constructor assigns uuid") {
    val uuid = UUID.randomUUID
    val behaviour = BasicBehaviour("b", uuid)
    assertEquals(behaviour.uuid, uuid)
  }

  test("constructor assigns random uuid") {
    val b1 = BasicBehaviour("b")
    val b2 = BasicBehaviour("b")
    assertNotEquals(b1.uuid, b2.uuid)
  }

  test("constructor assigns name") {
    val b = BasicBehaviour("b")
    assertEquals(b.name, "b")
  }

  test("full constructor assigns name") {
    val b = BasicBehaviour("b", UUID.randomUUID)
    assertEquals(b.name, "b")
  }

  test("equals when different class") {
    val b = BasicBehaviour("b")
    assertEquals(b.equals("b"), false)
  }

  test("equals when same class but different UUID") {
    val b1 = BasicBehaviour("b")
    val b2 = BasicBehaviour("b")
    assertEquals(b1.equals(b2), false)
  }

  test("equals when same UUID") {
    val uuid = UUID.randomUUID
    val b1 = BasicBehaviour("b1", uuid)
    val b2 = BasicBehaviour("b2", uuid)
    assertEquals(b1.equals(b2), true)
  }

  test("hashCode") {
    val uuid = UUID.randomUUID
    val b = BasicBehaviour("b", uuid)
    assertEquals(b.hashCode, uuid.hashCode)
  }
}
