create table users (
  id uuid not null,
  email text not null,
  password text not null,
  preferred_name text not null,
  created_at timestamp default current_timestamp
);

create table roles (
  name varchar(20) not null,
  description text not null,
  created_at timestamp default current_timestamp
);

create table user_roles (
  user_id uuid not null,
  role_name varchar(20) not null,
  created_at timestamp default current_timestamp
);

alter table users
add constraint pk_user primary key (id),
add constraint uq_email unique (email);

alter table roles
add constraint pk_roles primary key (name);

alter table user_roles
add constraint pk_user_roles primary key (user_id, role_name),
add constraint fk_user foreign key (user_id) references users (id),
add constraint fk_role foreign key (role_name) references roles (name);
