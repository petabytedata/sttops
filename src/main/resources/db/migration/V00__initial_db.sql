-- CREATE SCHEMA IF NOT EXISTS sttops;

create table if not exists users
(
    id 			serial not null,
    full_name   VARCHAR(255),
    username    VARCHAR(255),
    email       VARCHAR(255),
    password    VARCHAR(255),
    active		boolean default true
);

alter table users add constraint pk_users primary key (id);

create table if not exists file_meta_infos
(
    id 			serial not null,
    file_name   VARCHAR(255),
    file_path    VARCHAR(255),
    user_id      int,
    password    VARCHAR(255),
    active		boolean default true
);

alter table file_meta_infos add constraint pk_file_meta_infos primary key (id);

alter table file_meta_infos add constraint fk_file_meta_infos_user_id foreign key (user_id) references users (id);

create table if not exists stt_results
(
    id 			serial not null,
    file_id     int,
    stt_result    text
);

alter table stt_results add constraint pk_stt_results primary key (id);

alter table stt_results add constraint fk_stt_results_file_id foreign key (file_id) references file_meta_infos (id);