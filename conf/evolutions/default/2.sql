# --- !Ups

create table sa_question(
  id bigint not null auto_increment primary key,
  question varchar,
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
  choice int,
  create_at timestamp default current_timestamp not null,
  update_at timestamp default current_timestamp not null
);

# --- !Downs

drop table sa_question;
drop table sa_answer;

