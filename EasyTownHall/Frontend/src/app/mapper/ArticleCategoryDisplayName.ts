import { ArticleCategory } from "../models/enums/articleCategory";

export const ArticleCategoryDisplayName: { [key in ArticleCategory]: string } = {
    [ArticleCategory.GENERAL_ANNOUNCEMENTS]: 'Allgemeine Ankündigungen',
    [ArticleCategory.EVENTS]: 'Veranstaltungen',
    [ArticleCategory.CONSTRUCTION_AND_TRAFFIC]: 'Bau- und Verkehrsprojekte',
    [ArticleCategory.CITIZEN_PARTICIPATION]: 'Bürgerbeteiligung',
    [ArticleCategory.PUBLIC_SAFETY]: 'Öffentliche Sicherheit',
    [ArticleCategory.EDUCATION_AND_CULTURE]: 'Bildung und Kultur',
    [ArticleCategory.ENVIRONMENT_AND_SUSTAINABILITY]: 'Umwelt und Nachhaltigkeit',
    [ArticleCategory.SOCIAL_AND_HEALTH]: 'Soziales und Gesundheit',
    [ArticleCategory.ECONOMY_AND_BUSINESS]: 'Wirtschaft und Gewerbe',
    [ArticleCategory.LEISURE_AND_SPORTS]: 'Freizeit und Sport',
  };
  