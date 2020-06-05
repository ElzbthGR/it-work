
    create table users_tasks (
       task_id int8 not null,
        user_id int8 not null,
        primary key (task_id, user_id)
    );

    alter table if exists users_tasks 
       add constraint FK7todmyl52eiddpi6hc2nfgvbs 
       foreign key (task_id) 
       references tasks;

    alter table if exists users_tasks 
       add constraint FK6frwjo48hefay0rg31q970r8t 
       foreign key (user_id) 
       references users;
