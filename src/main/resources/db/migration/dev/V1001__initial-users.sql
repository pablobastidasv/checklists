insert
into
    roles
    (description, name)
values
    ('User role', 'USER');

insert
into
    users
    (email, password, id, preferred_name)
values
    ('alice@email.com', '$2a$12$HKtQ7yt3Xf9Xv4YYVl/ePu44xhQ6B2HwWm7nL01YG66rGES.aSajm',
     '0114dd8a-498c-4232-96f0-743e3345b44e', 'Alice Rabbit');

insert
into
    user_roles
    (user_id, role_name)
values
    ('0114dd8a-498c-4232-96f0-743e3345b44e', 'USER');
