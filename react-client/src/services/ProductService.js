import httpClient from "./HttpCommon";
import authHeader from "./AuthHeader";

export const listProducts = (params) => {
    return httpClient.get('/products', { params })
};

export const createProduct = (product) => {
    return httpClient.post('/products', product, { headers: authHeader() });
}

export const getProductById = (productId) => {
    return httpClient.get('/products/' + productId);
}

export const updateProduct = (productId, product) => {
    return httpClient.post('/products/' + productId, product, {
        headers: {
            "Content-Type": "multipart/form-data", ...authHeader()
        }
    });
}

export const deleteProduct = (productId) => {
    return httpClient.delete('/products/' + productId, { headers: authHeader() });
}