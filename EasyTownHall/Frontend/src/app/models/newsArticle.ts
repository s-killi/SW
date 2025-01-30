import { ArticleCategory } from "./enums/articleCategory";
import { TestModel } from "./testModel";

export interface NewsArticle {
    id: number; 
    author: string; 
    title: string;
    content?: string; 
    category: ArticleCategory; 
    createdAt: Date; 
    updatedAt?: Date; 
    published: boolean;
  }