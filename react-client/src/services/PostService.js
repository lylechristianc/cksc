import httpClient from "./HttpCommon";

export const listPosts = (params) => {
    return httpClient.get('/posts', { params })
};

export const createPost = (post) => {
    return httpClient.post('/posts', post)
}

export const getPostById = (postId) => {
    return httpClient.get('/posts/' + postId);
}

export const updatePost = (postId, post) => {
    return httpClient.put('/posts/' + postId, post);
}

export const deletePost = (postId) => {
    return httpClient.delete('/posts/' + postId);
}