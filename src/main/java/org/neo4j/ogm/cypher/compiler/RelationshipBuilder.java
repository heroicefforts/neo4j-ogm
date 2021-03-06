/*
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This product is licensed to you under the Apache License, Version 2.0 (the "License").
 * You may not use this product except in compliance with the License.
 *
 * This product may include a number of subcomponents with
 * separate copyright notices and license terms. Your use of the source
 * code for these subcomponents is subject to the terms and
 * conditions of the subcomponent's license, as noted in the LICENSE file.
 *
 */

package org.neo4j.ogm.cypher.compiler;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to compile Cypher that holds information about a relationship
 *
 * @author Adam George
 * @author Vince Bickers
 * @author Luanne Misquitta
 */
public abstract class RelationshipBuilder implements CypherEmitter, Comparable<RelationshipBuilder> {

    protected Long id;
    protected String type;
    protected String startNodeIdentifier;
    protected String endNodeIdentifier;
    protected String reference;
    private Boolean singleton = true; // will be false if the relationship can be mapped multiple times between two instances
    private String direction;

    final Map<String, Object> props = new HashMap<>();

    public Boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(Boolean singleton) {
        this.singleton = singleton;
    }

    protected RelationshipBuilder(String variableName) {
        this.reference = variableName;
    }

    public String getType() {
        return this.type;
    }

    public RelationshipBuilder type(String type) {
        this.type = type;
        return this;
    }

    public void addProperty(String propertyName, Object propertyValue) {
        this.props.put(propertyName, propertyValue);
    }

    public RelationshipBuilder direction(String direction) {
        this.direction = direction;
        return this;
    }

    public boolean hasDirection(String direction) {
        return this.direction != null && this.direction.equals(direction);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract void relate(String startNodeIdentifier, String endNodeIdentifier);

    public abstract boolean isNew();

    public String getReference() {
        return reference;
    }

    @Override
    public int compareTo(RelationshipBuilder o) {
        return reference.compareTo(o.reference);
    }

    @Override
    public String toString() {
        return "(" + startNodeIdentifier + ")-[" + reference + ":`" + type + "`]->(" + endNodeIdentifier + ")";
    }
}
