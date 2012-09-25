-- consulta Nr 1 - Total tweet / pessoas distintas / media
select m.screen_name, count(r.screen_name) as 'total tweet', 
count(distinct(r.screen_name)) as 'total pessoas distintas', 
count(r.screen_name) / count(distinct(r.screen_name)) as 'média tweet por pessoa/candidato' 
from resultado r, monitorado m where r.monitorado_id = m.id 
group by monitorado_id

--Consulta Nr 2 - Total tweet por dia / total de pessoas por dia
select DATE_FORMAT(data, '%d/%m/%Y') as 'Data',
count(distinct(screen_name)) as 'total de tweet' 
from resultado where monitorado_id=11 group by DATE_FORMAT(data, '%d/%m/%Y'), monitorado_id;  