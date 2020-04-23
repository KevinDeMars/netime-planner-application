create table schedules
(
    schedule_id serial not null
        constraint schedules_pk
            primary key
);

alter table schedules
    owner to postgres;

create table users
(
    user_id       serial  not null
        constraint users_pk
            primary key,
    username      text    not null,
    email         text    not null,
    password_hash text    not null,
    schedule_id   integer not null
        constraint users_schedules_id_fk
            references schedules
);

alter table users
    owner to postgres;

create unique index users_schedule_id_uindex
    on users (schedule_id);

create unique index users_name_uindex
    on users (username);

create table groups
(
    group_id    serial  not null
        constraint groups_pk
            primary key,
    schedule_id integer not null
        constraint groups_schedules_id_fk
            references schedules,
    owner_id    integer not null
        constraint groups_users_id_fk
            references users
);

alter table groups
    owner to postgres;

create unique index groups_schedule_id_uindex
    on groups (schedule_id);

create table activities
(
    activity_id   serial  not null
        constraint activities_pk
            primary key,
    time_start    time    not null,
    time_end      time    not null,
    days_of_week  integer not null,
    start_date    date    not null,
    end_date      date,
    week_interval integer,
    activity_name text    not null,
    description   text,
    location      text
);

alter table activities
    owner to postgres;

create table deadlines
(
    deadline_id          serial    not null
        constraint deadlines_pk
            primary key,
    due_timestamp        timestamp not null,
    start_timestamp      timestamp,
    deadline_name        text      not null,
    description          text,
    location             text,
    category_activity_id integer
        constraint deadlines_activities_id_fk
            references activities
);

alter table deadlines
    owner to postgres;

create unique index deadlines_id_uindex
    on deadlines (deadline_id);

create table schedules_deadlines
(
    schedule_id integer not null
        constraint schedule_deadlines_schedules_id_fk
            references schedules
            on delete cascade,
    deadline_id integer not null
        constraint schedule_deadlines_deadlines_id_fk
            references deadlines
            on delete cascade
);

alter table schedules_deadlines
    owner to postgres;

create table schedules_activities
(
    schedule_id integer not null
        constraint schedules_activities_schedules_schedule_id_fk
            references schedules
            on delete cascade,
    activity_id integer not null
        constraint schedules_activities_activities_activity_id_fk
            references activities
            on delete cascade
);

alter table schedules_activities
    owner to postgres;

create table schedules_worktimes
(
    schedule_id integer not null
        constraint schedules_worktimes_schedules_schedule_id_fk
            references schedules
            on delete cascade,
    activity_id integer not null
        constraint schedules_worktimes_activities_activity_id_fk
            references activities
            on delete cascade
);

alter table schedules_worktimes
    owner to postgres;

