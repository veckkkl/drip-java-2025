package homework5.task3;

import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class ReadWriteLockPersonDatabase implements PersonDatabase {
    private final Map<Integer, Person> personsById = new HashMap<>();

    private final Map<String, Set<Integer>> indexByName = new HashMap<>();
    private final Map<String, Set<Integer>> indexByAddress = new HashMap<>();
    private final Map<String, Set<Integer>> indexByPhone = new HashMap<>();

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    @Override
    public void add(Person person) {
        rwLock.writeLock().lock();
        try {
            if (personsById.containsKey(person.id())) {
                throw new IllegalArgumentException("Человек с ID " + person.id() + " уже существует");
            }
            personsById.put(person.id(), person);
            updateAllIndices(person);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        rwLock.writeLock().lock();
        try {
            Person person = personsById.remove(id);
            if (person != null) {
                removeFromAllIndices(person);
            }
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        return findByIndex(indexByName, name);
    }

    @Override
    public List<Person> findByAddress(String address) {
        return findByIndex(indexByAddress, address);
    }

    @Override
    public List<Person> findByPhone(String phone) {
        return findByIndex(indexByPhone, phone);
    }

    private List<Person> findByIndex(Map<String, Set<Integer>> index, String key) {
        rwLock.readLock().lock();
        try {
            Set<Integer> ids = index.getOrDefault(key, Collections.emptySet());
            return ids.stream()
                    .map(personsById::get)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } finally {
            rwLock.readLock().unlock();
        }
    }

    private void updateAllIndices(Person person) {
        updateIndex(indexByName, person.name(), person.id());
        updateIndex(indexByAddress, person.address(), person.id());
        updateIndex(indexByPhone, person.phoneNumber(), person.id());
    }

    private void removeFromAllIndices(Person person) {
        removeFromIndex(indexByName, person.name(), person.id());
        removeFromIndex(indexByAddress, person.address(), person.id());
        removeFromIndex(indexByPhone, person.phoneNumber(), person.id());
    }

    private void updateIndex(Map<String, Set<Integer>> index, String key, int id) {
        index.computeIfAbsent(key, k -> new HashSet<>()).add(id);
    }

    private void removeFromIndex(Map<String, Set<Integer>> index, String key, int id) {
        index.computeIfPresent(key, (k, v) -> {
            v.remove(id);
            return v.isEmpty() ? null : v;
        });
    }
}
