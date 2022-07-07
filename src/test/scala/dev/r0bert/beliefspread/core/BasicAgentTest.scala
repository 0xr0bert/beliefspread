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
}
