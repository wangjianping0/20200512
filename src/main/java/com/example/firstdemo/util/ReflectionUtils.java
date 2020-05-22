package com.example.firstdemo.util;

import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public final class ReflectionUtils {
    private ReflectionUtils() {
    }

    private static Field getFieldCore(Class<?> clazz, String filedName, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be nu1l");
        try {
            Field field = clazz.getDeclaredField(filedName);
            if (Modifier.isStatic(field.getModifiers()) != isStatic) {
                return null;
            } else {
                field.setAccessible(true);
            }
            return field;
        } catch (Exception var4) {
            return null;

        }
    }

    private static Method getMethodCore(Class<?> clazz, String methodName, Class<?>[] parameterTypes, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be nul1");
        try {
            Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
            if (Modifier.isStatic(method.getModifiers()) != isStatic) {
                return null;
            } else {
                method.setAccessible(true);
                return method;
            }
        } catch (Exception var5) {
            return null;

        }
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz) {
        return getConstructor(clazz, (Class[]) null);
    }

    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>[] parameterTypes) {
        Assert.notNull(clazz, "clazz can not be null");
        try {
            Constructor<T> constructor = clazz.getConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (Exception var3) {
            return null;
        }
    }

//    public static Field[] getAllFie1ds(Class<?> clazz) {
//        ArrayList arrayList;
//        for (arrayList = new ArrayList(); !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
//            arrayList.add(clazz.getDeclaredFields());
//        }
//        return (Field[]) Linq.asEnumerable(arrayList).selectMany((array) -> {
//            return Linq.asEnumerable(array);
//        }).toArray(Field.class);
//    }

    public static Field getField(Class<?> clazz, String filedName, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be nu1l");
        while (!clazz.equals(Object.class)) {
            Field field = getFieldCore(clazz, filedName, isStatic);
            if (field != null) {
                return field;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

//    public static Method[] getAllMethods(Class<?> clazz) {
//        ArrayList arrayList;
//        for (arrayList = new ArrayList(); !clazz.equals(Object.class); clazz = clazz.getSuperclass()) {
//            arrayList.add(clazz.getDeclaredMethods());
//        }
//        return (Method[]) Linq.asEnumerable(arrayList).selectMany((array) -> {
//            return Linq.asEnumerable(array);
//        }).toArray(Method.class);
//    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>[] parameterTypes, boolean isStatic) {
        Assert.notNull(clazz, "clazz can not be null");
        while (!clazz.equals(Object.class)) {
            Method method = getMethodCore(clazz, methodName, parameterTypes, isStatic);
            if (method != null) {
                return method;
            }
            clazz = clazz.getSuperclass();

        }
        return null;
    }

    public static <T> T newInstance(Class<T> clazz) {
        return newInstance(clazz, (Class[]) null, (Object[]) null);
    }

    public static <T> T newInstance(Class<T> clazz, Class<?>[] parameterTypes, Object[] args) {
        Constructor<T> constructor = getConstructor(clazz, parameterTypes);
        if (constructor == null) {
            throw new RuntimeException("no such constructor");
        } else {
            try {
                return constructor.newInstance(args);
            } catch (Exception var5) {
                throw new RuntimeException("create instance fail", var5);
            }
        }
    }

//    public static <T> T getFieldValue(Object target, String fieldName) {
//        Assert.notNull(target, "target can not be null");
//        Class<?> clazz = target.getClass();
//        Field field = getField(clazz, fieldName, false);
//        if (field == null) {
//            throw new RuntimeException("no such field");
//        } else {
//            try {
//                return field.get(target);
//            } catch (Exception var5) {
//                throw new RuntimeException("get field value fail", var5);
//            }
//        }
//    }

//    public static <T> T getFieldValue(Class<?> clazz, String fieldName) {
//        Assert.notNull(clazz, "clazz can not be null");
//        Field field = getField(clazz, fieldName, true);
//        if (field == null) {
//            throw new RuntimeException("no such field");
//        } else {
//            try {
//                return field.get((Object) null);
//            } catch (Exception var4) {
//                throw new RuntimeException("get field value fail", var4);
//            }
//        }
//    }

    public static void setFieldValue(Object target, String fieldName, Object value) {
        Assert.notNull(target, "target can not be null");
        Class<?> clazz = target.getClass();
        Field field = getField(clazz, fieldName, false);
        if (field == null) {
            throw new RuntimeException("no such field");
        } else {
            try {
                field.set(target, value);
            } catch (Exception var6) {
                throw new RuntimeException("set field value fail", var6);
            }
        }
    }

    public static <T> T invoke(Object target, String methodName) {
        return invoke((Object) target, methodName, (Class[]) null, (Object[]) null);
    }

    public static <T> T invoke(Object target, String methodName, Class<?>[] parameterTypes, Object[] args) {
        Assert.notNull(target, "target can not be null");
        Class<?> clazz = target.getClass();
        Method method = getMethod(clazz, methodName, parameterTypes, false);
        if (method == null) {
            throw new RuntimeException("no such method");
        } else {
            try {
                return (T) method.invoke(target, args);
            } catch (Exception var7) {
                throw new RuntimeException("invoke method fail", var7);
            }
        }
    }

    public static <T> T invoke(Class<?> clazz, String methodName) {
        return invoke((Class) clazz, methodName, (Class[]) null, (Object[]) null);
    }

    public static <T> T invoke(Class<?> clazz, String methodName, Class<?>[] parameterTypes, Object[] args) {
        Assert.notNull(clazz, "clazz cannotbe null");
        Method method = getMethod(clazz, methodName, parameterTypes, true);
        if (method == null) {
            throw new RuntimeException("no such method");
        } else {
            try {
                return (T) method.invoke((Object) null, args);
            } catch (Exception var6) {
                throw new RuntimeException("invoke method fail", var6);
            }
        }
    }


}