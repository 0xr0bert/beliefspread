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

/** A [BasicBehaviour] is an implementation of [Behaviour].
  *
  * @param name
  *   The name of the [BasicBehaviour].
  * @param uuid
  *   The [UUID] of the [BasicBehaviour].
  * @constructor
  *   Create a new [BasicBehaviour] with a supplied `name` and `uuid`.
  * @author
  *   Robert Greener
  * @since v0.14.0
  */
class BasicBehaviour(override var name: String, override var uuid: UUID)
    extends Behaviour {

  /** Create a new [BasicBehaviour] with a random [UUID].
    *
    * [UUID.randomUUID] is used to randomly generate the UUID.
    *
    * @param name
    *   The `name` of the [BasicBehaviour].
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  def this(name: String) = this(name, UUID.randomUUID)

  /** Compare equality between this [BasicBehaviour] and another
    * [BasicBehaviour]
    *
    * They are equal iff. the [UUID] is equal.
    *
    * @param other
    *   The other [Any].
    * @return
    *   true If the [UUID] is equal.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  override def equals(other: Any): Boolean = other match {
    case other: BasicBehaviour => this.uuid == other.uuid
    case _                     => false
  }

  /** Get the `hashCode` of the [BasicBelief].
    *
    * This is solely based on the [UUID].
    *
    * @return
    *   The hashCode.
    * @author
    *   Robert Greener
    * @since v0.14.0
    */
  override def hashCode(): Int = uuid.hashCode
}
