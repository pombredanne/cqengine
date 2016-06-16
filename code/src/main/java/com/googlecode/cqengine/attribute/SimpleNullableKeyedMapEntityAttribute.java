/**
 * Copyright 2012-2015 Niall Gallagher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.entity.KeyedMapEntity;
import com.googlecode.cqengine.query.option.QueryOptions;

/**
 * Created by npgall on 23/05/2016.
 * TODO - is this class needed, couldn't the MapEntity version work?
 */
public class SimpleNullableKeyedMapEntityAttribute<K, A> extends SimpleNullableAttribute<KeyedMapEntity, A> {

    final K mapKey;

    public SimpleNullableKeyedMapEntityAttribute(K mapKey, Class<A> mapValueType) {
        super(KeyedMapEntity.class, mapValueType, mapKey.toString());
        this.mapKey = mapKey;
    }

    public SimpleNullableKeyedMapEntityAttribute(K mapKey, Class<A> mapValueType, String attributeName) {
        super(KeyedMapEntity.class, mapValueType, attributeName);
        this.mapKey = mapKey;
    }

    @Override
    public A getValue(KeyedMapEntity map, QueryOptions queryOptions) {
        Object result = map.get(mapKey);
        if (result == null || getAttributeType().isAssignableFrom(result.getClass())) {
            return getAttributeType().cast(result);
        }
        throw new ClassCastException("Cannot cast " + result.getClass().getName() + " to " + getAttributeType().getName() + " for map key: " + mapKey);
    }

     @Override
     public int hashCode() {
         int result = super.hashCode();
         result = 31 * result + mapKey.hashCode();
         return result;
     }

    @Override
    public boolean canEqual(Object other) {
        return other instanceof SimpleNullableKeyedMapEntityAttribute;
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) && this.mapKey.equals(((SimpleNullableKeyedMapEntityAttribute)other).mapKey);
    }
}