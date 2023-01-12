create table url_table
(
    id           bigserial primary key,
    url          varchar(255),
    url_short    varchar(255),
    url_generate varchar(255)
);

insert into url_table (url, url_short, url_generate)
values
(
 'https://ya.ru/',
 'demo',
 'demo'
);
