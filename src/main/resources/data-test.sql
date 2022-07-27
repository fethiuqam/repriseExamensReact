insert into cours ( nom, sigle)
values
    ( 'programmation 1', 'INF1120') ,
    ( 'programmation 2', 'INF2120') ,
    ( 'systeme d exploitation', 'INF3173') ,
    ( 'algorithmique', 'INF5130');


insert into utilisateur (code_ms,nom, prenom, matricule , email, mot_de_passe,dtype)
values
    ('enseignant1','lord', 'melanie', 'LORM45698732', 'lord.melanie.courrier.uqam.ca', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','enseignant'),
    ('enseignant2','jack', 'berger', 'BERJ32695723', 'berger.jack.courrier.uqam.ca', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','enseignant' );

insert into utilisateur (code_ms,code_permanent, nom, prenom, email, telephone, mot_de_passe,dtype)
values
    ('etudiant1','TREM23146985', 'tremblay', 'marc', 'tremblay.marc.courrier.uqam.ca', '5146667777', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','etudiant'),
    ('etudiant2','BEAJ69326598', 'beaudry', 'jean', 'beaudry.jean.courrier.uqam.ca', '5145553333', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','etudiant'),
    ('etudiant3','LESB12544688', 'lestrange', 'beatrice', 'lestrange.beatrice.courrier.uqam.ca', '4508569999', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','etudiant');

insert into utilisateur (code_ms, nom, prenom, mot_de_passe, matricule, dtype)
values
    ('commis','lauzon', 'manon', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW', '1', 'personnel' );

insert into role (nom)
values
    ('commis');

insert into role_permissions (role_id, permissions)
values
    (1, 0),
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6);

insert into utilisateurs_roles (utilisateurs_id, roles_id)
values
    (5, 1);

insert into cours_groupe (groupe, session, annee, cours_id, enseignant_id)
values ('030', 1, '2022', 1, 1),
       ('020', 1, '2022', 2, 2),
       ('040', 2, '2022', 3, 1);

insert into cours_groupe_etudiant (cours_groupe_id, etudiant_id)
values
    ( 1, 3 ),
    ( 2, 3 ),
    ( 3, 3 );

insert into demande_reprise_examen (absence_date_debut , absence_date_fin , motif_absence , absence_details , description_examen , cours_groupe_id , etudiant_id )
values
    ('2022-03-01', '2022-03-10', 0, 'intervention chirurgicale programmee', 'examen intra', 1, 3),
    ('2022-04-20', '2022-04-30', 5, 'autre motif', 'examen final', 3, 3),
    ('2022-04-25', '2022-04-26', 3, 'convocation au tribunal pour temoignage', 'examen final', 2, 4);

insert into justification (description , url , demande_reprise_examen_id )
values
    ('certificat medicale', 'url1_demande1', 1),
    ('rendez-vous medical', 'url2_demande1' , 1),
    ('convocation du tribunal', 'url1_demande3', 3),
    ('justificatif autre', 'url1_demande2', 2);


insert into statut (date_heure , type_statut , demande_reprise_examen_id )
values
    ('2022-01-15T10:34:09', 0, 1),
    ('2022-01-15T10:40:01', 1, 1),
    ('2022-03-15T09:34:09', 0, 2),
    ('2022-03-17T14:40:01', 1, 2),
    ('2022-03-20T10:34:09', 3, 2),
    ('2022-02-12T11:05:08', 0, 3);

insert into decision (date_heure , type_decision , demande_reprise_examen_id )
values
    ('2022-03-20T10:34:09', 0, 2);

insert into message (type_message, contenu, date_heure, demande_reprise_examen_id)
values
    (0, 'Veuillez fournir une preuve médicale', '2022-02-15T10:34:09', 1),
    (0, 'Veuillez fournir une preuve de votre absence', '2022-02-15T10:39:09', 2),
    (1, 'Je vais soumettre un fichier PDF dans la demande', '2022-02-15T12:54:08', 2);
