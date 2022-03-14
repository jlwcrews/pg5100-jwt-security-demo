create table users
(
    user_id       serial primary key,
    user_created timestamp not null,
    user_email varchar(255) not null,
    user_enabled  boolean not null,
    user_password varchar(255) not null
);

insert into users
values (1, now(), 'jim@bob.com', true, '$2a$12$eld5F51gMVe2wNOhJoIU9uevlkRd.SgoxVtE7riu6OT5c7Mb5YD/e');

insert into users
values (2, now(), 'joe@bob.com', true, '$2a$12$eld5F51gMVe2wNOhJoIU9uevlkRd.SgoxVtE7riu6OT5c7Mb5YD/e');

create table authorities(
    authority_id serial primary key,
    authority_name varchar(255)
);

insert into authorities
values (1, 'USER');

insert into authorities
values (2, 'ADMIN');

create table users_authorities(
    user_entity_user_id bigint,
    authorities_authority_id bigint
);

insert into users_authorities
values (1, 1);

insert into users_authorities
values (2, 2);



