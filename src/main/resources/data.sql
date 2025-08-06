-- permissions
INSERT INTO permissions (name) VALUES ('DASHBOARD_VIEW');
INSERT INTO permissions (name) VALUES ('USER_EDIT');
INSERT INTO permissions (name) VALUES ('USER_DELETE');

-- roles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_VIEWER');

-- role -> permission mappings
INSERT INTO role_permissions (role_id, permission_id)
VALUES (
           (SELECT id FROM roles WHERE name='ROLE_ADMIN'),
           (SELECT id FROM permissions WHERE name='DASHBOARD_VIEW')
       );

INSERT INTO role_permissions (role_id, permission_id)
VALUES (
           (SELECT id FROM roles WHERE name='ROLE_ADMIN'),
           (SELECT id FROM permissions WHERE name='USER_EDIT')
       );

INSERT INTO role_permissions (role_id, permission_id)
VALUES (
           (SELECT id FROM roles WHERE name='ROLE_ADMIN'),
           (SELECT id FROM permissions WHERE name='USER_DELETE')
       );

INSERT INTO role_permissions (role_id, permission_id)
VALUES (
           (SELECT id FROM roles WHERE name='ROLE_VIEWER'),
           (SELECT id FROM permissions WHERE name='DASHBOARD_VIEW')
       );

-- create default admin user
INSERT INTO users (email, name, picture, enabled)
VALUES ('alice@yourcompany.com', 'Alice Admin', NULL, true);

INSERT INTO user_roles (user_id, role_id)
VALUES (
           (SELECT id FROM users WHERE email='alice@yourcompany.com'),
           (SELECT id FROM roles WHERE name='ROLE_ADMIN')
       );
