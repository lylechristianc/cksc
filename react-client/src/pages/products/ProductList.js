import React, {useState, useEffect} from 'react'
import { useNavigate } from 'react-router-dom'
import {listProducts, deleteProduct} from '../../services/ProductService'

const ProductList = () => {

    const [products, setProducts] = useState([])

    const navigate = useNavigate()

    useEffect(() => {
        getAllProducts();
    }, [])

    const getAllProducts = () => {
        listProducts().then((response) => {
            setProducts(response.data)
            console.log(response.data);
        }).catch(error =>{
            console.log(error);
        })
    }

    const removeProduct = (productId) => {
        deleteProduct(productId).then((response) =>{
            getAllProducts();
        }).catch(error =>{
            console.log(error);
        })
    }

    function addNewProduct() {
        navigate('/add-product')
    }

    const updateProduct = (id) => {
        navigate(`/edit-product/${id}`)
    }

    return (
        <div className = "container">
            <h2 className = "text-center"> List Products </h2>
             <button className = "btn btn-primary mb-2" onClick={addNewProduct }>Add Product</button>
            <table className="table table-bordered table-striped">
                <thead>
                    <th> Id </th>
                    <th> Name </th>
                    <th> Description </th>
                    <th> Price </th>
                    <th> Actions </th>
                </thead>
                <tbody>
                    {
                        products.map(
                            product =>
                            <tr key = {product.id}>
                                <td> {product.id} </td>
                                <td> {product.name} </td>
                                <td> {product.description} </td>
                                <td>{product.price}</td>
                                <td>
                                    <button className="btn btn-info" onClick={() => updateProduct(product.id)} >Update</button>
                                    <button className = "btn btn-danger" onClick = {() => removeProduct(product.id)}
                                    style = {{marginLeft:"10px"}}> Delete</button>
                                </td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ProductList