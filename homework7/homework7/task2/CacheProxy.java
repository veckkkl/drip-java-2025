package homework7.task2;

import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

public class CacheProxy implements InvocationHandler {
    private final Object target;
    private final Map<Method, Map<List<Object>, Object>> memoryCache = new HashMap<>();
    private final Path tempDir;

    private CacheProxy(Object target) {
        this.target = target;
        this.tempDir = Paths.get(System.getProperty("java.io.tmpdir"), "cache-proxy");
        try {
            Files.createDirectories(tempDir);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create cache directory", e);
        }
    }

    public static <T> T create(T target, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new CacheProxy(target));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Cache cacheAnnotation = method.getAnnotation(Cache.class);
        if (cacheAnnotation == null) {
            return method.invoke(target, args);
        }

        List<Object> key = Arrays.asList(args);
        Object cachedValue = getFromCache(method, key, cacheAnnotation.persist());
        if (cachedValue != null) {
            return cachedValue;
        }

        Object result = method.invoke(target, args);
        putToCache(method, key, result, cacheAnnotation.persist());
        return result;
    }

    private Object getFromCache(Method method, List<Object> key, boolean persist) {
        Map<List<Object>, Object> methodCache = memoryCache.get(method);
        if (methodCache != null && methodCache.containsKey(key)) {
            return methodCache.get(key);
        }

        if (persist) {
            Path cacheFile = getCacheFilePath(method, key);
            if (Files.exists(cacheFile)) {
                try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(cacheFile))) {
                    return ois.readObject();
                } catch (IOException | ClassNotFoundException e) {

                }
            }
        }

        return null;
    }

    private void putToCache(Method method, List<Object> key, Object value, boolean persist) {
        memoryCache.computeIfAbsent(method, k -> new HashMap<>())
                .put(key, value);

        if (persist) {
            Path cacheFile = getCacheFilePath(method, key);
            try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(cacheFile))) {
                oos.writeObject(value);
            } catch (IOException e) {
                System.err.println("Failed to persist cache for " + method.getName() + ": " + e.getMessage());
            }
        }
    }

    private Path getCacheFilePath(Method method, List<Object> key) {
        String methodName = method.getName();
        String argsHash = Integer.toHexString(key.hashCode());
        return tempDir.resolve(methodName + "_" + argsHash + ".cache");
    }
}
