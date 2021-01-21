package com.faceit.example.internetlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/*
service mysql status
sudo service mysql stop
sudo service mysql start

sudo docker-compose build
sudo docker-compose up
sudo docker-compose ps
sudo docker-compose stop

sudo docker rm
sudo docker ps -a
sudo docker image ls
sudo docker images -a
https://www.digitalocean.com/community/tutorials/how-to-remove-docker-images-containers-and-volumes-ru

Стек технологий:
1. Java 8 или выше
2. Сборка проекта: Maven
3. Backend: Spring Boot(Web, Data JPA, Security)
4. БД: MySQL
5. Фронтенд: Boostrap 3 (CSS)+HTML + JS(любой фреймворк: jQuery|Angular|VueJS|React или другой)

Описание:
Нужно сделать простое веб приложение(сайт) интернет библиотека.
У каждого действия с книгой есть дата действия, статус, читатель, книга, состояние книги.
Нужно сделать простой функционал: после авторизации
(регистрацию пользователя можно не делать, просто руками добавить несколько пользователей в БД)
у пользователя есть возможность просмотреть список взятых книг, взять новую,
отредактировать или удалить существующие. Запросы по созданию/редактированию/удалению нужно сделать через Ajax.
Все веб сервисы(создание задачи/редактирование/удаление) должны работать через JSON.
Для верстки можно использовать Boostrap CSS. Начини пожалуйста с модуля авторизации,
чтобы была возможность у пользователя войти/выйти. Потом можно перейти к реализации функционала.
*/
@EnableScheduling
@SpringBootApplication
public class InternetLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternetLibraryApplication.class, args);
    }
}
