import {inject} from "inversify";
import {types} from "../../../shared/di/modules.ts";
import type {AxiosInstance} from "axios";
import type {Product} from "../../../entities/Product.ts";

export class CatalogueClient{
    constructor(
        @inject(types.AxiosInstance)
        private readonly client: AxiosInstance
    ) {}

    BASE_URL = "http://127.0.0.1:8080/catalogue"

    async getAllProducts(): Promise<Product[]>{
        const response =
            await this.client.get<Promise<Product[]>>(this.BASE_URL)
        return response.data
    }

    async getProductById(id: string): Promise<Product>{
        const response =
            await this.client.get<Promise<Product>>(`${this.BASE_URL}/${id}`)
        return response.data
    }
}