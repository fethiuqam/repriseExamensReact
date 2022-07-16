insert into cours ( nom, sigle)
values
    ('programmation 1', 'INF1120'),
    ('programmation 2', 'INF2120'),
    ('systeme d exploitation', 'INF3173'),
    ('algorithmique', 'INF5130'),
    ('base de donnees', 'INF3080'),
    ('teleinformatique', 'INF3271'),
    ('structure de donnees et algorithmes', 'INF3105'),
    ('principe des systemes d exploitation', 'INF3173'),
    ('mathématiques pour l informatique',' INF1132'),
    ('statistiques pour les sciences','MAT4681'),
    ('utilisation et administration des systemes informatiques','INF1070'),
    ('organisation, gestion et systeme d information','AOT1110'),
    ('economie des technologies de l information','ECO1081'),
    ('introduction a la programmation web', 'ÌNF3190'),
    ('construction et maintenance logiciels','INF3135'),
    ('genie logiciel : analyse et modelisation', 'INF5151'),
    ('reseaux sans fil et applications mobiles','TEL4165'),
    ('genie logiciel: conception','INF5153'),
    ('projet d analyse et modelisation', 'INM5151'),
    ('programmation web avancee','INF5190'),
    ('genie logiciel : conduite de projets informatiques','INF6150'),
    ('programmation fonctionnelle et logique','INF6120'),
    ('informatique et societe', 'INM6000'),
    ('architecture des ordinateurs','INF4170'),
    ('interface personne-machines', 'INF4150'),
    ('intellignece artificielle','INF4230'),
    ('introduction a la securite informatique','INF4471'),
    ('bioinformatique','INF4500'),
    ('theorie et compilation des compilateurs','INF5000'),
    ('infographie','INF5071'),
    ('gestion et analyse de donnees','INF5081'),
    ('programmation concurente et parallele','INF5171'),
    ('introduction aux progiciels de gestion integres','AOT5332'),
    ('implantation et configuration de progiciels et gestion integres','AOT5334'),
    ('systemes decisionels et intelligence d affaires','AOT5321'),
    ('collaboration a l ere du travail hybride','AOT5341');

insert into utilisateur ( code_ms,nom, prenom, matricule , email, mot_de_passe,dtype)
values
    ('enseignant1','lord', 'melanie', 'LORM45698732', 'lord.melanie.courrier.uqam.ca', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','enseignant' ),
    ('enseignant2','jack', 'berger', 'BERJ32695723', 'berger.jack.courrier.uqam.ca',  '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','enseignant' );

insert into utilisateur (code_ms,code_permanent, nom, prenom, email, telephone, mot_de_passe,dtype)
values
    ('etudiant1','TREM23146985', 'tremblay', 'marc', 'tremblay.marc.courrier.uqam.ca', '5146667777', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','etudiant'),
    ('etudiant2','BEAJ69326598', 'beaudry', 'jean', 'beaudry.jean.courrier.uqam.ca', '5145553333', '$2a$10$zNrHpjFWBtIYMAQUhtN9pejTJxlJ/tU7pt4SO1tIUeITtveWpU4nW','etudiant');

insert into utilisateur (code_ms, nom, prenom, mot_de_passe, employe_id, dtype)
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

insert into cours_groupe (groupe, session, cours_id, enseignant_id)
values
    ('030', 1, 1, 1),
    ('020', 1, 2, 2),
    ('040', 1, 3, 1);

insert into demande_reprise_examen (absence_date_debut , absence_date_fin , motif_absence , absence_details , description_examen , cours_groupe_id , etudiant_id )
values
    ('2022-03-01', '2022-03-10', 0, 'intervention chirurgicale programmee', 'examen intra', 1, 3),
    ('2022-04-30', '2022-04-20', 5, 'autre motif', 'examen final', 3, 3),
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
    ('2022-02-12T11:05:08', 0, 3),
    ('2022-03-15T09:34:09', 0, 2),
    ('2022-03-17T14:40:01', 1, 2),
    ('2022-03-20T10:34:09', 3, 2);

insert into message (type_message, contenu, date_heure, demande_reprise_examen_id)
values
    (0, 'Veuillez fournir une preuve médicale', '2022-02-15T10:34:09', 1),
    (0, 'Veuillez fournir une preuve de votre absence', '2022-02-15T10:39:09', 2),
    (1, 'Je vais soumettre un fichier PDF dans la demande', '2022-02-15T12:54:08', 2),
    (0, 'Veuillez préciser la période de votre convocation', '2022-02-15T10:45:09', 3),
    (1, 'De lundi à vendredi passé', '2022-02-15T12:54:08', 3),
    (0, 'Pouvez-vous fournir une demande officielle prouvant que vous avez été convoqué?', '2022-02-15T13:22:05', 3);