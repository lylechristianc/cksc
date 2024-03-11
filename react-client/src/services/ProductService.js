import httpClient from "./HttpCommon";

export const listProducts = () => {
    return httpClient.get('/products')
};

export const createProduct = (product) => {
    return httpClient.post('/products', product)
}

export const getProductById = (productId) => {
    return httpClient.get('/products/' + productId);
}

export const updateProduct = (productId, product) => {
    return httpClient.put('/products/' + productId, product);
}

export const deleteProduct = (productId) => {
    return httpClient.delete('/products/' + productId);
}