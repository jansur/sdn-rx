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

import static org.neo4j.springframework.data.core.cypher.Expressions.*;

import org.apiguardian.api.API;
import org.neo4j.springframework.data.core.cypher.support.Visitor;

/**
 * @author Michael J. Simons
 * @since 1.0
 */
@API(status = API.Status.INTERNAL, since = "1.0")
public final class NestedExpression implements Expression {

	private final Expression delegate;

	NestedExpression(Expression delegate) {
		this.delegate = delegate;
	}

	@Override
	public void accept(Visitor visitor) {

		visitor.enter(this);
		nameOrExpression(this.delegate).accept(visitor);
		visitor.leave(this);
	}
}
