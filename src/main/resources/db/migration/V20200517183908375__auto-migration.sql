
    create table codes (
       id int8 not null,
        created_date_time timestamp,
        last_modified_date_time timestamp,
        accepted boolean,
        compiler_id int8,
        output varchar(255),
        task_id int8,
        template varchar(255),
        primary key (id)
    );

    create table compiler_templates (
       id int8 not null,
        base_template varchar(255),
        compiler_id int8,
        primary key (id)
    );

    alter table if exists compiler_templates 
       drop constraint if exists UK_rqc9gofc86haqt0pnnigdraqr;

    alter table if exists compiler_templates 
       add constraint UK_rqc9gofc86haqt0pnnigdraqr unique (compiler_id);
create sequence codes_seq start 1 increment 1;
create sequence compiler_templates_seq start 1 increment 1;

    alter table if exists codes 
       add constraint FKg4cki4qo14wwvb7e7ww9nkx59 
       foreign key (task_id) 
       references tasks;
