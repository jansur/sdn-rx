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

import static org.neo4j.springframework.data.core.cypher.support.Visitable.*;

import java.util.LinkedList;

import org.apiguardian.api.API;
import org.neo4j.springframework.data.core.cypher.support.Visitor;
import org.springframework.util.Assert;

/**
 * Represents a chain of relationships. The chain is meant to be in order and the right node of an element is related to
 * the left node of the next element.
 *
 * @author Michael J. Simons
 * @since 1.0
 */
@API(status = API.Status.INTERNAL, since = "1.0")
public final class RelationshipChain implements PatternElement, ExposesRelationships<RelationshipChain> {

	private final LinkedList<Relationship> relationships = new LinkedList<>();

	static RelationshipChain create(Relationship firstElement) {

		return new RelationshipChain()
			.add(firstElement);
	}

	RelationshipChain add(Relationship element) {

		Assert.notNull(element, "Elements of a relationship chain must not be null.");
		this.relationships.add(element);
		return this;
	}

	@Override
	public RelationshipChain relationshipTo(Node other, String... types) {
		return this.add(this.relationships.getLast().getRight().relationshipTo(other, types));
	}

	@Override
	public RelationshipChain relationshipFrom(Node other, String... types) {
		return this.add(this.relationships.getLast().getRight().relationshipFrom(other, types));
	}

	@Override
	public RelationshipChain relationshipBetween(Node other, String... types) {
		return this.add(this.relationships.getLast().getRight().relationshipBetween(other, types));
	}

	/**
	 * Replaces the last element of this chains with a copy of the relationship with the new symbolic name..
	 *
	 * @param newSymbolicName The new symbolic name to use
	 * @return This chain
	 */
	public RelationshipChain named(String newSymbolicName) {

		Relationship lastElement = this.relationships.removeLast();
		return this.add(lastElement.named(newSymbolicName));
	}

	@Override
	public void accept(Visitor visitor) {

		visitor.enter(this);

		Node lastNode = null;
		for (Relationship relationship : relationships) {

			relationship.getLeft().accept(visitor);
			relationship.getDetails().accept(visitor);

			lastNode = relationship.getRight();
		}

		visitIfNotNull(lastNode, visitor);

		visitor.leave(this);
	}
}
