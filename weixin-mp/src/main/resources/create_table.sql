/* this is for in-memory H2 DB initialization */
SET SCHEMA public;

create table api_message
(
    channel_id integer not null,
    incoming_message text not null,
    outcoming_message text,
    state integer not null,
    response text,
    created_at timestamp default now(),
    id uuid not null
        constraint api_message_id_pk
        primary key,
    reference_id varchar(255) not null,
    ref_id varchar(50) default ''::character varying not null
)
;

create unique index api_message_id_uindex
    on api_message (id)
;

create index api_message_reference_id_index
    on api_message (reference_id)
;

create index api_message_ref_id_index
    on api_message (ref_id)
;

create table execution_step
(
    id uuid not null
        constraint execution_step_pkey
        primary key,
    disbursement_id uuid not null
        constraint execution_step_api_message_id_fk
        references api_message,
    ref_id varchar(50),
    request text,
    response text,
    serial_version_uid bigint not null,
    state integer not null,
    created_at timestamp default now() not null
)
;

create unique index execution_step_id_uindex
    on execution_step (id)
;

create table arrival_notify
(
    id uuid not null
        constraint arrival_notify_pkey
        primary key,
    receipt_id varchar(32) not null,
    payout_amt varchar(15) not null,
    remain_payout_amt varchar(15) not null,
    content text,
    created_at timestamp default now() not null
)
;

create unique index arrival_notify_id_uindex
    on arrival_notify (id)
;

create unique index arrival_notify_receipt_id_uindex
    on arrival_notify (receipt_id)
;