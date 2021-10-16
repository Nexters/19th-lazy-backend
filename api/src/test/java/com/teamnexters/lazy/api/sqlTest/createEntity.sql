# 습관 Table
create table habit (
                       habit_idx bigint not null auto_increment,
                       created_dt datetime,
                       modified_dt datetime,
                       habit_category integer not null,
                       habit_delay_day integer not null,
                       habit_detail varchar(255),
                       habit_frequency varchar(255),
                       habit_name varchar(255) not null,
                       habit_notice_state bit,
                       habit_notice_time time,
                       mem_idx bigint,
                       primary key (habit_idx)
) engine=InnoDB;

# 회원 Table
create table member (
                        mem_idx bigint not null auto_increment,
                        oauth_id varchar(255),
                        password varchar(255),
                        created_dt datetime,
                        modified_dt datetime,
                        mem_email varchar(255) not null,
                        mem_name varchar(255) not null,
                        mem_nickname varchar(255),
                        mem_image_url varchar(255) not null,
                        provider varchar(255) not null,
                        role varchar(255) not null,
                        primary key (mem_idx)
) engine=InnoDB;