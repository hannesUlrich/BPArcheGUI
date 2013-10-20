# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table archetype (
  id                        varchar(255) not null,
  name                      varchar(255),
  purpose                   varchar(255),
  usage                     varchar(255),
  misusage                  varchar(255),
  constraint pk_archetype primary key (id))
;

create table benutzer (
  accountname               varchar(255) not null,
  password                  varchar(255),
  full_name                 varchar(255),
  themen_type               integer,
  constraint pk_benutzer primary key (accountname))
;

create table choice (
  id                        integer not null,
  choice_string             varchar(255),
  element_id                integer,
  constraint pk_choice primary key (id))
;

create table daten (
  id                        integer not null,
  archetype_id              varchar(255),
  user_id                   varchar(255),
  value                     varchar(255),
  selected                  varchar(255),
  constraint pk_daten primary key (id))
;

create table element (
  id                        integer not null,
  type                      varchar(255),
  min                       integer,
  max                       integer,
  archetype_id              varchar(255),
  constraint pk_element primary key (id))
;

create table used_arche_string (
  id                        integer not null,
  archetype_id              varchar(255),
  used_archetype            varchar(255),
  constraint pk_used_arche_string primary key (id))
;

create sequence archetype_seq;

create sequence benutzer_seq;

create sequence choice_seq;

create sequence daten_seq;

create sequence element_seq;

create sequence used_arche_string_seq;

alter table choice add constraint fk_choice_element_1 foreign key (element_id) references element (id) on delete restrict on update restrict;
create index ix_choice_element_1 on choice (element_id);
alter table daten add constraint fk_daten_archetype_2 foreign key (archetype_id) references archetype (id) on delete restrict on update restrict;
create index ix_daten_archetype_2 on daten (archetype_id);
alter table element add constraint fk_element_archetype_3 foreign key (archetype_id) references archetype (id) on delete restrict on update restrict;
create index ix_element_archetype_3 on element (archetype_id);
alter table used_arche_string add constraint fk_used_arche_string_archetype_4 foreign key (archetype_id) references archetype (id) on delete restrict on update restrict;
create index ix_used_arche_string_archetype_4 on used_arche_string (archetype_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists archetype;

drop table if exists benutzer;

drop table if exists choice;

drop table if exists daten;

drop table if exists element;

drop table if exists used_arche_string;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists archetype_seq;

drop sequence if exists benutzer_seq;

drop sequence if exists choice_seq;

drop sequence if exists daten_seq;

drop sequence if exists element_seq;

drop sequence if exists used_arche_string_seq;

