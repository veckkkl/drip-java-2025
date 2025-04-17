package homework5.task3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SynchronizedPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> personsById = new ConcurrentHashMap<>();

    private final Map<String, Set<Integer>> indexByName = new ConcurrentHashMap<>();
    private final Map<String, Set<Integer>> indexByAddress = new ConcurrentHashMap<>();
    private final Map<String, Set<Integer>> indexByPhone = new ConcurrentHashMap<>();

    private final Object lock = new Object();

    @Override
    public void add(Person person) {
        synchronized (lock) {
            if (personsById.containsKey(person.id())) {
                throw new IllegalArgumentException("Человек с ID " + person.id() + " уже существет");
            }

            personsById.put(person.id(), person);

            updateIndex(indexByName, person.name(), person.id());
            updateIndex(indexByAddress, person.address(), person.id());
            updateIndex(indexByPhone, person.phoneNumber(), person.id());
        }
    }

    @Override
    public void delete(int id) {
        synchronized (lock) {
            Person person = personsById.remove(id);
            if (person != null) {
                removeFromIndex(indexByName, person.name(), id);
                removeFromIndex(indexByAddress, person.address(), id);
                removeFromIndex(indexByPhone, person.phoneNumber(), id);
            }
        }
    }

    @Override
    public List<Person> findByName(String name) {
        return findFromIndex(indexByName, name);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return findFromIndex(indexByAddress, address);
    }

    @Override
    public List<Person> findByPhone(String phone) {
        return findFromIndex(indexByPhone, phone);
    }

    private List<Person> findFromIndex(Map<String, Set<Integer>> index, String key) {
        Set<Integer> ids;
        synchronized (lock) {
            ids = index.getOrDefault(key, Collections.emptySet());
            ids = new HashSet<>(ids);
        }

        return ids.stream()
                .map(personsById::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void updateIndex(Map<String, Set<Integer>> index, String key, int id) {
        index.compute(key, (k, v) -> {
            if (v == null) {
                v = new HashSet<>();
            }
            v.add(id);
            return v;
        });
    }

    private void removeFromIndex(Map<String, Set<Integer>> index, String key, int id) {
        index.computeIfPresent(key, (k, v) -> {
            v.remove(id);
            return v.isEmpty() ? null : v;
        });
    }
}
