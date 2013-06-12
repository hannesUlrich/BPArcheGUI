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

create sequence archetype_seq;

create sequence benutzer_seq;




# --- !Downs

drop table if exists archetype cascade;

drop table if exists benutzer cascade;

drop sequence if exists archetype_seq;

drop sequence if exists benutzer_seq;

