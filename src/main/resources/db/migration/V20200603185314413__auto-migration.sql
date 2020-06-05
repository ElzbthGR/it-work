
    alter table if exists verification_tasks 
       add column code uuid;

    alter table if exists verification_tasks 
       add column send_date_time timestamp;
