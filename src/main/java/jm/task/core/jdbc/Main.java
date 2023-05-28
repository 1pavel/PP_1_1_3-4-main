package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService us = new UserServiceImpl();
        // Создание таблицы User(ов)
        us.createUsersTable();

        // Добавление 4 User(ов) в таблицу с данными на свой выбор.
        us.saveUser("Bill", "Gates", (byte) 65);
        us.saveUser("Steve", "Jobs", (byte) 55);
        us.saveUser("Some", "User", (byte) 20);
        us.saveUser("K", "A", (byte) 29);

        //Получение всех User из базы и вывод в консоль
        us.getAllUsers().stream().forEach(n-> System.out.println(n.toString()) );

        // Очистка таблицы User(ов)
        us.cleanUsersTable();

        // Удаление таблицы
        us.dropUsersTable();
    }

}


