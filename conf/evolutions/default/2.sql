# --- !Ups

create table survey(
  id bigint not null auto_increment primary key,
  survey_title varchar not null,
  create_at timestamp default current_timestamp not null,
  update_at timestamp default current_timestamp not null
);

create table sa_question(
  id bigint not null auto_increment primary key,
  survey_id bigint not null,
  question varchar not null,
  question_count int not null,
  choice1 varchar,
  choice2 varchar,
  choice3 varchar,
  choice4 varchar,
  choice5 varchar,
  create_at timestamp default current_timestamp not null,
  update_at timestamp default current_timestamp not null
);

create table sa_answer(
  id bigint not null auto_increment primary key,
  question_id bigint not null,
  choice1 int,
  choice2 int,
  choice3 int,
  choice4 int,
  choice5 int,
  create_at timestamp default current_timestamp not null,
  update_at timestamp default current_timestamp not null
);

create table eo_question(
  id bigint not null auto_increment primary key,
  survey_id bigint not null,
  question varchar not null,
  create_at timestamp default current_timestamp not null,
  update_at timestamp default current_timestamp not null
);

create table eo_answer(
  id bigint not null auto_increment primary key,
  question_id bigint not null,
  choice int,
  create_at timestamp default current_timestamp not null,
  update_at timestamp default current_timestamp not null
);

# --- !Downs

drop table sa_answer;
drop table sa_question;
drop table eo_answer;
drop table eo_question;
drop table survey;

