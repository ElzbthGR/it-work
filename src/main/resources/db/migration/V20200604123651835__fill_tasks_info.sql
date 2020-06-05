insert into tasks (id, role_type, title, type, description)
values (nextval('tasks_seq'), 'PROGRAMMER', 'Найти ошибку в коде', 'CODE', null);

insert into tasks (id, role_type, title, type, description)
values (nextval('tasks_seq'), 'PROGRAMMER', 'Цикл', 'CODE', 'Необходимо вывести с помощью цикла числа от 1 до 5 включительно без разделяющих символов.
');

insert into tasks (id, role_type, title, type, description)
values (nextval('tasks_seq'), 'PROGRAMMER', 'Hello World!', 'CODE', null);

insert into tasks (id, role_type, title, type, description)
values (nextval('tasks_seq'), 'TESTER', 'Составить диаграмму вариантов использования', 'UML', 'Необходимо составить диаграмму использования для . С основами составления диаграмм этого типа можно ознакомиться здесь: https://www.intuit.ru/studies/courses/1007/229/lecture/5954');

insert into tasks (id, role_type, title, type, description)
values (nextval('tasks_seq'), 'TESTER', 'Составить диаграмму классов', 'UML', null);

insert into tasks (id, role_type, title, type, description)
values (nextval('tasks_seq'), 'TESTER', 'Проверить API', 'TEST', null);

insert into tasks (id, role_type, title, type, description)
values (nextval('tasks_seq'), 'PROGRAMMER', 'Работа с командами GIT', 'SEQUENCE', 'Для работы с системой контроля версий GIT используются чаще всего используются 3 команды. Необходимо команды ниже расставить в верном порядке, при условии, что в удаленном репозитории есть изменения и нужно добавить свои. О командах можно прочитать здесь: https://git-scm.com/book/en/v2');
