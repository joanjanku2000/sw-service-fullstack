import {Resource} from "./resource.model";

export interface AppService {
    id?: string;
    resources: Resource[];
    version?: number
}