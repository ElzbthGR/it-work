
    create table chat_messages (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        right_message boolean,
        server_message varchar(255),
        start boolean,
        task_id int8,
        user_message varchar(255),
        primary key (id)
    );
create sequence chat_messages_seq start 1 increment 1;

    alter table if exists chat_messages 
       add constraint FKe3tn55xm4h4uog1wgawrx873y 
       foreign key (task_id) 
       references tasks;
