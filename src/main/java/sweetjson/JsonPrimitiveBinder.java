/*
 * Copyright (C) 2020 Devashish Jaiswal.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sweetjson;

public class JsonPrimitiveBinder<T> implements JsonBinder<T>
{
    public static final JsonBinder<?> INSTANCE = new JsonPrimitiveBinder<>();

    @SuppressWarnings("unchecked")
    @Override
    public T construct (final JsonValue value, final Typedef<T> definition, final Bag bag)
    {
        var type = value.get_type();
        return switch (type)
                {
                    case STRING -> (T) value.as_string();
                    case NUMBER -> (T) JsonUtils.get_number_field(value, definition.klass());
                    case BOOL -> (T) Boolean.valueOf(value.as_bool());
                    case NULL -> (T) value.as_object();
                    default -> throw new RuntimeException("Attempted to construct primitive from `" + (type) + "`!");
                };
    }
}
