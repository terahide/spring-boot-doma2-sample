SELECT * FROM
    users
WHERE
    deleted_at IS NULL
/*%if criteria.id != null */
AND user_id = /* criteria.id */1
/*%end*/
/*%if criteria.email != null */
AND email = /* criteria.email */'aaaa@bbbb.com'
/*%end*/
/*%if criteria.onlyNullAddress */
AND address IS NULL
/*%end*/
