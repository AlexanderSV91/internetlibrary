INSERT number_authorizations(quantity, last_authorization_date)
VALUES (3, '2021-02-14 23:59:59'),
       (3, '2021-02-24 23:59:59'),
       (3, '2021-04-20 23:59:59');

UPDATE users
SET number_authorization_id = 1
WHERE id = 1;

UPDATE users
SET number_authorization_id = 2
WHERE id = 2;

UPDATE users
SET number_authorization_id = 3
WHERE id = 3;
