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



