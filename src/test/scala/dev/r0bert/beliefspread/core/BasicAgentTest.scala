package dev.r0bert.beliefspread.core

import java.util.UUID

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
}
