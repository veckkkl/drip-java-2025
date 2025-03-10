package homework3.task9;

import java.util.*;
import java.util.stream.Collectors;

public class AnimalTasks {

    // Задача 1
    public static List<Animal> sortByHeight(List<Animal> animals) {
        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::height))
                .toList();
    }

    // Задача 2
    public static List<Animal> sortByWeightAndSelectTopK(List<Animal> animals, int k) {
        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::weight).reversed())
                .limit(k)
                .toList();
    }

    // Задача 3
    public static Map<Animal.Type, Integer> countAnimalsByType(List<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(a -> 1)));
    }

    // Задача 4
    public static Animal findAnimalWithLongestName(List<Animal> animals) {
        return animals.stream()
                .max(Comparator.comparingInt(a -> a.name().length()))
                .orElseThrow(() -> new IllegalArgumentException("Список животных пустой"));
    }

    // Задача 5
    public static Animal.Sex findMajoritySex(List<Animal> animals) {
        Map<Animal.Sex, Long> sexCount = animals.stream()
                .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));

        return sexCount.get(Animal.Sex.M) > sexCount.get(Animal.Sex.F) ? Animal.Sex.M : Animal.Sex.F;
    }

    // Задача 6
    public static Map<Animal.Type, Animal> findHeaviestAnimalPerType(List<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(
                        Animal::type,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Animal::weight)),
                                opt -> opt.orElseThrow()
                        )
                ));
    }

    // Задача 7
    public static Animal findKthOldestAnimal(List<Animal> animals, int k) {
        return animals.stream()
                .sorted(Comparator.comparingInt(Animal::age).reversed())
                .skip(k - 1)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Недостаточно животных"));
    }

    // Задача 8
    public static Optional<Animal> findHeaviestAnimalBelowHeight(List<Animal> animals, int k) {
        return animals.stream()
                .filter(a -> a.height() < k)
                .max(Comparator.comparingInt(Animal::weight));
    }
    // Задача 9
    public static Integer sumOfPaws(List<Animal> animals) {
        return animals.stream()
                .mapToInt(Animal::paws)
                .sum();
    }

    // Задача 10
    public static List<Animal> findAnimalsWithAgeNotMatchingPaws(List<Animal> animals) {
        return animals.stream()
                .filter(a -> a.age() != a.paws())
                .toList();
    }

    // Задача 11
    public static List<Animal> findBitingAnimalsAbove100cm(List<Animal> animals) {
        return animals.stream()
                .filter(a -> a.bites() && a.height() > 100)
                .toList();
    }
    // Задача 12
    public static Integer countAnimalsWithWeightGreaterThanHeight(List<Animal> animals) {
        return (int) animals.stream()
                .filter(a -> a.weight() > a.height())
                .count();
    }

    // Задача 13
    public static List<Animal> findAnimalsWithMultiWordNames(List<Animal> animals) {
        return animals.stream()
                .filter(a -> a.name().split(" ").length > 2)
                .toList();
    }

    // Задача 14
    public static Boolean hasDogTallerThanK(List<Animal> animals, int k) {
        return animals.stream()
                .anyMatch(a -> a.type() == Animal.Type.DOG && a.height() > k);
    }

    // Задача 15
    public static Map<Animal.Type, Integer> sumWeightByTypeInAgeRange(List<Animal> animals, int k, int l) {
        return animals.stream()
                .filter(a -> a.age() >= k && a.age() <= l)
                .collect(Collectors.groupingBy(
                        Animal::type,
                        Collectors.summingInt(Animal::weight)
                ));
    }

    // Задача 16
    public static List<Animal> sortByTypeSexName(List<Animal> animals) {
        return animals.stream()
                .sorted(Comparator
                        .comparing(Animal::type)
                        .thenComparing(Animal::sex)
                        .thenComparing(Animal::name))
                .toList();
    }

    // Задача 17
    public static Boolean doSpidersBiteMoreThanDogs(List<Animal> animals) {
        long spiderBites = animals.stream()
                .filter(a -> a.type() == Animal.Type.SPIDER && a.bites())
                .count();

        long dogBites = animals.stream()
                .filter(a -> a.type() == Animal.Type.DOG && a.bites())
                .count();

        return spiderBites > dogBites;
    }

    // Задача 18
    public static Animal findHeaviestFishInMultipleLists(List<List<Animal>> animalLists) {
        return animalLists.stream()
                .flatMap(List::stream)
                .filter(a -> a.type() == Animal.Type.FISH)
                .max(Comparator.comparingInt(Animal::weight))
                .orElseThrow(() -> new IllegalArgumentException("Рыб нет"));
    }

    // Задача 19
    public enum ValidationError {
        INVALID_NAME, INVALID_AGE, INVALID_HEIGHT, INVALID_WEIGHT
    }

    public static Map<String, Set<ValidationError>> findAnimalsWithErrors(List<Animal> animals) {
        return animals.stream()
                .filter(a -> hasErrors(a))
                .collect(Collectors.toMap(
                        Animal::name,
                        a -> getErrors(a)
                ));
    }

    private static boolean hasErrors(Animal animal) {
        return !getErrors(animal).isEmpty();
    }

    private static Set<ValidationError> getErrors(Animal animal) {
        Set<ValidationError> errors = new HashSet<>();
        if (animal.name() == null || animal.name().isEmpty()) errors.add(ValidationError.INVALID_NAME);
        if (animal.age() <= 0) errors.add(ValidationError.INVALID_AGE);
        if (animal.height() <= 0) errors.add(ValidationError.INVALID_HEIGHT);
        if (animal.weight() <= 0) errors.add(ValidationError.INVALID_WEIGHT);
        return errors;
    }

    // Задача 20
    public static Map<String, String> formatErrors(List<Animal> animals) {
        return findAnimalsWithErrors(animals).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(ValidationError::name)
                                .collect(Collectors.joining(", "))
                ));
    }
}
