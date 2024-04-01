import './App.css';
import {BrowserRouter, Routes, Route } from 'react-router-dom';
import FooterComponent from './components/FooterComponent';
import  HeaderComponent  from './components/HeaderComponent';
import ProductList from './pages/products/ProductList';
import ProductEntry from './pages/products/ProductEntry';
import PostList from './pages/posts/PostList';
import PostEntry from './pages/posts/PostEntry';

function App() {
  return (
    <div>
      <BrowserRouter>
        <HeaderComponent />
        <div className= "container">
          <Routes>
              <Route path = "/" element = { <ProductList /> }></Route>
              <Route path = "/products" element = { <ProductList /> }></Route>
              <Route path = "/add-product" element = { <ProductEntry />} ></Route>
              <Route path = "/edit-product/:id" element = { <ProductEntry />}></Route>
              <Route path = "/posts" element = { <PostList /> }></Route>
              <Route path = "/add-post" element = { <PostEntry />} ></Route>
              <Route path = "/edit-post/:id" element = { <PostEntry />}></Route>
            </Routes>
        </div>
        <FooterComponent />
        </BrowserRouter>
    </div>
  );
}

export default App;