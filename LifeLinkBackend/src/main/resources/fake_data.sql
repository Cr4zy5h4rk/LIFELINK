INSERT INTO statistics (id, number_of_life_saved, target_blood_bags, number_of_donors)
VALUES (2023, 1150, 50000, 1200);

INSERT INTO statistics (id, number_of_life_saved, target_blood_bags, number_of_donors)
VALUES (2024, 1300, 100000, 1450);

INSERT INTO statistics (id, number_of_life_saved, target_blood_bags, number_of_donors)
VALUES (2025, 1500, 200000, 1800);

INSERT INTO region (name) VALUES
                              ('Dakar'),
                              ('Diourbel'),
                              ('Fatick'),
                              ('Kaffrine'),
                              ('Kaolack'),
                              ('Kédougou'),
                              ('Kolda'),
                              ('Louga'),
                              ('Matam'),
                              ('Saint-Louis'),
                              ('Sédhiou'),
                              ('Tambacounda'),
                              ('Thiès'),
                              ('Ziguinchor');

INSERT INTO address (address, longitude, latitude, region_id) VALUES
                                                                  ('Dakar Plateau', -16.0756, 14.1825, 1),
                                                                  ('Parcelles Assainies', -17.4307, 14.7597, 1),
                                                                  ('Touba', -15.8766, 14.8561, 2),
                                                                  ('Kaolack Centre', -16.0756, 14.1825, 5),
                                                                  ('Saint-Louis', -16.4842, 16.0323, 10),
                                                                  ('Tambacounda', -13.6730, 13.7712, 12),
                                                                  ('Kolda Ville', -14.9382, 12.8863, 7),
                                                                  ('Ziguinchor', -16.2646, 12.5598, 14),
                                                                  ('Thiès Nord', -16.9253, 14.7894, 13),
                                                                  ('Louga', -16.2249, 15.6170, 8);


INSERT INTO blood_donation_campaign
(status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    ( 'IN_PROGRESS', '2025-02-10T08:00:00Z','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '77 123 45 67', 'Title: Campagne Don de Sang Dakar Centre | Description: Donnez votre sang, sauvez des vies! Nous avons besoin de donneurs de tout groupe sanguin | Location: Centre National de Transfusion Sanguine, Dakar | Schedule: Lundi-Vendredi 9h-17h, Samedi 9h-12h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign1_wolof.mp3',1);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (2, 'IN_PROGRESS', '2025-02-15T09:00:00Z','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '77 987 65 43', 'Title: Don de Sang Urgence Hôpital Principal | Description: Urgence pour groupes O+ et O-. Votre don peut sauver des vies maintenant! | Location: Hôpital Principal de Dakar | Schedule: Tous les jours 8h-18h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign2_wolof.mp3',5);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (3, 'COMPLETED','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-01-12T10:00:00Z', '78 123 45 67', 'Title: Journée du Don - Université Cheikh Anta Diop | Description: Grande collecte de sang sur le campus, venez nombreux! | Location: UCAD, Amphithéâtre principal | Schedule: Mercredi 10h-16h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign3_wolof.mp3',3);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (4, 'NOT_STARTED','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-03-20T09:30:00Z', '76 345 67 89', 'Title: Don de Sang Entreprises - Zone Industrielle | Description: Collecte organisée pour les employés des entreprises de la zone industrielle | Location: Centre Commercial Dipson | Schedule: Jeudi-Vendredi 10h-15h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign4_wolof.mp3',3);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (5, 'IN_PROGRESS','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-02-28T08:30:00Z', '77 456 78 90', 'Title: Don de Sang Spécial Thalassémie | Description: Campagne dédiée aux patients atteints de thalassémie. Tous les groupes sanguins sont bienvenus | Location: Hôpital Abass Ndao | Schedule: Lundi-Mercredi 9h-16h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign5_wolof.mp3',6);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (6, 'CANCELLED', '2025-02-10T08:00:00Z','2025-02-10T08:00:00Z','2025-02-05T10:00:00Z', '70 123 45 67', 'Title: Campagne Mobile Rufisque | Description: Notre unité mobile sera présente à Rufisque pour faciliter vos dons | Location: Place Publique de Rufisque | Schedule: Samedi 9h-17h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign6_wolof.mp3',6);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (7, 'IN_PROGRESS', '2025-02-22T09:00:00Z','2025-02-22T09:00:00Z','2025-02-22T09:00:00Z', '77 234 56 78', 'Title: SOS Pédiatrie - Don de Sang Urgent | Description: Besoin critique pour les enfants hospitalisés, tous groupes sanguins | Location: Hôpital pour Enfants de Diamniadio | Schedule: Tous les jours 8h-20h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign7_wolof.mp3',7);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (8, 'IN_PROGRESS', '2025-03-01T08:30:00Z','2025-02-22T09:00:00Z','2025-02-22T09:00:00Z', '77 765 43 21', 'Title: Grande Collecte Thiès | Description: Première grande collecte de l''année à Thiès, objectif 300 poches | Location: Centre Culturel de Thiès | Schedule: Vendredi-Samedi 9h-18h, Dimanche 9h-12h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign8_wolof.mp3',2);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(9, 'NOT_STARTED', '2025-02-10T08:00:00Z','2025-02-10T08:00:00Z','2025-03-15T10:00:00Z', '78 987 65 43', 'Title: Don du Sang Saint-Louis | Description: Collecte organisée par l''Association des Étudiants en Médecine | Location: Université Gaston Berger, Saint-Louis | Schedule: Mardi-Mercredi 10h-16h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign9_wolof.mp3',4);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
    (10, 'COMPLETED','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-01-25T09:00:00Z', '77 111 22 33', 'Title: Journée Mondiale du Don de Sang | Description: Célébration de la journée mondiale du don de sang avec collecte spéciale | Location: Place de l''Obélisque, Dakar | Schedule: Samedi 8h-18h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign10_wolof.mp3',5);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(11, 'IN_PROGRESS','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-02-18T08:30:00Z', '77 333 44 55', 'Title: Don de Sang Mbour | Description: Collecte de sang à Mbour, tous les groupes sanguins sont nécessaires | Location: Centre de Santé de Mbour | Schedule: Lundi-Mardi 9h-15h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign11_wolof.mp3',3);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(12, 'IN_PROGRESS','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-03-05T09:00:00Z', '77 444 55 66', 'Title: Campagne Spéciale A+ et B+ | Description: Besoin urgent de donneurs A+ et B+ pour les réserves nationales | Location: Centre National de Transfusion Sanguine, Dakar | Schedule: Mercredi-Vendredi 9h-17h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign12_wolof.mp3',6);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(13, 'NOT_STARTED','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-03-25T10:00:00Z', '77 555 66 77', 'Title: Don de Sang Écoles | Description: Sensibilisation et collecte dans les écoles de Dakar | Location: Lycée Seydou Nourou Tall | Schedule: Lundi 10h-15h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign13_wolof.mp3',5);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(14, 'COMPLETED','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-01-18T09:30:00Z', '77 666 77 88', 'Title: Don de Sang Militaire | Description: Collecte organisée par les forces armées, ouverte au public | Location: Camp Militaire de Ouakam | Schedule: Jeudi 8h-16h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign14_wolof.mp3',1);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(15, 'IN_PROGRESS','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-02-12T08:00:00Z', '77 777 88 99', 'Title: Don de Sang Kaolack | Description: Grande collecte à Kaolack, venez nombreux pour sauver des vies | Location: Hôpital Régional de Kaolack | Schedule: Mardi-Mercredi 9h-16h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign15_wolof.mp3',1);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(16, 'IN_PROGRESS','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-03-08T09:00:00Z', '77 888 99 00', 'Title: Don de Sang Féminin | Description: Campagne spéciale pour encourager les femmes à donner leur sang | Location: Maison de la Femme, Dakar | Schedule: Vendredi-Samedi 9h-15h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign16_wolof.mp3',2);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(17, 'NOT_STARTED', '2025-02-10T08:00:00Z','2025-02-10T08:00:00Z','2025-04-02T10:00:00Z', '77 999 00 11', 'Title: Don de Sang Intergénérationnel | Description: Une campagne qui unit les générations pour donner ensemble | Location: Centre Culturel Blaise Senghor | Schedule: Samedi 10h-17h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign17_wolof.mp3',4);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(18, 'CANCELLED', '2025-02-10T08:00:00Z','2025-02-10T08:00:00Z','2025-02-02T09:00:00Z', '77 000 11 22', 'Title: Don de Sang Touba | Description: Collecte à Touba, collaboration avec les autorités religieuses | Location: Grande Mosquée de Touba | Schedule: Dimanche 14h-18h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign18_wolof.mp3',3);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(19, 'IN_PROGRESS','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-02-25T08:30:00Z', '77 112 23 34', 'Title: Don de Sang Médina | Description: Collecte de proximité dans le quartier de la Médina | Location: Centre de Santé Gaspard Kamara | Schedule: Lundi-Mardi 9h-16h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign19_wolof.mp3',6);

INSERT INTO blood_donation_campaign
(id, status, created_at,start_at,end_at, contact, campaign_details, image, wolof_audio_description,address_id)
VALUES
(20, 'IN_PROGRESS','2025-02-10T08:00:00Z','2025-02-10T08:00:00Z', '2025-03-12T09:00:00Z', '77 345 67 89', 'Title: Don de Sang Fann | Description: Campagne organisée pour répondre aux besoins de l''hôpital | Location: Hôpital Fann, Dakar | Schedule: Mercredi-Jeudi 9h-17h', 'https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg', 'https://lifelink.sn/audio/campaign20_wolof.mp3',2);


INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
    (1, 'Hôpital Fann', '33 869 81 81', 'Centre de Transfusion Sanguine de l''Hôpital Fann. Ouvert tous les jours de 8h à 16h.', 'https://lifelink.sn/audio/center_1_wolof.mp3', 1);


INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(2, 'Hôpital Principal de Dakar', '33 839 50 50', 'Service de don du sang de l''Hôpital Principal. Ouvert du lundi au vendredi de 8h à 17h.', 'https://lifelink.sn/audio/center_2_wolof.mp3', 2);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(3, 'Hôpital Abass Ndao', '33 821 24 82', 'Centre de collecte de sang. Ouvert du lundi au samedi de 9h à 15h.', 'https://lifelink.sn/audio/center_3_wolof.mp3', 3);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(4, 'Centre Hospitalier de Thiès', '33 951 14 14', 'Centre régional de don de sang de Thiès. Ouvert du lundi au vendredi de 8h à 16h.', 'https://lifelink.sn/audio/center_4_wolof.mp3', 4);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(5, 'Hôpital de Saint-Louis', '33 961 12 32', 'Service de transfusion sanguine de l''hôpital régional. Ouvert tous les jours de 8h à 14h.', 'https://lifelink.sn/audio/center_5_wolof.mp3', 5);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(6, 'Hôpital Militaire de Ouakam', '33 860 31 31', 'Centre de don du sang de l''hôpital militaire. Ouvert du lundi au vendredi de 8h à 16h.', 'https://lifelink.sn/audio/center_6_wolof.mp3', 6);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(7, 'Hôpital Régional de Kaolack', '33 941 20 64', 'Centre de don de sang de Kaolack. Ouvert du lundi au samedi de 8h à 15h.', 'https://lifelink.sn/audio/center_7_wolof.mp3', 7);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(8, 'Centre de Santé de Mbour', '33 957 10 20', 'Service de collecte de sang. Ouvert du lundi au vendredi de 9h à 16h.', 'https://lifelink.sn/audio/center_8_wolof.mp3', 8);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(9, 'Hôpital Régional de Ziguinchor', '33 991 15 22', 'Centre régional de transfusion sanguine. Ouvert du lundi au samedi de 8h à 15h.', 'https://lifelink.sn/audio/center_9_wolof.mp3', 9);

INSERT INTO donation_center
(id, structure_name, contact, center_details, wolof_audio_details, address_id)
VALUES
(10, 'Centre National de Transfusion Sanguine', '33 824 21 78', 'Centre principal de collecte et de distribution de sang pour tout le pays. Ouvert tous les jours de 8h à 18h.', 'https://lifelink.sn/audio/center_10_wolof.mp3', 10);


INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (1, '2025-03-22T09:00:00Z', 'PENDING', 'B', 'POSITIVE', 'Title: Urgence, Besoin de sang B+ | Blood Type: B+ | Hospital: Hôpital Fann | Date: 2025-03-22T09:00:00Z', 'https://lifelink.sn/audio/emergency_1_wolof.mp3', 1);

INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (2, '2025-03-21T14:30:00Z', 'PENDING', 'A', 'POSITIVE', 'Title: Urgence, Besoin de sang A+ | Blood Type: A+ | Hospital: Hôpital Fann | Date: 2025-03-21T14:30:00Z', 'https://lifelink.sn/audio/emergency_2_wolof.mp3', 1);

INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (3, '2025-03-20T10:15:00Z', 'PENDING', 'O', 'NEGATIVE', 'Title: Urgence, Besoin de sang O- | Blood Type: O- | Hospital: Hôpital Fann | Date: 2025-03-20T10:15:00Z', 'https://lifelink.sn/audio/emergency_3_wolof.mp3', 1);

INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (4, '2025-03-19T08:45:00Z', 'PENDING', 'B', 'POSITIVE', 'Title: Urgence, Besoin de sang B+ | Blood Type: B+ | Hospital: Hôpital Principal de Dakar | Date: 2025-03-19T08:45:00Z', 'https://lifelink.sn/audio/emergency_4_wolof.mp3', 2);


    INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (6, '2025-03-17T11:00:00Z', 'PENDING', 'A', 'NEGATIVE', 'Title: Urgence, Besoin de sang A- | Blood Type: A- | Hospital: Centre Hospitalier de Thiès | Date: 2025-03-17T11:00:00Z', 'https://lifelink.sn/audio/emergency_6_wolof.mp3', 4);

INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (7, '2025-03-16T13:45:00Z', 'PENDING', 'O', 'POSITIVE', 'Title: Urgence, Besoin de sang O+ | Blood Type: O+ | Hospital: Hôpital de Saint-Louis | Date: 2025-03-16T13:45:00Z', 'https://lifelink.sn/audio/emergency_7_wolof.mp3', 5);

INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (8, '2025-03-15T09:30:00Z', 'PENDING', 'B', 'NEGATIVE', 'Title: Urgence, Besoin de sang B- | Blood Type: B- | Hospital: Hôpital Militaire de Ouakam | Date: 2025-03-15T09:30:00Z', 'https://lifelink.sn/audio/emergency_8_wolof.mp3', 6);

INSERT INTO blood_request
(id, created_at, status, blood_type, resus_type, details, wolof_audio_details, donation_center_id)
VALUES
    (9, '2025-03-14T15:15:00Z', 'PENDING', 'A', 'POSITIVE', 'Title: Urgence, Besoin de sang A+ | Blood Type: A+ | Hospital: Hôpital Fann | Date: 2025-03-14T15:15:00Z', 'https://lifelink.sn/audio/emergency_9_wolof.mp3', 1);



-- Insertion de données pour la table donor
INSERT INTO donor (
    id,
    first_name,
    last_name,
    phone_number,
    picture,
    gender,
    birth_date,
    email,
    password,
    fidelity_points,
    blood_type,
    resus_type,
    receive_otp_for_blood_request,
    last_donation_date,
    medical_data
) VALUES
      ('10', 'Abdoulkarim', 'Seck', '1234567890',
       'https://media.istockphoto.com/id/1262964438/photo/success-happens-the-moment-you-believe-it-will.jpg?s=612x612&w=0&k=20&c=tpjbR4aaaiB43sneEWgatyFIQOmN3E-3nB5CBE5Idyg=',
       'MALE', '1990-05-15', 'abdoulkarim.seck@example.com', 'hashedpassword1',
       100, 'O', 'POSITIVE', FALSE, '2024-01-10',
       '{"infosMedicales":{"groupeSanguin":"O+","allergies":["Pénicilline","Arachides"],"antecedentsMedicaux":["Hypertension","Diabète"],"medicamentsActuels":["Metformine","Lisinopril"],"conditionsChronic":["Diabète","Hypertension"]},"vaccinations":[{"vaccin":"COVID-19","date":"15/06/2021","dose":"2"},{"vaccin":"Grippe","date":"10/10/2023","dose":"1"}],"consultations":[{"date":"01/10/2023","medecin":"Dr. Martin","diagnostic":"Hypertension","traitement":"Lisinopril 10mg par jour"},{"date":"15/11/2023","medecin":"Dr. Petit","diagnostic":"Diabète de type 2","traitement":"Metformine 500mg deux fois par jour"}],"contactsUrgence":[{"nom":"Zaynab","relation":"Épouse","telephone":"+221774567544"}],"assurance":{"fournisseur":"Assurance XYZ","numeroPolicee":"123456789","couverture":"Complète"},"modeDeVie":{"tabagisme":"Non-fumeur","alcool":"Non","activitePhysique":"Modérée","regime":"Végétarien"},"appareilsMedicaux":[{"appareil":"Stimulateur cardiaque","dateImplantation":"10/05/2020"}],"donOrganes":{"statut":"Non-consentement"},"donSanguin":{"eligibilite":"Éligible","historiqueDons":[{"date":"01/10/2023","volume_ml":450,"centre":"Banque de Sang de Dakar","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"Repos et hydratation"}]},"journauxAcces":[{"horodatage":"05/10/2023 10:00:00","accessiblePar":"Dr. Martin","objectif":"Consultation"}],"consentements":[{"type":"Partage de données","statut":"Accordé","date":"01/01/2023"},{"type":"Don de sang","statut":"Accordé","date":"01/01/2023"}],"alertes":[{"type":"Allergie","description":"Allergie sévère à la Pénicilline"}]}'::jsonb),

      ('20', 'Khadija', 'Mbaye', '0987654321',
       'https://images.unsplash.com/photo-1530785602389-07594beb8b73?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8YWZyaWNhbiUyMHdvbWFufGVufDB8fDB8fHww',
       'FEMALE', '1985-09-22', 'khadija.mbaye@example.com', 'hashedpassword2',
       150, 'A', 'NEGATIVE', TRUE, '2023-12-05',
       '{"infosMedicales":{"groupeSanguin":"A-","allergies":[],"antecedentsMedicaux":["Appendicectomie (2015)"],"medicamentsActuels":[],"conditionsChronic":[]},"vaccinations":[{"vaccin":"COVID-19","date":"05/05/2021","dose":"2"},{"vaccin":"Tétanos","date":"12/06/2023","dose":"Rappel"},{"vaccin":"Fièvre jaune","date":"20/04/2018","dose":"1"}],"consultations":[{"date":"12/06/2023","medecin":"Dr. Faye","diagnostic":"Bilan de santé général","traitement":"Rappel vaccin tétanos"}],"contactsUrgence":[{"nom":"Mouhamed Dieng","relation":"Conjointe","telephone":"+221762345678"},{"nom":"Cheikh Diallo","relation":"Ami","telephone":"+221778901234"}],"assurance":{"fournisseur":"SoliSanté","numeroPolicee":"SOL-456789","couverture":"Premium"},"modeDeVie":{"tabagisme":"Ancien fumeur (arrêt il y a 5 ans)","alcool":"Modéré","activitePhysique":"Régulière","regime":"Normal"},"appareilsMedicaux":[],"donOrganes":{"statut":"Consentement"},"donSanguin":{"eligibilite":"Éligible","historiqueDons":[{"date":"10/03/2022","volume_ml":450,"centre":"Hôpital Principal de Dakar","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"RAS"},{"date":"15/06/2022","volume_ml":450,"centre":"Centre National de Transfusion Sanguine","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"RAS"},{"date":"20/09/2022","volume_ml":450,"centre":"Clinique mobile de don","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"RAS"},{"date":"25/12/2022","volume_ml":450,"centre":"Hôpital Principal de Dakar","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"RAS"},{"date":"30/03/2023","volume_ml":450,"centre":"Centre National de Transfusion Sanguine","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"RAS"},{"date":"05/07/2023","volume_ml":450,"centre":"Clinique mobile de don","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"RAS"},{"date":"10/11/2023","volume_ml":450,"centre":"Hôpital Principal de Dakar","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"RAS"}]},"journauxAcces":[{"horodatage":"10/11/2023 11:15:00","accessiblePar":"Dr. Sall","objectif":"Don de sang"},{"horodatage":"12/06/2023 09:30:00","accessiblePar":"Dr. Faye","objectif":"Consultation de routine"}],"consentements":[{"type":"Partage de données","statut":"Accordé","date":"01/01/2023"},{"type":"Don de sang","statut":"Accordé","date":"01/01/2022"},{"type":"Don d''organes","statut":"Accordé","date":"01/01/2022"},{"type":"Participation recherche médicale","statut":"Accordé","date":"10/06/2023"}],"alertes":[]}'::jsonb),

      ('30', 'Ameth', 'Ba', '1122334445',
       'https://t3.ftcdn.net/jpg/01/21/69/76/360_F_121697618_GLezMEDaZAitrmSAVrLUmsxufmWR6LaC.jpg',
       'MALE', '1993-03-30', 'ameth.ba@example.com', 'hashedpassword3',
       200, 'B', 'POSITIVE', FALSE, '2024-02-20',
       '{"infosMedicales":{"groupeSanguin":"B+","allergies":["Pénicilline","Arachides"],"antecedentsMedicaux":["Hypertension","Diabète"],"medicamentsActuels":["Metformine","Lisinopril"],"conditionsChronic":["Diabète","Hypertension"]},"vaccinations":[{"vaccin":"COVID-19","date":"15/06/2021","dose":"2"},{"vaccin":"Grippe","date":"10/10/2023","dose":"1"}],"consultations":[{"date":"01/10/2023","medecin":"Dr. Martin","diagnostic":"Hypertension","traitement":"Lisinopril 10mg par jour"},{"date":"15/11/2023","medecin":"Dr. Petit","diagnostic":"Diabète de type 2","traitement":"Metformine 500mg deux fois par jour"}],"contactsUrgence":[{"nom":"Aminata Seye","relation":"Épouse","telephone":"+221774567548"}],"assurance":{"fournisseur":"Assurance XYZ","numeroPolicee":"123456789","couverture":"Complète"},"modeDeVie":{"tabagisme":"Non-fumeur","alcool":"Non","activitePhysique":"Modérée","regime":"Végétarien"},"appareilsMedicaux":[{"appareil":"Stimulateur cardiaque","dateImplantation":"10/05/2020"}],"donOrganes":{"statut":"Non-consentement"},"donSanguin":{"eligibilite":"Éligible","historiqueDons":[{"date":"01/10/2023","volume_ml":450,"centre":"Banque de Sang de Dakar","statut":"Testé et Approuvé","tests":[{"test":"VIH","resultat":"Négatif"},{"test":"Hépatite B","resultat":"Négatif"}],"reactionsPostDon":"Aucune","conseils":"Repos et hydratation"}]},"journauxAcces":[{"horodatage":"05/10/2023 10:00:00","accessiblePar":"Dr. Martin","objectif":"Consultation"}],"consentements":[{"type":"Partage de données","statut":"Accordé","date":"01/01/2023"},{"type":"Don de sang","statut":"Accordé","date":"01/01/2023"}],"alertes":[{"type":"Allergie","description":"Allergie sévère à la Pénicilline"}]}'::jsonb)
ON CONFLICT (id) DO NOTHING;

-- Articles normaux (OTHER)
INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (1, 'Importance du don de sang régulier',
     'https://th.bing.com/th/id/R.b72f6de8b5463290402e96c35a3d88a6?rik=kEUfVc11ix2tjg&pid=ImgRaw&r=0',
     'Le don de sang régulier est crucial pour maintenir des réserves suffisantes dans les banques de sang. Chaque jour, des centaines de patients ont besoin de transfusions sanguines pour survivre à des interventions chirurgicales, des accidents, ou pour traiter des maladies comme l''anémie ou la leucémie.

     Un donneur peut donner son sang tous les 8 semaines sans risque pour sa santé. Cette fréquence permet au corps de régénérer complètement les cellules sanguines prélevées.

     Devenir donneur régulier présente plusieurs avantages :
     - Vous contribuez à sauver jusqu''à trois vies à chaque don
     - Vous bénéficiez d''un suivi médical régulier
     - Vous participez activement à la solidarité communautaire

     N''attendez pas qu''une catastrophe se produise pour donner votre sang. Les besoins sont constants et votre contribution est précieuse en tout temps.',
     'https://lifelink.sn/audio/article_1_wolof.mp3',
     '2025-03-15T10:00:00Z',
     'OTHER',
     '10');

INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (2, 'Les différents groupes sanguins et leur compatibilité',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'Le système ABO classe le sang en quatre groupes principaux : A, B, AB et O. Chaque groupe peut être Rhésus positif (+) ou négatif (-), ce qui donne huit groupes sanguins possibles.

     La compatibilité entre donneurs et receveurs est essentielle pour les transfusions :
     - Le groupe O- est donneur universel, mais ne peut recevoir que du O-
     - Le groupe AB+ est receveur universel, mais ne peut donner qu''aux AB+
     - Les personnes du groupe A peuvent recevoir du A et du O
     - Les personnes du groupe B peuvent recevoir du B et du O

     Connaître votre groupe sanguin est important non seulement pour les transfusions mais aussi pour comprendre quelles personnes pourraient bénéficier de votre don.

     Le groupe O+ est le plus courant en Afrique de l''Ouest, tandis que le AB- est le plus rare. Cette distribution varie selon les régions et les populations.',
     'https://lifelink.sn/audio/article_2_wolof.mp3',
     '2025-03-10T14:30:00Z',
     'OTHER',
     '20');

INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (3, 'Mythes et réalités sur le don de sang',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'De nombreux mythes persistent autour du don de sang, ce qui peut dissuader des donneurs potentiels. Voici quelques idées reçues et les faits qui les contredisent :

     MYTHE : Donner son sang est douloureux.
     RÉALITÉ : La plupart des donneurs ne ressentent qu''une légère piqûre au moment de l''insertion de l''aiguille, similaire à une prise de sang ordinaire.

     MYTHE : Donner son sang fait grossir ou maigrir.
     RÉALITÉ : Le don de sang n''a aucun impact sur votre poids. Votre corps remplace rapidement le volume sanguin prélevé.

     MYTHE : Le don de sang affaiblit le corps.
     RÉALITÉ : Le corps humain contient environ 5 litres de sang et un don ne représente que 450 ml. Cette quantité est facilement régénérée sans affecter votre force ou votre énergie.

     MYTHE : On peut attraper des maladies en donnant son sang.
     RÉALITÉ : Tout le matériel utilisé est stérile et à usage unique, rendant impossible la transmission de maladies.

     La sensibilisation est essentielle pour encourager plus de personnes à devenir donneurs et ainsi sauver des vies.',
     'https://lifelink.sn/audio/article_3_wolof.mp3',
     '2025-03-05T09:15:00Z',
     'OTHER',
     '10');

INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (4, 'Comment se préparer pour un don de sang',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'Une bonne préparation avant votre don de sang peut améliorer votre expérience et garantir que votre don soit utilisable. Voici quelques conseils essentiels :

     1. Hydratez-vous bien : Buvez au moins 2 litres d''eau dans les 24 heures précédant votre don.

     2. Mangez un repas équilibré : Consommez un repas riche en fer (viande, légumes verts) et en glucides dans les heures précédant votre don.

     3. Évitez certains aliments : Limitez la consommation d''aliments gras et d''alcool avant votre don.

     4. Reposez-vous suffisamment : Assurez-vous d''avoir eu une bonne nuit de sommeil.

     5. Apportez une pièce d''identité et, si possible, votre carte de donneur si vous en avez une.

     6. Portez des vêtements confortables avec des manches qui peuvent être facilement relevées.

     Après le don, restez assis pendant 10-15 minutes, hydratez-vous et mangez une collation légère. Évitez les efforts physiques intenses pendant 24 heures.',
     'https://lifelink.sn/audio/article_4_wolof.mp3',
     '2025-02-28T16:45:00Z',
     'OTHER',
     '30');

INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (5, 'Les besoins spécifiques en sang au Sénégal',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'Le Sénégal, comme de nombreux pays d''Afrique, fait face à des défis spécifiques concernant l''approvisionnement en sang. Les maladies comme le paludisme, l''anémie falciforme et les complications liées à la grossesse créent des besoins constants en produits sanguins.

     Les statistiques montrent que le pays a besoin d''environ 150,000 poches de sang par an, mais les dons ne couvrent actuellement que 60% de ces besoins. Cette pénurie a des conséquences graves, particulièrement dans les zones rurales où l''accès aux services de transfusion est limité.

     La sensibilisation communautaire joue un rôle crucial pour augmenter le nombre de donneurs volontaires. Des initiatives comme les campagnes mobiles de don de sang dans les écoles, les lieux de travail et les mosquées ont montré des résultats prometteurs.

     Le groupe sanguin O+ est le plus courant au Sénégal, mais les groupes rhésus négatifs sont particulièrement recherchés en raison de leur rareté et de leur compatibilité élevée.',
     'https://lifelink.sn/audio/article_5_wolof.mp3',
     '2025-02-20T11:30:00Z',
     'OTHER',
     '20');

-- Témoignages (TESTIMONY)
INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (6, 'Mon expérience de premier don',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'J''appréhendais mon premier don de sang, ayant toujours eu une légère peur des aiguilles. Mais après avoir appris qu''un proche avait eu besoin d''une transfusion lors d''une intervention chirurgicale, j''ai décidé de franchir le pas.

     À mon arrivée au centre de don, l''équipe médicale m''a accueilli chaleureusement et m''a expliqué chaque étape du processus. L''entretien médical préalable m''a rassuré sur ma capacité à donner et sur la sécurité de la procédure.

     Le prélèvement lui-même a été beaucoup plus rapide et moins inconfortable que je ne l''imaginais – environ 10 minutes où j''ai pu me détendre en écoutant de la musique. La légère sensation de vertige que j''ai ressentie après a vite disparu en prenant la collation offerte par l''équipe.

     Ce qui m''a le plus marqué, c''est le sentiment d''avoir accompli quelque chose d''important. Savoir que mon don pourrait contribuer à sauver jusqu''à trois vies m''a donné une vraie satisfaction. Je suis maintenant un donneur régulier et j''encourage tous ceux qui hésitent encore à faire cette démarche simple mais tellement significative.',
     'https://lifelink.sn/audio/testimony_1_wolof.mp3',
     '2025-03-18T13:20:00Z',
     'TESTIMONY',
     '10');

INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (7, 'Le don qui a changé ma vie',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'Mon histoire avec le don de sang a commencé de l''autre côté de l''équation – en tant que receveur. Suite à un grave accident de la route il y a trois ans, j''ai reçu plusieurs transfusions qui m''ont littéralement sauvé la vie.

     Pendant ma convalescence, je n''ai cessé de penser aux personnes anonymes dont le sang coulait désormais dans mes veines. Des gens qui avaient pris une heure de leur temps pour un geste qui m''a permis de continuer à vivre, à voir mes enfants grandir.

     Dès que mon état de santé me l''a permis, je me suis engagé à devenir donneur régulier pour "rembourser ma dette". Chaque trois mois, je me rends au centre de transfusion et je ressens une immense gratitude en pensant que je contribue peut-être à sauver quelqu''un comme d''autres l''ont fait pour moi.

     Cette expérience m''a aussi poussé à devenir bénévole dans des campagnes de sensibilisation au don de sang. Je partage mon témoignage pour montrer l''impact réel que ce geste simple peut avoir sur des vies humaines.',
     'https://lifelink.sn/audio/testimony_2_wolof.mp3',
     '2025-03-12T09:45:00Z',
     'TESTIMONY',
     '30');

INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (8, 'Donner en famille, une tradition qui sauve des vies',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'Dans ma famille, le don de sang est une véritable tradition qui se transmet de génération en génération. Mon père, infirmier de profession, nous a inculqué l''importance de ce geste dès notre plus jeune âge.

     Quand j''ai eu 18 ans, mon père m''a accompagné pour mon premier don. Ce moment reste gravé dans ma mémoire comme un rite de passage, une façon de concrétiser les valeurs de solidarité et d''entraide qu''il nous avait enseignées.

     Aujourd''hui, nous sommes cinq membres de la famille à donner régulièrement notre sang. Nous en avons même fait une activité familiale : tous les trois mois, nous nous retrouvons au centre de don pour notre "journée solidarité", suivie d''un repas partagé.

     Au fil des années, notre cercle s''est élargi, avec des amis et même des collègues qui se sont joints à nous. Ce qui a commencé comme une tradition familiale est devenu un petit mouvement dans notre communauté, démontrant que l''exemple peut être contagieux quand il s''agit de faire le bien.',
     'https://lifelink.sn/audio/testimony_3_wolof.mp3',
     '2025-03-05T15:10:00Z',
     'TESTIMONY',
     '20');

INSERT INTO article
(id, title, image, content, wolof_audio_resume, created_at, article_type, donor_id)
VALUES
    (9, 'Comment le don de sang m''a permis de vaincre ma peur des aiguilles',
     'https://th.bing.com/th/id/R.145447e11d13dc04f69f913537262d3a?rik=XRFF2sAAH5S3wQ&pid=ImgRaw&r=0',
     'J''ai longtemps souffert d''une phobie des aiguilles si intense que je pouvais m''évanouir à la simple vue d''une seringue. Cette peur m''a empêché pendant des années de faire un geste qui me tenait pourtant à cœur : donner mon sang.

     Tout a changé lorsque j''ai appris qu''un ami proche souffrant de drépanocytose avait besoin de transfusions régulières. Sa situation m''a donné le courage de confronter ma peur.

     Ma première tentative s''est soldée par un échec – j''ai paniqué au dernier moment. Mais l''équipe médicale, habituée à ce type de situation, a été d''un soutien incroyable. Ils m''ont proposé une approche progressive : d''abord visiter le centre sans donner, puis essayer une simple prise de sang, et enfin tenter un don complet avec des techniques de relaxation.

     Après trois visites, j''ai réussi à faire mon premier don complet. La fierté ressentie ce jour-là a largement dépassé la peur. Aujourd''hui, je ne dirai pas que ma phobie a totalement disparu, mais elle ne me contrôle plus. Chaque don est une petite victoire personnelle doublée d''un geste utile pour les autres.',
     'https://lifelink.sn/audio/testimony_4_wolof.mp3',
     '2025-02-25T17:30:00Z',
     'TESTIMONY',
     '20');
