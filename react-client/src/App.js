import React, { useState, useEffect } from "react";
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import {BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import FooterComponent from './components/FooterComponent';
import  HeaderComponent  from './components/HeaderComponent';
import ProductList from './pages/products/ProductList';
import ProductEntry from './pages/products/ProductEntry';

import AuthService from "./services/AuthService";

import Login from "./components/Login";
import Register from "./components/Register";
//import Home from "./components/Home";
import Profile from "./components/Profile";

function App() {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    const user = AuthService.getCurrentUser();

    if (user) {
      setCurrentUser(user);
    }
  }, []);

  const logOut = () => {
    AuthService.logout();
  };

  return (
    <div>
      <BrowserRouter>
        <HeaderComponent user = { currentUser } logOut = { logOut } />
        <div className= "container">
          <Routes>
            <Route path = "/" element = { <ProductList user = { currentUser } /> }></Route>
            <Route path = "/login" element = { <Login/> } />
            <Route path = "/register" element = { <Register/> } />
            <Route path = "/profile" element = { <Profile/> } />
            <Route path = "/products" element = { <ProductList user = { currentUser } /> }></Route>
            <Route path = "/add-product" element = { <ProductEntry />} ></Route>
            <Route path = "/edit-product/:id" element = { <ProductEntry />}></Route>
          </Routes>
        </div>
        <FooterComponent />
        </BrowserRouter>
    </div>
  );
}

export default App;