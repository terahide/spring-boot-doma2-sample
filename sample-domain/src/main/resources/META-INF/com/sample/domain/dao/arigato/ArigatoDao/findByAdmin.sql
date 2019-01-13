select * from arigatos
where 1 = 1
/*%if condition.subject != null*/
    and subject like '%' || /*condition.subject*/'a' || '%'
/*%end*/
order by created_at desc;