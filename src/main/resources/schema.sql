drop table if exists bookshelf_user;
create table bookshelf_user
(
    id       bigint primary key,
    username varchar,
    password varchar,
    role     varchar
);
