package sweetjson;

import java.lang.reflect.InvocationTargetException;

public class TypeDefinition {
    private final String m_id;
    private final Class<?> m_klass;
    private final Class<?>[] m_type_args;

    private TypeDefinition (final Class<?> klass, Class<?>... args) {
        m_id = klass.getCanonicalName().toLowerCase();
        m_klass = klass;
        m_type_args = args;
    }

    public Class<?> klass () {
        return m_klass;
    }

    public boolean is_array () {
        return klass().isArray();
    }

    public boolean is_json_primitive () {
        if (m_klass.isPrimitive()) return true;
        return m_klass == String.class
                || m_klass == Byte.class
                || m_klass == Short.class
                || m_klass == Character.class
                || m_klass == Integer.class
                || m_klass == Long.class
                || m_klass == Float.class
                || m_klass == Double.class;
    }

    public Class<?>[] type_args () {
        return m_type_args;
    }

    public Class<?> first_type_arg () {
        return m_type_args[0];
    }

    public Class<?> second_type_arg () {
        return m_type_args[1];
    }

    public boolean has_type_args () {
        return m_type_args != null;
    }

    @Override
    public int hashCode () {
        return m_id.hashCode() + 888;
    }

    @Override
    public boolean equals (final Object other) {
        if (!(other instanceof TypeDefinition))
            return false;
        var other_type = (TypeDefinition) other;
        return other_type.m_id.equals(m_id);
    }

    public Object create_instance () {
        try {
            return klass().getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | InvocationTargetException exc) {
            throw new RuntimeException(exc);
        }
    }

    public static TypeDefinition wrap (final Class<?> klass, Class<?>... type_args) {
        return new TypeDefinition(klass, type_args);
    }
}