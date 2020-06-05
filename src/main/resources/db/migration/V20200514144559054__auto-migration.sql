
    alter table if exists task_responses 
       add column passed boolean;

    alter table if exists task_response_media_file 
       drop constraint if exists UK_cirdagvcavtu4deroukifxvjj;

    alter table if exists task_response_media_file 
       add constraint UK_cirdagvcavtu4deroukifxvjj unique (media_file_id);

    alter table if exists task_response_media_file 
       add constraint FKgqjpufe64246tiadjwks2hj0f 
       foreign key (task_response_id) 
       references task_responses;
