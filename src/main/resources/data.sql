insert into cours (id, nom, sigle)
values
    (1, 'programmation 1', 'INF1120') ,
    (2, 'programmation 2', 'INF2120') ,
    (3, 'systeme d exploitation', 'INF3173') ,
    (4, 'algorithmique', 'INF5130');

insert into enseignant (id, nom, prenom, matricule , email)
values
    (1,'lord', 'melanie', 'LORM45698732', 'lord.melanie.courrier.uqam.ca' ),
    (2,'jack', 'berger', 'BERJ32695723', 'berger.jack.courrier.uqam.ca' );

insert into etudiant (id, code_permanent, nom, prenom, email, telephone)
values
    (1, 'TREM23146985', 'tremblay', 'marc', 'tremblay.marc.courrier.uqam.ca', '5146667777'),
    (2, 'BEAJ69326598', 'beaudry', 'jean', 'beaudry.jean.courrier.uqam.ca', '5145553333');


insert into cours_groupe (id, groupe, session, cours_id, enseignant_id)
values
    (1, '030', 1, 1, 1 ),
    (2, '020', 1, 2, 2 ),
    (3, '040', 1, 3, 1 );

insert into demande_reprise_examen (id, absence_date_debut , absence_date_fin , motif_absence , absence_details , description_examen , cours_groupe_id , etudiant_id )
values
    (1, '2022-03-01', '2022-03-10', 0, 'intervention chirurgicale programmee', 'examen intra', 1, 1),
    (2, '2022-04-30', '2022-04-20', 5, 'autre motif', 'examen final', 3, 1),
    (3, '2022-04-25', '2022-04-26', 3, 'convocation au tribunal pour temoignage', 'examen final', 2, 2);

insert into justification (id, description , url , demande_reprise_examen_id )
values
    (1, 'certificat medicale', 'url1_demande1', 1),
    (2, 'rendez-vous medical', 'url2_demande1' , 1),
    (3, 'convocation du tribunal', 'url1_demande3', 3),
    (4, 'justificatif autre', 'url1_demande2', 2);


insert into statut (id, date_heure , type_statut , demande_reprise_examen_id )
values
    (1, '2022-01-15T10:34:09', 0, 1),
    (2, '2022-01-15T10:40:01', 1, 1),
    (3, '2022-02-12T11:05:08', 0, 3),
    (4, '2022-03-15T09:34:09', 0, 2),
    (5, '2022-03-17T14:40:01', 1, 2),
    (6, '2022-03-20T10:34:09', 3, 2);