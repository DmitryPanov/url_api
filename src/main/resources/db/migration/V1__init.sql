CREATE TABLE IF NOT EXISTS url_table
(
    id           BIGSERIAL PRIMARY KEY,
    url          VARCHAR(255),
    url_short    VARCHAR(255),
    url_generate VARCHAR(255),
    created_at   timestamp default current_timestamp,
    updated_at   timestamp default current_timestamp
);

insert into url_table (url, url_short, url_generate)
values ('https://ya.ru/',
        'demo',
        'demo');
