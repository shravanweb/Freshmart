-- Keep profile app role aligned with the canonical user role.
UPDATE _user_profile up
SET _app_role = u._role
FROM _user u
WHERE up._user_id = u._id
  AND up._app_role IS DISTINCT FROM u._role;
