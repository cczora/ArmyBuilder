drop schema if exists public cascade;
create schema public;

create table unit_type(
	unit_type_id uuid primary key not null,
    name varchar(20) not null,
	power_points int not null
);

create table faction_type(
	faction_type_id uuid primary key not null,
    name varchar(50) not null
);

create table detachment_type(
	detachment_type_id uuid primary key not null,
    name varchar(50) not null,
    command_pts int not null
);

create table account(
	username varchar primary key not null,
    password varchar(100) not null,
    profile_pic_url varchar(100)
);

create table army(
	army_id uuid primary key not null,
    name varchar(50) not null,
    notes varchar(255),
    command_points int not null,
    size varchar(10) not null,

    username varchar not null,
    constraint username
		foreign key (username)
        references account(username),

    faction_type_id uuid not null,
    constraint afaction_type_id
		foreign key (faction_type_id)
        references faction_type(faction_type_id)
);

create table detachment(
	detachment_id uuid primary key not null,

    army_id uuid not null,
    constraint army_id
		foreign key (army_id)
        references army(army_id),

	detachment_type_id uuid not null,
    constraint detachment_type_id
		foreign key (detachment_type_id)
        references detachment_type(detachment_type_id),

	faction_type_id uuid not null,
    constraint dfaction_type_id
		foreign key (faction_type_id)
        references faction_type(faction_type_id),

    name varchar(25) not null,
    notes varchar(255)
);

create table unit(
	unitId uuid primary key not null,
    detachment_id uuid not null,
    constraint detachment_id
		foreign key (detachment_id)
        references detachment(detachment_id),
    unit_type_id uuid not null,
    constraint unit_type_id
		foreign key (unit_type_id)
        references unit_type(unit_type_id),
	name varchar(50) not null,
    notes varchar(255)
);

insert into unit_type(unit_type_id, name, power_points) values
(cast('fc1fa72b-c5b5-4dc9-84f6-8e53ee267122' as uuid), 'HQ', 10),
(cast('46f8c211-15cf-4ec4-bc31-f79847acb127' as uuid), 'Troop', 5),
(cast('19dce160-7546-484a-980f-17da543583a7' as uuid), 'Elite', 8),
(cast('bd4ef865-01fc-4f02-bcdd-3ed1d22232c2' as uuid), 'Fast Attack', 7),
(cast('a36bfb4e-cf4a-447a-be6b-a3ece4e9d089' as uuid), 'Heavy Support', 6),
(cast('b2429eaa-fd3b-429e-bc4e-aba5a1e5b8a0' as uuid), 'Dedicated Transport', 7),
(cast('853dff0e-0a48-4081-a11f-5d1e7dd0cbc3' as uuid), 'Flyer', 11),
(cast('8ecf0ba8-2502-41a1-a621-d15e4ce5359c' as uuid), 'Fortification', 8),
(cast('b3a1ab1a-672a-4874-8188-05c9b27d6fd2' as uuid), 'Lord of War', 12);

insert into detachment_type(detachment_type_id, name, command_pts) values
(cast('576f8f32-a087-4640-a9b8-fc85c1d71157' as uuid), 'Patrol', 0),
(cast('6c58c958-88d8-4f1a-8a57-7a2e5aa082f9' as uuid), 'Battalion', 3),
(cast('54c955dc-da82-43c4-b7f1-8919bb158234' as uuid), 'Brigade', 9),
(cast('bdecd589-46ef-4abb-9f2e-fb74cf8ebdc7' as uuid), 'Supreme Command', 1),
(cast('b6056d84-9ea1-4fe1-adb8-72c1f4e43d61' as uuid), 'Super-Heavy', 3),
(cast('65d6447a-9057-4187-b26b-a38cb018901d' as uuid), 'Air Wing', 1),
(cast('20d4b37d-bf03-4669-a48c-89aac68c1ea4' as uuid), 'Super-Heavy Auxiliary', 0),
(cast('71bf39a4-0752-4062-859b-43c67da59f95' as uuid), 'Fortification Network', 0),
(cast('e624da25-cdc5-4894-a0fa-eb917c0e956a' as uuid), 'Auxiliary Support', 1), -- Lose 1 CP per each you have in your army once all other bonuses are calculated
(cast('4fbc5c42-9982-4fd1-9a4c-5c3065f61e27' as uuid), 'Vanguard', 1),
(cast('0cc039a1-af07-4de7-b963-657efec80fbc' as uuid), 'Spearhead', 1),
(cast('2fa7ed6b-1c20-4f43-b035-67d0e862dd44' as uuid), 'Outrider', 1);

insert into faction_type(faction_type_id, name) values
(cast('9596a0af-cd09-464d-96e7-2272b6925672' as uuid), 'Chaos Space Marines'),
(cast('53d66fb1-3f47-4fdf-9bb4-546ff12730ba' as uuid), 'Chaos Demons'),
(cast('89c8e399-e725-40a0-8125-28310b0ea797' as uuid), 'Drukhari'),
(cast('0823671c-3332-4112-8502-4bd22814c96b' as uuid), 'Craftworld Eldar'),
(cast('3622c9d0-996c-4a1a-bd0f-17bb62190e22' as uuid), 'Eldar Harlequins'),
(cast('205fe7d0-ed75-4d18-a2f5-7d5324e27f0e' as uuid), 'Necrons'),
(cast('be7f6be3-6fa3-48dc-a677-5d8b76bf2dcb' as uuid), 'Orks'),
(cast('9424e785-206f-483c-a628-7dc7df269b74' as uuid), 'Tau Empire'),
(cast('e58d2f74-cf12-41a4-bae7-cc4950ba5035' as uuid), 'Tyranids'),
(cast('85743142-3446-4fb5-8f1e-9c90a27cca76' as uuid), 'Genestealer Cults'),
(cast('dbcc1470-a2cc-4f80-a537-e61fba8f61e1' as uuid), 'Ultramarines'),
(cast('e2533ff6-1819-41f1-8534-e4232bf22d30' as uuid), 'Blood Angels'),
(cast('eb1c0719-e8f8-49e2-af78-b7785728ed70' as uuid), 'Dark Angels'),
(cast('b0759580-6852-4f92-a2c6-67de8b4f8a58' as uuid), 'Grey Knights'),
(cast('fdd46290-e638-4e2d-8237-32548781ceaf' as uuid), 'Deathwatch'),
(cast('1c53df00-61be-4339-85cc-cfdbaad528f2' as uuid), 'Space Wolves'),
(cast('e857fe0d-6c19-4828-86bc-bc0a7b15f8a1' as uuid), 'Astra Militarum'),
(cast('98ad1ea9-80e2-4bf8-b6b1-db30dcca111c' as uuid), 'Adepta Sororitas'),
(cast('d1772699-71f6-4c14-a02b-bc8c25956a8b' as uuid), 'Imperial Knights'),
(cast('c1995968-1b3a-4303-bcfa-14efda4ac2c4' as uuid), 'Adeptus Mechanicus'),
(cast('0243f3de-45ca-461d-99bc-bf49c0beb6d1' as uuid), 'Adeptus Custodes');

insert into Account(username, password, profile_pic_url) values
('Test User', '$2a$10$qH9Y7rzHTt7aoIsn2O4rZeRJi8fpg9n9ctAnsSNTyu.fApNiJTheu', 'http://www.icanhascheezeburger.com');