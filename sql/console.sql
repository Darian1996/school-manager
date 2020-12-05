-- login_role_user

select *
from login_role_user
         inner join login_user
                    on login_user.id = login_role_user.user_id;
select *
from login_role_user
         inner join login_role
                    on login_role_user.role_id = login_role.id;

-- login_role_permission
select *
from login_role_permission
         inner join login_role on login_role_permission.role_id = login_role.id;

select *
from login_role_permission
         inner join login_permission on login_role_permission.permission_id = login_permission.id;

-- login_permission_security
select *
from login_permission_security
         inner join login_permission on login_permission.id = login_permission_security.permission_id;
select *
from login_permission_security
         inner join login_security on login_security.id = login_permission_security.security_id;

-- login_role_menu
select *
from login_role_menu
         inner join login_role on login_role.id = login_role_menu.role_id;
select *
from login_role_menu
         inner join login_menu on login_menu.id = login_role_menu.menu_id;

