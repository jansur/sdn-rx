/*
 * Copyright (c) 2019 "Neo4j,"
 * Neo4j Sweden AB [https://neo4j.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.springframework.data.core.cypher;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * @author Michael J. Simons
 */
class PropertyTest {

	@Test
	void preconditionsShouldBeAsserted() {

		assertThatIllegalArgumentException().isThrownBy(() -> Property.create(Node.create("a"), ""))
			.withMessage("A property derived from a node needs a parent with a symbolic name.");
		String expectedMessage = "The properties name is required.";
		assertThatIllegalArgumentException().isThrownBy(() -> Property.create(Node.create("a").named("s"), null))
			.withMessage(
				expectedMessage);
		assertThatIllegalArgumentException().isThrownBy(() -> Property.create(Node.create("a").named("s"), "\t"))
			.withMessage(
				expectedMessage);
	}
}
