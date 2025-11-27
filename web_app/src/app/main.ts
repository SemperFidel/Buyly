import { createApp } from 'vue'
import './style.css'
import './tailwind.css'
import App from './App.vue'
import {createPinia} from "pinia"
import PrimeVue from 'primevue/config';

const app = createApp(App);
const pinia = createPinia();

app.use(pinia);
app.use(PrimeVue)
app.mount('#app');