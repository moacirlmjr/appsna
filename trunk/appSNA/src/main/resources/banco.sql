create database appsnaelection;

use appsnaelection;

create table monitorado(
id bigint not null auto_increment,
screen_name varchar(30) not null,
twitter_id bigint not null,
primary key(id)
);


create table termo(
id bigint not null auto_increment,
monitorado_id bigint not null,
conteudo varchar(30),
constraint primary key(id, monitorado_id),
constraint foreign key(monitorado_id) references monitorado(id)
);

create table resultado(
id bigint not null auto_increment,
screen_name varchar(30) not null, 
termo_id bigint not null, 
data timestamp not null,
latitude bigint,
longitude bigint,
monitorado_id bigint ,
constraint primary key(id),
constraint foreign key(termo_id) references termo(id),
constraint foreign key(monitorado_id) references monitorado(id)
);