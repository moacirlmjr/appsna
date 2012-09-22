create database appsna;

use appsna;

create table elemento(
id bigint not null auto_increment,
nome varchar(30) not null,
screen_name varchar(30) not null,
biografia text not null,
localizacao varchar(50),
totalFollowing int not null,
totalTweets int not null, 
URL text, 
timeZone varchar(30),
linguagem char(2),
dataDeCricao timestamp,
URLImagem text,
constraint primary key(id)
);


create table relacionamento(
id_source bigint not null,
id_target bigint not null,
primary key(id_source, id_target),
constraint foreign key(id_source) references elemento(id),
constraint foreign key(id_target) references elemento(id)
);


create table status(
id bigint not null auto_increment,
id_elemento bigint not null,
dataDeCriacao timestamp,
texto text not null,
latitude bigint,
longitude bigint,
totalRetweet int,
isRetweeted bit,
primary key(id, id_elemento),
constraint foreign key(id_elemento) references elemento(id)
);

create table usermention(
id bigint not null auto_increment,
id_status bigint not null,
id_elemento bigint not null,
mention text,
primary key(id, id_status, id_elemento),
foreign key(id_status) references tweet(id),
foreign key(id_elemento) references tweet(id_elemento)
);

create table urlmention(
id bigint not null auto_increment,
id_status bigint not null,
id_elemento bigint not null,
url text,
primary key(id, id_status, id_elemento),
foreign key(id_status) references tweet(id),
foreign key(id_elemento) references tweet(id_elemento)
);


create table hashtagmention(
id bigint not null auto_increment,
id_status bigint not null,
id_elemento bigint not null,
hashtag text,
primary key(id, id_status, id_elemento),
foreign key(id_status) references tweet(id),
foreign key(id_elemento) references tweet(id_elemento)
);

