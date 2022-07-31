insert into cours (nom, sigle)
values ('programmation 1', 'INF1120'),
       ('programmation 2', 'INF2120'),
       ('systeme d exploitation', 'INF3173'),
       ('algorithmique', 'INF5130'),
       ('base de donnees', 'INF3080'),
       ('teleinformatique', 'INF3271'),
       ('structure de donnees et algorithmes', 'INF3105'),
       ('principe des systemes d exploitation', 'INF3173'),
       ('mathématiques pour l informatique', ' INF1132'),
       ('statistiques pour les sciences', 'MAT4681'),
       ('utilisation et administration des systemes informatiques', 'INF1070'),
       ('organisation, gestion et systeme d information', 'AOT1110'),
       ('economie des technologies de l information', 'ECO1081'),
       ('introduction a la programmation web', 'ÌNF3190'),
       ('construction et maintenance logiciels', 'INF3135'),
       ('genie logiciel : analyse et modelisation', 'INF5151'),
       ('reseaux sans fil et applications mobiles', 'TEL4165'),
       ('genie logiciel: conception', 'INF5153'),
       ('projet d analyse et modelisation', 'INM5151'),
       ('programmation web avancee', 'INF5190'),
       ('genie logiciel : conduite de projets informatiques', 'INF6150'),
       ('programmation fonctionnelle et logique', 'INF6120'),
       ('informatique et societe', 'INM6000'),
       ('architecture des ordinateurs', 'INF4170'),
       ('interface personne-machines', 'INF4150'),
       ('intellignece artificielle', 'INF4230'),
       ('introduction a la securite informatique', 'INF4471'),
       ('bioinformatique', 'INF4500'),
       ('theorie et compilation des compilateurs', 'INF5000'),
       ('infographie', 'INF5071'),
       ('gestion et analyse de donnees', 'INF5081'),
       ('programmation concurente et parallele', 'INF5171'),
       ('introduction aux progiciels de gestion integres', 'AOT5332'),
       ('implantation et configuration de progiciels et gestion integres', 'AOT5334'),
       ('systemes decisionels et intelligence d affaires', 'AOT5321'),
       ('collaboration a l ere du travail hybride', 'AOT5341');

insert into utilisateur (code_ms, nom, prenom, matricule, email, mot_de_passe, dtype)
values ('enseignant1', 'lord', 'melanie', 'LORM45698732', 'lord.melanie.courrier.uqam.ca',
        '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', 'enseignant'),
       ('enseignant2', 'jack', 'berger', 'BERJ32695723', 'berger.jack.courrier.uqam.ca',
        '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', 'enseignant');

insert into utilisateur (code_ms, code_permanent, nom, prenom, email, telephone, mot_de_passe, dtype)
values ('etudiant1', 'TREM23146985', 'tremblay', 'marc', 'tremblay.marc.courrier.uqam.ca', '5146667777',
        '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', 'etudiant'),
       ('etudiant2', 'BEAJ69326598', 'beaudry', 'jean', 'beaudry.jean.courrier.uqam.ca', '5145553333',
        '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', 'etudiant'),
       ('etudiant3', 'TREM23146985', 'tremblay', 'marc', 'tremblay.marc.courrier.uqam.ca', '5146667777',
        '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', 'etudiant'),
       ('etudiant4', 'BEAJ69326598', 'beaudry', 'jean', 'beaudry.jean.courrier.uqam.ca', '5145553333',
        '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', 'etudiant');

insert into utilisateur (code_ms, nom, prenom, mot_de_passe, matricule, dtype)
values ('commis', 'lauzon', 'manon', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', '1', 'personnel'),
       ('directeur', 'beaudry', 'eric', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', '2',
        'personnel');

insert into role (nom)
values ('commis'),
       ('directeur'),
       ('admin');

insert into role_permissions (role_id, permissions)
values (1, 0),
       (1, 1),
       (1, 2),
       (1, 3),
       (1, 5),
       (1, 6),
       (1, 9),
       (3, 7),
       (3, 8),
       (2, 0),
       (2, 1),
       (2, 2),
       (2, 4);

insert into utilisateurs_roles (utilisateurs_id, roles_id)
values (7, 1),
       (8, 2),
       (8, 3);

insert into cours_groupe (groupe, session, annee, cours_id, enseignant_id)
values ('030', 1, '2022', 1, 1),
       ('020', 1, '2022', 2, 2),
       ('040', 2, '2022', 3, 1);

insert into cours_groupe_etudiant (cours_groupe_id, etudiant_id)
values (1, 3),
       (2, 3),
       (3, 3);

insert into demande_reprise_examen (absence_date_debut, absence_date_fin, motif_absence, absence_details,
                                    description_examen, cours_groupe_id, etudiant_id)
values ('2022-03-01', '2022-03-10', 0, 'intervention chirurgicale programmee', 'examen intra', 1, 3),
       ('2022-04-20', '2022-04-30', 5, 'autre motif', 'examen final', 3, 3),
       ('2022-04-25', '2022-04-26', 3, 'convocation au tribunal pour temoignage', 'examen final', 3, 4),
       ('2022-01-15', '2022-02-03', 4, 'fete religieuse', 'examen intra', 2, 4),
       ('2018-01-20', '2018-02-21', 4, 'fete religieuse juive', 'examen intra', 1, 4),
       ('2022-01-15', '2022-03-03', 2, 'accident de ski', 'examen intra', 2, 4),
       ('2022-02-15', '2022-02-20', 1, 'deces de conjoint', 'examen intra', 2, 5),
       ('2022-01-08', '2022-02-02', 5, 'autre motif', 'examen intra', 1, 5),
       ('2022-01-15', '2022-03-03', 4, 'acceptation a la papeaute', 'examen intra', 1, 5),
       ('2022-03-04', '2022-05-28', 4, 'pape courant', 'examen final', 3, 5),
       ('2022-03-08', '2022-03-016', 5, 'autre motif', 'examen intra', 2, 5);

insert into justification (description, url, demande_reprise_examen_id)
values ('certificat medicale', 'url1_demande1', 1),
       ('rendez-vous medical', 'url2_demande1', 1),
       ('convocation du tribunal', 'url1_demande3', 3),
       ('justificatif autre', 'url1_demande2', 2);


insert into statut (date_heure, type_statut, demande_reprise_examen_id)
values ('2022-01-15T10:34:09', 0, 1),
       ('2022-01-16T10:40:01', 1, 1),
       ('2022-01-17T10:45:01', 2, 1),
       ('2022-05-01T09:34:09', 0, 2),
       ('2022-05-03T14:40:01', 1, 2),
       ('2022-05-05T10:34:09', 3, 2),
       ('2022-04-27T11:00:01', 1, 3),
       ('2022-04-29T11:34:09', 3, 3),
       ('2022-02-04T14:36:02', 1, 4),
       ('2022-02-05T14:37:02', 3, 4),
       ('2018-02-22T11:01:10', 1, 5),
       ('2018-02-24T14:36:02', 3, 5),
       ('2018-03-22T10:02:07', 4, 5),
       ('2018-03-29T10:02:07', 6, 5),
       ('2018-04-10T18:30:09', 8, 5),
       ('2021-03-03T10:34:09', 1, 6),
       ('2021-03-04T11:02:18', 3, 6),
       ('2021-04-15T09:34:09', 5, 6),
       ('2021-02-20T09:34:09', 1, 7),
       ('2021-02-21T10:34:09', 3, 7),
       ('2021-02-27T15:35:08', 5, 7),
       ('2021-03-05T10:40:01', 1, 8),
       ('2021-03-08T10:40:01', 3, 8),
       ('2021-03-20T10:40:01', 4, 8),
       ('2020-03-01T12:05:08', 1, 9),
       ('2020-05-20T14:36:02', 1, 10),
       ('2020-05-29T15:36:05', 2, 10),
       ('2022-02-03T10:34:08', 1, 11),
       ('2022-02-05T11:34:09', 3, 11),
       ('2022-02-08T11:34:09', 4, 11),
       ('2022-02-20T11:34:09', 6, 11);



insert into decision (date_heure, type_decision, demande_reprise_examen_id)
values ('2022-05-03T11:00:09', 0, 2),
       ('2022-03-15T10:40:01', 2, 3),
       ('2022-03-25T10:40:01', 1, 4),
       ('2018-02-05T10:40:01', 0, 5),
       ('2018-02-08T10:40:01', 2, 5),
       ('2018-02-20T10:40:01', 4, 5),
       ('2021-02-27T10:40:01', 0, 6),
       ('2021-03-03T11:30:01', 3, 6),
       ('2021-02-22T15:35:08', 0, 7),
       ('2021-02-25T15:35:08', 2, 7),
       ('2021-02-27T15:35:08', 5, 7),
       ('2021-03-05T10:40:01', 0, 8),
       ('2021-03-08T10:40:01', 2, 8),
       ('2021-03-20T10:40:01', 4, 8),
       ('2022-02-08T12:05:08', 0, 11),
       ('2022-02-10T12:05:08', 2, 11),
       ('2022-02-12T12:05:08', 4, 11);

insert into reprise (date_heure, duree_minutes, local, surveillant, cours_groupe_id)
values ('2022-04-15T09:30', 120, 'PK4130', 'marc pelletier', 2);

update cours_groupe set reprise_id = 1 where id = 2 ;

insert into message (type_message, contenu, date_heure, demande_reprise_examen_id)
values (0, 'Veuillez fournir une preuve médicale', '2022-02-15T10:34:09', 1),
       (0, 'Veuillez fournir une preuve de votre absence', '2022-02-15T10:39:09', 2),
       (1, 'Je vais soumettre un fichier PDF dans la demande', '2022-02-15T12:54:08', 2);
