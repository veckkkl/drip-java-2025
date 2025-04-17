package homework5.task3;

import java.util.List;

interface PersonDatabase {
    void add(Person person);
    void delete(int id);

    List<Person> findByName(String name);
    List<Person> findByAddress(String address);
    List<Person> findByPhone(String phone);
}

