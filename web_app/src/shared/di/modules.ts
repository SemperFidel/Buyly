import {Container} from 'inversify'
import axios, {type AxiosInstance} from "axios";
export const types = {
    AxiosInstance: Symbol.for("Axios")
}

const container = new Container()

const AxiosClient: AxiosInstance = axios.create({
    baseURL: "https://api.example.com",
    timeout: 5000,
})

container.bind<AxiosInstance>(types.AxiosInstance).toConstantValue(AxiosClient)