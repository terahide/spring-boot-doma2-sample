select a.* from arigatos a
inner join users f on (a.from_id = f.user_id)
inner join users t on (a.to_id = t.user_id)
where 1 = 1
/*%if condition.subject != null*/
    and subject like concat('%', /* condition.subject */1, '%')
/*%end*/
/*%if condition.body != null*/
    and body like concat('%', /* condition.body */1, '%')
/*%end*/
/*%if condition.fromName != null*/
    and concat(f.last_name, f.first_name) like concat('%', /* condition.fromName */1, '%')
/*%end*/
/*%if condition.toName != null*/
    and concat(t.last_name, t.first_name) like concat('%', /* condition.toName */1, '%')
/*%end*/
/*%if condition.dateFrom != null*/
    and /*condition.dateFrom*/'2018-12-31' <=  a.created_at
/*%end*/
/*%if condition.dateTo != null*/
    and a.created_at <= /*condition.dateTo*/'2019-12-31'
/*%end*/
order by created_at desc;