create database rms;
use rms;

create table users_(
  id_ int primary key auto_increment,
  username_ varchar(100) not null unique,
  password_ varchar(280) not null,
  first_name_ varchar(50) not null,
  last_name_ varchar(50) not null,
  status_ boolean not null default true
);        

create table admins_(
  id_ int primary key auto_increment,
  username_ varchar(100) not null unique,
  password_ varchar(280) not null
);  
 
create table positions_(
  id_ int primary key auto_increment,
  title_ varchar(100) not null unique,
  status_ boolean not null default true
);
 
create table employees_(
  id_ int primary key auto_increment,
  first_name_ varchar(50) not null,
  last_name_ varchar(50) not null,
  email_ varchar(100) not null unique,
  pos_id_ int not null,
  phone_ varchar(16) not null unique,
  status_ boolean not null default true,
  foreign key(pos_id_) references positions_(id_)
);

create table categories_(
  id_ int primary key auto_increment,
  title_ varchar(100) not null unique,
  status_ boolean not null default true
);

create table products_(
  id_ int primary key auto_increment,
  title_ varchar(100) not null unique,
  cate_id_ int not null,
  price_ decimal(6,2) not null,
  status_ boolean not null default true,
  foreign key(cate_id_) references categories_(id_) 
);

create table orders_(
  id_ int primary key auto_increment,
  title_ varchar(100) not null unique,
  user_id_ int not null,
  created_at_ datetime not null default now(),
  updated_at_ datetime not null default now(),
  completed_ boolean not null default false,
  foreign key(user_id_) references users_(id_)
); 

create table ordered_products_(
  id_ int primary key auto_increment,
  prod_id_ int not null,
  order_id_ int not null,
  created_at_ datetime not null default now(), 
  foreign key(order_id_) references orders_(id_) on DELETE cascade,
  foreign key(prod_id_) references products_(id_)
);

create table errors_(
  id_ int primary key auto_increment,
  msg_ varchar(255) not null,
  date_ datetime not null default now()  
);

