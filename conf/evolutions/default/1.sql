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

create table user (
  accountname               varchar(255) not null,
  password                  varchar(255),
  full_name                 varchar(255),
  constraint pk_user primary key (accountname))
;

create sequence archetype_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists archetype;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists archetype_seq;

drop sequence if exists user_seq;

