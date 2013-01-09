--separou os usuarios em quem faz/não-faz parte dos 478 
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

--Retorna os dados de um elemento acrescido da informacao de se ele pertence/não-pertence aos 478
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
and id_usuario = 112871179;


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
and id_usuario = 112871179;


-- Retorna os dados de uma pessoa que possue negatividade
SELECT identificacao as 'Id Twitter' , ndoc as 'Nr Cpf', COUNT(ndoc) AS 'Qtde Negativas'
FROM j246_analitivo_negativas an, atr_twitter_saida ts where 
an.ndoc = ts.cpf_atbr and ts.identificacao='26671798'
GROUP BY ndoc


--devolver a soma de negatividade da vizinhanca
select sum(qtde_negativas) from
(
SELECT COUNT(an.ndoc) as qtde_negativas
FROM j246_analitivo_negativas an, atr_twitter_saida ts where 
an.ndoc = ts.cpf_atbr and ts.identificacao IN
(select id_target from relacionamento where id_source = '105868888')
GROUP BY ndoc
) as u;


--devolver a quantidade de pessoas que possuem negatividade na vizinhanca
select count(qtde_negativas) from
(
SELECT COUNT(an.ndoc) as qtde_negativas
FROM j246_analitivo_negativas an, atr_twitter_saida ts where 
an.ndoc = ts.cpf_atbr and ts.identificacao IN
(select id_target from relacionamento where id_source = '105868888')
GROUP BY ndoc
) as u;


--grau de inadimplencia
SELECT d.qtde/t.total from(
SELECT COUNT(ndoc) AS 'qtde'
FROM j246_analitivo_negativas an, atr_twitter_saida ts where 
an.ndoc = ts.cpf_atbr and ts.identificacao='101763077'
GROUP BY ndoc
) as d
,
(
select sum(qtde_negativas) as total from
(
SELECT COUNT(an.ndoc) as qtde_negativas
FROM j246_analitivo_negativas an, atr_twitter_saida ts where 
an.ndoc = ts.cpf_atbr and ts.identificacao IN
(select id_target from relacionamento where id_source = '101763077')
GROUP BY ndoc
) as u
) as t;


--consulta full-time
SELECT d.qtde/t.total AS grau_inadimplencia, t.total as quantidade_negativas_viz, d.qtde as negativas 
FROM(
SELECT COUNT(ndoc) AS 'qtde'
FROM j246_analitivo_negativas an, atr_twitter_saida ts 
WHERE an.ndoc = ts.cpf_atbr and ts.identificacao='105868888'
GROUP BY ndoc) as d
,
(SELECT SUM(qtde_negativas) as total 
FROM(
SELECT COUNT(an.ndoc) as qtde_negativas
FROM j246_analitivo_negativas an, atr_twitter_saida ts where 
an.ndoc = ts.cpf_atbr and ts.identificacao IN
(SELECT id_target 
FROM relacionamento 
WHERE id_source = '105868888')
GROUP BY ndoc) as u
) as t;











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



