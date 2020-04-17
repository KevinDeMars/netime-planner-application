create table users
(
    id            serial       not null
        constraint users_pk
            primary key,
    name          varchar(30)  not null,
    email         varchar(100) not null,
    password_hash text         not null
);

alter table users
    owner to postgres;

create unique index users_name_uindex
    on users (name);

create table groups
(
    id serial not null
        constraint groups_pk
            primary key
);

alter table groups
    owner to postgres;

create table schedules
(
    id             serial not null
        constraint schedules_pk
            primary key,
    user_owner_id  integer
        constraint schedules_users_id_fk
            references users,
    group_owner_id integer
        constraint schedules_groups_id_fk
            references groups,
    constraint schedules_check
        check ((user_owner_id IS NOT NULL) OR (group_owner_id IS NOT NULL))
);

alter table schedules
    owner to postgres;

create unique index schedules_user_owner_id_uindex
    on schedules (user_owner_id);

create unique index schedules_group_owner_id_uindex
    on schedules (group_owner_id);

create table activities
(
    id            serial  not null
        constraint activities_pk
            primary key,
    time_start    time    not null,
    time_end      time    not null,
    days_of_week  integer not null,
    start_date    date    not null,
    end_date      date,
    week_interval integer
);

alter table activities
    owner to postgres;