export const Permission = {
  ListerDRE: "ListerDRE",
  AfficherDRE: "AfficherDRE",
  AfficherJustificatifs: "AfficherJustificatifs",
  JugerRecevabilite: "JugerRecevabilite",
  PlanifierDates: "PlanifierDates",
  GererUsagers: "GererUsagers",
  GererRoles: "GererRoles",
};

export const TypeId = {
  Etudiant: "etudiant",
  Enseignant: "enseignant",
  Personnel: "personnel",
};

export const types = {
  [TypeId.Etudiant]: { id: TypeId.Etudiant, nom: "Étudiant" },
  [TypeId.Enseignant]: { id: TypeId.Enseignant, nom: "Enseignant" },
  [TypeId.Personnel]: { id: TypeId.Personnel, nom: "Personnel" },
};
