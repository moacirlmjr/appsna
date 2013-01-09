select id_usuario, nome, '1' as 'Tipo'  
from usuario 
where id_usuario IN(
SELECT identificacao 
FROM atr_twitter_saida
)
UNION ALL
select id_usuario, nome, '0' as 'Tipo'  
from usuario 
where id_usuario NOT IN(
SELECT identificacao 
FROM atr_twitter_saida
);






select id_usuario, nome, '1' as 'Tipo'  
from usuario 
where id_usuario IN(
SELECT identificacao 
FROM atr_twitter_saida 
)
and id_usuario = 112871179
UNION ALL
select id_usuario, nome, '0' as 'Tipo'  
from usuario 
where id_usuario NOT IN(
SELECT identificacao 
FROM atr_twitter_saida
)
and id_usuario = 112871179;select id_usuario, nome, '1' as 'Tipo'  
from usuario 
where id_usuario IN(
SELECT identificacao 
FROM atr_twitter_saida 
)
and id_usuario = 112871179
UNION ALL
select id_usuario, nome, '0' as 'Tipo'  
from usuario 
where id_usuario NOT IN(
SELECT identificacao 
FROM atr_twitter_saida
)
and id_usuario = 112871179;




select u.id_usuario, u.nome, s.texto, s.data_criacao from
usuario u, status s
where u.id_usuario = s.id_usuario
and s.texto like '% credito %'

seleciona o usuario de origem, nome, o texto do status e a 
data de criacao do status que contenham a palavra credito
no corpo dos 144 caracteres



select u.id_usuario, u.nome, count(*) as 'total de mencoes' from
usuario u, status s
where u.id_usuario = s.id_usuario
and s.texto like '% credito %'
group by u.id_usuario

seleciona o usuario de origem, nome, o total de ocorrencias
dos status que contenham a palavra credito
no corpo dos 144 caracteres


select u.id_usuario, u.nome, s.texto, s.data_criacao, m.id_user_mentionade from
usuario u, status s, usermention m
where u.id_usuario = s.id_usuario
and s.id_status=m.id_status
and s.texto like '% credito %'

seleciona o usuario de origem, nome, o texto do status,
data de criacao do status e o destino da msg que contenham a 
palavra credito no corpo dos 144 caracteres
select u.id_usuario, u.nome, s.texto, s.data_criacao, m.id_user_mentionade from
usuario u, status s, usermention m
where u.id_usuario = s.id_usuario
and s.id_status=m.id_status
and s.texto like '% credito %' order by year(s.data_criacao), month(s.data_criacao)

seleciona o usuario de origem, nome, o texto do status,
data de criacao do status e o destino da msg que contenham a 
palavra credito no corpo dos 144 caracteres ordenando-os 
pelo ano e mes em ordem crescente.


select count(*) as 'total ocorrencias' from
usuario u, status s, usermention m
where u.id_usuario = s.id_usuario
and s.id_status = m.id_status
and s.texto like '% credito %'


seleciona o total de ocorrencias de msg que contenham a 
palavra credito no corpo dos 144 caracteres



