# --- Created by Slick DDL
# To stop Slick DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table `COMPANY` (`name` VARCHAR(254) NOT NULL,`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY);
create table `COMPUTER` (`name` VARCHAR(254) NOT NULL,`introduced` BIGINT,`discontinued` BIGINT,`companyId` BIGINT,`id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY);

# --- !Downs

drop table `COMPANY`;
drop table `COMPUTER`;

