create table member
(
    member_id    bigint auto_increment comment 'member pk'
        primary key,
    email      varchar(80)  not null comment '이메일',
    name       varchar(40)  not null comment '이름',
    password   varchar(255) not null comment '비밀번호',
    phone      varchar(20)  not null comment '전화번호',
    role       varchar(10)  not null comment '권한(SELLER: 판매자, CUSTOMER: 구매자)',
    created_at datetime     not null comment '생성 일시',
    updated_at datetime     null comment '수정일시'
);