# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table "Author" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"paperid" BIGINT NOT NULL,"personid" BIGINT NOT NULL,"position" INTEGER NOT NULL);
create table "Comment" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"paperid" BIGINT NOT NULL,"personid" BIGINT NOT NULL,"content" VARCHAR NOT NULL);
create table "Email" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"to" VARCHAR NOT NULL,"subject" VARCHAR NOT NULL,"content" VARCHAR NOT NULL);
create table "File" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"name" VARCHAR NOT NULL,"size" BIGINT NOT NULL,"content" BLOB NOT NULL);
create table "LOGIN_USERS" ("UID" TEXT NOT NULL,"PID" TEXT NOT NULL,"EMAIL" TEXT NOT NULL,"FIRSTNAME" TEXT NOT NULL,"LASTNAME" TEXT NOT NULL,"AUTHMETHOD" TEXT NOT NULL,"HASHER" TEXT,"PASSWORD" TEXT,"SALT" TEXT);
alter table "LOGIN_USERS" add constraint "LOGINUSERS_PK" primary key("UID","PID");
create table "Paper" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"title" VARCHAR NOT NULL,"format" INTEGER NOT NULL,"keywords" VARCHAR NOT NULL,"abstrct" VARCHAR NOT NULL,"nauthors" INTEGER NOT NULL,"file" BIGINT NOT NULL);
create table "PaperTopic" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"paperid" BIGINT NOT NULL,"topicid" BIGINT NOT NULL);
create table "Person" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"firstname" VARCHAR NOT NULL,"lastname" VARCHAR NOT NULL,"organization" VARCHAR,"role" INTEGER NOT NULL,"email" VARCHAR NOT NULL);
create table "Review" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"paperid" BIGINT NOT NULL,"personid" BIGINT NOT NULL,"confidence" INTEGER NOT NULL,"evaluation" INTEGER NOT NULL,"content" VARCHAR NOT NULL);
create table "Topic" ("ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL,"updatedat" TIMESTAMP NOT NULL,"updatedby" BIGINT NOT NULL,"name" VARCHAR NOT NULL,"description" VARCHAR NOT NULL);
create table "logintokens" ("uuid" text NOT NULL PRIMARY KEY,"email" text NOT NULL,"creationtime" TIMESTAMP NOT NULL,"expirationtime" TIMESTAMP NOT NULL,"issignup" BOOLEAN NOT NULL,"isinvitation" BOOLEAN NOT NULL);

# --- !Downs

drop table "Author";
drop table "Comment";
drop table "Email";
drop table "File";
alter table "LOGIN_USERS" drop constraint "LOGINUSERS_PK";
drop table "LOGIN_USERS";
drop table "Paper";
drop table "PaperTopic";
drop table "Person";
drop table "Review";
drop table "Topic";
drop table "logintokens";

