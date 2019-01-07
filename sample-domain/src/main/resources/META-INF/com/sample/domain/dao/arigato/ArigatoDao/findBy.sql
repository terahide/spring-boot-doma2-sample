select * from arigatos
where 1 = 1
/*%if condition.getMode() == "FROM_ME"*/
and from_id = /*condition.mineId*/1
/*%end*/
/*%if condition.getMode() == "TO_ME"*/
and to_id = /*condition.mineId*/1
/*%end*/
order by created_at desc;