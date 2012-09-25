-- consulta Nr 1
select m.screen_name, count(r.screen_name) as 'total tweet', 
count(distinct(r.screen_name)) as 'total pessoas distintas', 
count(r.screen_name) / count(distinct(r.screen_name)) as 'média tweet por pessoa/candidato' 
from resultado r, monitorado m where r.monitorado_id = m.id 
group by monitorado_id

