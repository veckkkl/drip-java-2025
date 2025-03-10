package homework3.task5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ContactSorter {
    public static List<Contact> parseContacts(String[] names, String sortOrder) {
        if (names == null || names.length == 0) {
            return new ArrayList<>(); // Возвращаем пустой список, если входной массив пуст или равен null
        }

        // Преобразуем массив строк в список объектов Contact
        List<Contact> contacts = new ArrayList<>();
        for (String name : names) {
            String[] parts = name.split(" ", 2); // Разделяем строку на имя и фамилию
            String firstName = parts[0];
            String lastName = parts.length > 1 ? parts[1] : "";
            contacts.add(new Contact(firstName, lastName));
        }

        // Сортируем контакты по фамилии (или имени, если фамилия отсутствует)
        Comparator<Contact> comparator = Comparator.comparing(
                contact -> contact.getLastName().isEmpty() ? contact.getFirstName() : contact.getLastName()
        );

        if ("DESC".equalsIgnoreCase(sortOrder)) {
            comparator = comparator.reversed(); // Для сортировки по убыванию
        }

        Collections.sort(contacts, comparator);

        return contacts;
    }
}