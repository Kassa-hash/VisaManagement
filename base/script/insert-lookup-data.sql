-- Insérer les sexes
INSERT INTO sexe (libelle) VALUES 
('Masculin'),
('Féminin');

-- Insérer les nationalités
INSERT INTO nationalite (libelle) VALUES 
('Malagasy'),
('Français'),
('Américain'),
('Britannique'),
('Allemand'),
('Chinois'),
('Indien');

-- Insérer les pays
INSERT INTO pays (libelle) VALUES 
('Madagascar'),
('France'),
('États-Unis'),
('Royaume-Uni'),
('Allemagne'),
('Chine'),
('Inde'),
('Japon'),
('Afrique du Sud');

-- Insérer les situations familiales
INSERT INTO situation_familiale (libelle) VALUES 
('Célibataire'),
('Marié(e)'),
('Divorcé(e)'),
('Veuf/Veuve'),
('Pacsé(e)');

-- Insérer les types de visa
INSERT INTO type_visa (libelle) VALUES 
('Visa Investisseur'),
('Visa Travailleur'),
('Visa Étudiant'),
('Visa Touristique'),
('Visa Familial');

-- Insérer les types de demande de visa
INSERT INTO type_demande_visa (libelle) VALUES 
('Première demande'),
('Renouvellement'),
('Transformation');

-- Insérer les documents communs
INSERT INTO documents_communs (libelle) VALUES 
('Extrait de naissance'),
('Certificat de mariage'),
('Certificat de divorce'),
('Passeport'),
('Carte d''identité'),
('Certificat de résidence'),
('Casier judiciaire');

-- Insérer les documents spécifiques
INSERT INTO documents_types (libelle) VALUES 
('Certificat de scolarité'),
('Lettre d''acceptation universitaire'),
('Contrat de travail'),
('Preuve de résidence'),
('Justificatif de revenus'),
('Assurance maladie'),
('Bilan de santé'),
('Attestation d''hébergement'),
('Certificat d''investissement');

-- Insérer les statuts
INSERT INTO statut (libelle) VALUES 
('En attente'),
('Acceptée'),
('Refusée'),
('Suspendue'),
('En cours de traitement');
