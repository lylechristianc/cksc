import React, { useState, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import { updateProduct, createProduct, getProductById } from '../../services/ProductService';

const ProductEntry = () => {

    const [ name, setName ] = useState('')
    const [ description, setDescription ] = useState('')
    const [ price, setPrice ] = useState('')

    const [ error, setError ] = useState({})
    const [ valErrors, setValErrors ] = useState([])

    const navigate = useNavigate();
    const { id } = useParams();

    const saveOrUpdateProduct = (e) => {
        e.preventDefault();

        const product = { name, description, price }

        console.log(product);
        if (id) {
            updateProduct(id, product).then((response) => {
                navigate('/products')
            }).catch (error => {
                console.log(error)
                 setError(error)
                 let errors = error?.response?.data["validation-errors"]
                 setValErrors(errors ?? [])
            })
        } else {
            createProduct(product).then((response) => {
                console.log(response.data)
                navigate('/products');
            }).catch (error => {
                console.log(error)
                 setError(error)
                 let errors = error?.response?.data["validation-errors"]
                 setValErrors(errors ?? [])
            })
        }

    }

    useEffect(() => {
        if (id){
            getProductById(id).then((response) => {
                setName(response.data.name)
                setDescription(response.data.description)
                setPrice(response.data.price)
            }).catch (error => {
                console.log(error)
            })
        }

    }, [id])

    const pageTitle = () => {
        if(id){
            return <h2 className = "text-center">Update Product</h2>
        }else{
            return <h2 className = "text-center">Add Product</h2>
        }
    }

    return (
        <div>
           <br /><br />
           <div className = "container">
                <div className = "row">
                    <div className = "card col-md-6 offset-md-3 offset-md-3">
                       {
                           pageTitle()
                       }
                         {
                                pageTitle()
                         }
                         { error.message && (
                               <>
                               <p className="text-danger ms-3">{ error.message }</p>
                               { Object.entries(valErrors).map( item =>
                                   <p className="text-danger ms-3"><strong>{ item[0] }</strong>{ ': ' + item[1] }</p>
                               )}
                               { error?.response?.data?.errors?.map( item =>
                                    <p className="text-danger ms-3">{ item }</p>
                               )}
                               </>
                         )}
                        <div className = "card-body">
                            <form>
                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Name :<span className="text-danger">{ valErrors.name }</span></label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter name"
                                        name = "name"
                                        className = { "form-control" + (valErrors.name ? " is-invalid" : "") }
                                        value = { name }
                                        onChange = {(e) => setName(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Description : <span className="text-danger">{ valErrors.description }</span></label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter description"
                                        name = "description"
                                        className = { "form-control" + (valErrors.description ? " is-invalid" : "") }
                                        value = { description }
                                        onChange = {(e) => setDescription(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Price :<span className="text-danger">{ valErrors.price }</span></label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter price"
                                        name = "price"
                                        className = { "form-control" + (valErrors.price ? " is-invalid" : "") }
                                        value = { price }
                                        onChange = {(e) => setPrice(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <button className = "btn btn-success" onClick = {(e) => saveOrUpdateProduct(e)} >Submit </button>
                                {/* <Link to="/products" className="btn btn-danger"> Cancel </Link> */}
                            </form>
                        </div>
                    </div>
                </div>
           </div>
        </div>
    )
}

export default ProductEntry