package homework7.task1;

import java.lang.reflect.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomObjectGenerator {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public <T> T nextObject(Class<T> clazz) {
        return nextObject(clazz, null);
    }

    public <T> T nextObject(Class<T> clazz, String factoryMethodName) {
        try {
            if (factoryMethodName != null) {
                return createViaFactoryMethod(clazz, factoryMethodName);
            }
            return createViaConstructor(clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create object of type " + clazz.getName(), e);
        }
    }

    private <T> T createViaFactoryMethod(Class<T> clazz, String methodName) throws Exception {
        Method factoryMethod = findFactoryMethod(clazz, methodName);
        Parameter[] parameters = factoryMethod.getParameters();
        Object[] args = generateArguments(parameters);
        return (T) factoryMethod.invoke(null, args);
    }

    private <T> T createViaConstructor(Class<T> clazz) throws Exception {
        Constructor<?> constructor = findSuitableConstructor(clazz);
        Parameter[] parameters = constructor.getParameters();
        Object[] args = generateArguments(parameters);
        return (T) constructor.newInstance(args);
    }

    private Method findFactoryMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName) && Modifier.isStatic(method.getModifiers())) {
                return method;
            }
        }
        throw new NoSuchMethodException("Factory method " + methodName + " not found in " + clazz.getName());
    }

    private Constructor<?> findSuitableConstructor(Class<?> clazz) throws NoSuchMethodException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new NoSuchMethodException("No public constructors found for " + clazz.getName());
        }
        return constructors[0];
    }

    private Object[] generateArguments(Parameter[] parameters) throws Exception {
        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            args[i] = generateValueForParameter(parameters[i]);
        }
        return args;
    }

    private Object generateValueForParameter(Parameter parameter) throws Exception {
        Class<?> type = parameter.getType();

        Min minAnnotation = parameter.getAnnotation(Min.class);
        Max maxAnnotation = parameter.getAnnotation(Max.class);
        NotNull notNullAnnotation = parameter.getAnnotation(NotNull.class);

        if (notNullAnnotation != null) {
            return generateNonNullValue(type, minAnnotation, maxAnnotation);
        } else if (random.nextBoolean()) {
            return null;
        }
        return generateNonNullValue(type, minAnnotation, maxAnnotation);
    }

    private Object generateNonNullValue(Class<?> type, Min min, Max max) {
        if (type == int.class || type == Integer.class) {
            int minVal = min != null ? min.value() : Integer.MIN_VALUE/2;
            int maxVal = max != null ? max.value() : Integer.MAX_VALUE/2;
            return random.nextInt(minVal, maxVal);
        } else if (type == long.class || type == Long.class) {
            long minVal = min != null ? min.value() : Long.MIN_VALUE/2;
            long maxVal = max != null ? max.value() : Long.MAX_VALUE/2;
            return random.nextLong(minVal, maxVal);
        } else if (type == double.class || type == Double.class) {
            double minVal = min != null ? min.value() : -Double.MAX_VALUE/2;
            double maxVal = max != null ? max.value() : Double.MAX_VALUE/2;
            return random.nextDouble(minVal, maxVal);
        } else if (type == float.class || type == Float.class) {
            float minVal = min != null ? (float) min.value() : -Float.MAX_VALUE/2;
            float maxVal = max != null ? (float) max.value() : Float.MAX_VALUE/2;
            return minVal + random.nextFloat() * (maxVal - minVal);
        } else if (type == boolean.class || type == Boolean.class) {
            return random.nextBoolean();
        } else if (type == String.class) {
            int length = random.nextInt(5, 20);
            return random.ints(length, 'a', 'z' + 1)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        } else if (type.isRecord() || type.getDeclaredConstructors().length > 0) {
            return nextObject(type);
        }
        return null;
    }
}
