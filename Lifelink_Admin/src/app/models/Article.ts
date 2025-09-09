
// Enum pour les types d'articles
export enum ArticleType {
  TESTIMONY = 'TESTIMONY',
  OTHER = 'OTHER',
}

// Interface principale pour ArticleDTO
export interface ArticleDTO {
  id?: number;
  title?: string;
  image?: string;
  content?: string;
  wolofAudioResume?: string;
  createdAt?: string | Date; // Vous pouvez utiliser string pour les dates ISO
  articleType?: ArticleType;
}