CREATE TABLE checklists (
    id UUID not null,
    name VARCHAR(255) not null,
    description TEXT not null,
    owner_id UUID not null,
    status VARCHAR(20) not null DEFAULT 'ACTIVE'
);

CREATE TABLE checklist_items (
    id UUID not null,
    checklist_id UUID not null,
    description TEXT not null,
    completed BOOLEAN not null DEFAULT FALSE,
    position INT not null DEFAULT 0
);

alter table checklists
    add constraint pk_checklists primary key (id),
    add constraint fk_checklists_owner foreign key (owner_id) references users (id) on delete set null;

alter table checklist_items
    add constraint pk_checklist_items primary key (id),
    add constraint fk_checklist_items_checklists foreign key (checklist_id) references checklists (id) on delete cascade;



