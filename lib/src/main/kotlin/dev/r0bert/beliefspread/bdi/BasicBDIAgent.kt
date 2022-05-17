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

package dev.r0bert.beliefspread.bdi

import dev.r0bert.beliefspread.core.BasicAgent
import dev.r0bert.beliefspread.core.Belief
import dev.r0bert.beliefspread.core.UnstableAPI
import java.util.*

/**
 * A [BasicBDIAgent] is an implementation of [BDIAgent], extending from [BasicAgent].
 *
 * @param uuid The [UUID] of the agent.
 * @constructor Create a new [BasicBDIAgent] with a [uuid].
 * @author Robert Greener
 * @since v0.13.0
 */
@UnstableAPI
open class BasicBDIAgent(uuid: UUID) : BasicAgent(uuid), BDIAgent {
    /**
     * Create a new [BasicBDIAgent] with a random [UUID].
     *
     * The [UUID] is generated with [UUID.randomUUID]
     * @author Robert Greener
     * @since v0.13.0
     */
    @UnstableAPI
    constructor() : this(UUID.randomUUID())

    @UnstableAPI
    @Deprecated("Not yet implemented", level = DeprecationLevel.ERROR)
    override fun chooseAction(time: UInt, beliefs: Collection<Belief>) {
        TODO("Not yet implemented")
    }
}