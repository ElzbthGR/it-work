insert into users (id, created_date_time, last_modified_date_time, deleted, email, password_hash, role)
values (nextval('users_seq'), current_timestamp, current_timestamp, false, 'admin@mail.ru',
        '$2a$10$CmUEmAi9pyU9WPnBaTVfFuKG57w5bWTBIwzRucdfAX1cc4933Hsaa', 'ADMIN');