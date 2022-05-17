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

import dev.r0bert.beliefspread.core.UnstableAPI
import org.apache.commons.lang3.reflect.FieldUtils
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(UnstableAPI::class)
class BasicBDIAgentTest {
    @Test
    fun `constructor assigns uuid`() {
        val uuid = UUID.randomUUID()
        val a = BasicBDIAgent(uuid)
        assertEquals(uuid, (FieldUtils.readField(a, "uuid", true) as UUID))
    }

    @Test
    fun `constructor assigns random uuid`() {
        val a1 = BasicBDIAgent()
        val a2 = BasicBDIAgent()
        assertNotEquals(
            FieldUtils.readField(a1, "uuid", true) as UUID,
            FieldUtils.readField(a2, "uuid", true) as UUID
        )
    }
}