   create table account (
       id bigint not null,
        active boolean,
        balance decimal(19,2),
        created_on timestamp,
        updated_on timestamp,
        account_number varchar(255),
        account_id bigint,
        branch_id bigint,
        primary key (id)
    );
 
    
    create table account_type (
       id bigint not null,
        account_name varchar(255),
        created_on timestamp,
        interest decimal(19,2),
        min_balance decimal(19,2),
        updated_on timestamp,
        primary key (id)
    );
 
    
    create table branch (
       id bigint not null,
        created_on timestamp,
        location varchar(255),
        name varchar(255),
        swift_code varchar(255),
        updated_on timestamp,
        primary key (id)
    );
 
    
    create table customer (
       id bigint not null,
        active boolean,
        created_on timestamp,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        mobile_number varchar(255),
        password varchar(255),
        updated_on timestamp,
        user_name varchar(255),
        segment_id bigint,
        primary key (id)
    );
 
    
    create table customer_accounts (
       customer_id bigint not null,
        accounts_id bigint not null
    );
 
    
    create table segment (
       id bigint not null,
        name varchar(255),
        primary key (id)
    );
 
    
    alter table customer_accounts 
       add constraint UK_a57qsyc891bskuga1eiocwsgu unique (accounts_id);
 
    
    alter table account 
       add constraint FK47tjxomnkavls0efv6oi0dr7 
       foreign key (account_id) 
       references account_type;
 
    
    alter table account 
       add constraint FKcwcof6gi0txp7t7mow4ii4584 
       foreign key (branch_id) 
       references branch;
 
    
    alter table customer 
       add constraint FKfuml6pjd8p2e18vdgt0utkn6s 
       foreign key (segment_id) 
       references segment;
 
    
    alter table customer_accounts 
       add constraint FKmnmtvf493hohxbsowa2nm0uhj 
       foreign key (accounts_id) 
       references account;
 
    
    alter table customer_accounts 
       add constraint FKcx0fjock6ehel4wul0c0mmeb5 
       foreign key (customer_id) 
       references customer;