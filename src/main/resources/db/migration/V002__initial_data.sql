insert into users
values (nextval('users_user_id_seq'),now(), 'admin@admin.com', true, '$2a$12$aJZigio5thHN4luSrVcQ3enLErI74CDaHqHANgoW69B8ru5uOUCrW');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'USER');

insert into authorities
values (nextval('authorities_authority_id_seq'), 'ADMIN');

insert into users_authorities
values (1, 2);

insert into users_authorities
values (1, 1);