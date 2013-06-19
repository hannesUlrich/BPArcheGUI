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
  constraint pk_benutzer primary key (accountname))
;

create table element (
  id                        integer not null,
  type                      varchar(255),
  archetype_id              varchar(255),
  constraint pk_element primary key (id))
;

create sequence archetype_seq;

create sequence benutzer_seq;

create sequence element_seq;

alter table element add constraint fk_element_archetype_1 foreign key (archetype_id) references archetype (id) on delete restrict on update restrict;
create index ix_element_archetype_1 on element (archetype_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists archetype;

drop table if exists benutzer;

drop table if exists element;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists archetype_seq;

drop sequence if exists benutzer_seq;

drop sequence if exists element_seq;

