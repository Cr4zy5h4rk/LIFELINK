export enum CampaignStatus {
  NOT_STARTED = 'NOT_STARTED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}

export interface CampaignDTO {
  id?: number;
  title: string;
  description: string;
  imageUrl: string;
  startDate: string; // Format ISO
  endDate: string; // Format ISO
  location: string;
  contactPhone: string;
  schedule: string;
  status: CampaignStatus;
}

// "id": 1,
//         "title": "Campage de collecte de sang",
//         "description": "https://lifelink.sn/audio/campaign1_wolof.mp3",
//         "imageUrl": "https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg",
//         "startDate": "2025-02-10T08:00:00Z",
//         "endDate": "2025-02-10T08:00:00Z",
//         "location": "Dakar, Dakar Plateau",
//         "contactPhone": "77 123 45 67",
//         "schedule": "Title: Campagne Don de Sang Dakar Centre | Description: Donnez votre sang, sauvez des vies! Nous avons besoin de donneurs de tout groupe sanguin | Location: Centre National de Transfusion Sanguine, Dakar | Schedule: Lundi-Vendredi 9h-17h, Samedi 9h-12h",
//         "status": "IN_PROGRESS"
//     },
//     {
//         "id": 2,
//         "title": "Campage de collecte de sang",
//         "description": "https://lifelink.sn/audio/campaign2_wolof.mp3",
//         "imageUrl": "https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg",
//         "startDate": "2025-02-10T08:00:00Z",
//         "endDate": "2025-02-10T08:00:00Z",
//         "location": "Saint-Louis, Saint-Louis",
//         "contactPhone": "77 987 65 43",
//         "schedule": "Title: Don de Sang Urgence Hôpital Principal | Description: Urgence pour groupes O+ et O-. Votre don peut sauver des vies maintenant! | Location: Hôpital Principal de Dakar | Schedule: Tous les jours 8h-18h",
//         "status": "IN_PROGRESS"
//     },
//     {
//         "id": 3,
//         "title": "Campage de collecte de sang",
//         "description": "https://lifelink.sn/audio/campaign3_wolof.mp3",
//         "imageUrl": "https://cff2.earth.com/uploads/2022/07/07074909/Blood-scaled.jpg",
//         "startDate": "2025-02-10T08:00:00Z",
//         "endDate": "2025-01-12T10:00:00Z",
//         "location": "Diourbel, Touba",
//         "contactPhone": "78 123 45 67",
//         "schedule": "Title: Journée du Don - Université Cheikh Anta Diop | Description: Grande collecte de sang sur le campus, venez nombreux! | Location: UCAD, Amphithéâtre principal | Schedule: Mercredi 10h-16h",
//         "status": "COMPLETED"
//     },
