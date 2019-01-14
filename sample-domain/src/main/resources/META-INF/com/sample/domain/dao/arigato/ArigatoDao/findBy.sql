select * from arigatos
where 1 = 1
/*%if condition.getMode() == "FROM"*/
and from_id = /*condition.userId*/1
/*%end*/
/*%if condition.getMode() == "TO"*/
and to_id = /*condition.userId*/1
/*%end*/
order by created_at desc;