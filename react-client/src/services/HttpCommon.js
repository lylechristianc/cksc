import axios from "axios";

export default axios.create({
    baseURL: process.env.REACT_APP_SPRING_BOOT_BASE_URL + '/api',
    headers: {
        'Content-Type': 'application/json'
    }
});