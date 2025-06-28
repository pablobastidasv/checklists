CREATE TABLE templates (
    id UUID not null,
    name VARCHAR(255) not null,
    description TEXT not null,
    owner_id UUID not null,
    status VARCHAR(20) not null DEFAULT 'ACTIVE'
);

CREATE TABLE template_items (
    id UUID not null,
    template_id UUID not null,
    description TEXT not null,
    completed BOOLEAN not null DEFAULT FALSE,
    position INT not null DEFAULT 0
);

alter table templates
    add constraint pk_templates primary key (id),
    add constraint fk_templates_owner foreign key (owner_id) references users (id) on delete set null;

alter table template_items
    add constraint pk_template_items primary key (id),
    add constraint fk_template_items_templates foreign key (template_id) references templates (id) on delete cascade;



