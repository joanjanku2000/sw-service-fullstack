import { AppService } from "./service.model";

export interface AppServicePage {
    content: AppService[];
    totalElements: number;
    totalPages: number;
    number: number;
}