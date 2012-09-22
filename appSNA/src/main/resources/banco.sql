create database appsna;

use appsna;

create table elemento(
id long not null auto_increment,
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
id_source long not null,
id_target long not null,
primary key(id_source, id_target),
constraint foreign key(id_source) references elemento(id),
constraint foreign key(id_target) references elemento(id)
);
