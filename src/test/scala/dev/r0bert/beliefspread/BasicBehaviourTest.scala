/*
 * belief-spread
 * Copyright (c) 2022 Robert Greener
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the LICENSE, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public LICENSE
 * along with this program. If not, see <https://www.gnu.org/licenses>
 */
package dev.r0bert.beliefspread

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
