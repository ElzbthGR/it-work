
    create table answers (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        correct boolean,
        description varchar(255),
        explanation varchar(255),
        task_id int8,
        primary key (id)
    );

    create table files (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        deleted boolean,
        content_type varchar(255),
        creator_id int8,
        extension varchar(255),
        original_name varchar(255),
        path varchar(255),
        used boolean not null,
        uuid varchar(255),
        primary key (id)
    );

    create table sequence_items (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        description varchar(255),
        item_order int8,
        task_id int8,
        primary key (id)
    );

    create table task_media_file (
       task_id int8 not null,
        media_file_id int8 not null,
        primary key (task_id, media_file_id)
    );

    create table task_response_media_file (
       task_response_id int8 not null,
        media_file_id int8 not null,
        primary key (task_response_id, media_file_id)
    );

    create table task_responses (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        deleted boolean,
        text varchar(255),
        primary key (id)
    );

    create table tasks (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        deleted boolean,
        description varchar(255),
        role_type varchar(255),
        title varchar(255),
        type varchar(255),
        primary key (id)
    );

    create table users (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        deleted boolean,
        email varchar(255),
        firs_name varchar(255),
        last_name varchar(255),
        password_hash varchar(255),
        role varchar(255),
        primary key (id)
    );

    create table users_tasks (
       user_id int8 not null,
        task_id int8 not null,
        primary key (user_id, task_id)
    );

    create table verification_tasks (
       file_id int8 not null,
        task_id int8 not null,
        user_id int8 not null,
        task_response_id int8,
        primary key (file_id, task_id, user_id)
    );

    alter table if exists task_media_file 
       drop constraint if exists UK_e5myu5kw6wer5tm56fiminh95;

    alter table if exists task_media_file 
       add constraint UK_e5myu5kw6wer5tm56fiminh95 unique (media_file_id);
create sequence answers_seq start 1 increment 1;
create sequence files_seq start 1 increment 1;
create sequence sequence_items_seq start 1 increment 1;
create sequence task_responses_seq start 1 increment 1;
create sequence tasks_seq start 1 increment 1;
create sequence users_seq start 1 increment 1;

    alter table if exists answers 
       add constraint FKn5epgjnilteookxcqy38xfcx3 
       foreign key (task_id) 
       references tasks;

    alter table if exists sequence_items 
       add constraint FKje0bidn3o43vcyd4o17k64xg6 
       foreign key (task_id) 
       references tasks;

    alter table if exists task_media_file 
       add constraint FK6t9iiraidvbochl1eut6ri1ol 
       foreign key (media_file_id) 
       references files;

    alter table if exists task_media_file 
       add constraint FKpkqbmjef69bvgtk2fsem52onk 
       foreign key (task_id) 
       references tasks;

    alter table if exists task_response_media_file 
       add constraint FKltqgpniks0fwdw6useo0luw2k 
       foreign key (media_file_id) 
       references files;

    alter table if exists users_tasks 
       add constraint FK7d707lylp7o2u878m026iqhn 
       foreign key (task_id) 
       references users;

    alter table if exists users_tasks 
       add constraint FK9x903dl25yt363t7ex2p4bj04 
       foreign key (user_id) 
       references tasks;

    alter table if exists verification_tasks 
       add constraint FKsx5quxd82f43hxyxd1pkr5fr7 
       foreign key (file_id) 
       references files;

    alter table if exists verification_tasks 
       add constraint FKrqmc6ahl6t21wn6ou7o6ruwih 
       foreign key (task_id) 
       references tasks;

    alter table if exists verification_tasks 
       add constraint FKasvconh81vr6degmr0142g4g7 
       foreign key (task_response_id) 
       references task_responses;

    alter table if exists verification_tasks 
       add constraint FKe0ys6ho4ywvnox4r37cp3iepg 
       foreign key (user_id) 
       references users;
