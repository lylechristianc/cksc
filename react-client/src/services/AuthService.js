import httpClient from "./HttpCommon";

const register = (username, loginName, password) => {
  return httpClient.post("/auth/signup", {
    username,
    loginName,
    password,
  });
};

const login = (username, password) => {
  return httpClient.post("/auth/signin", {
      username,
      password,
    })
    .then((response) => {
      if (response.data.accessToken) {
        localStorage.setItem("user", JSON.stringify(response.data));
      }
      return response.data;
    });
};

const logout = () => {
  localStorage.removeItem("user");
};

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const AuthService = {
  register,
  login,
  logout,
  getCurrentUser,
};

export default AuthService;