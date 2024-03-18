import React, { useState, useEffect, useMemo } from 'react'
import { useNavigate } from 'react-router-dom'
import { useTable } from "react-table";
import Box from "@mui/material/Box";
import { Pagination } from "@mui/material";
import Icon from '@mui/material/Icon';
import { listProducts, deleteProduct } from '../../services/ProductService'

const ProductList = () => {

    const [searchText, setSearchText] = useState("");
    const [products, setProducts] = useState([])

    const navigate = useNavigate()
    const pageSize = 5;

    const [page, setPage] = useState(1);
    const [count, setCount] = useState(0);
    const handlePageChange = (event, value) => {
        setPage(value);
    };

    useEffect(() => {
        getAllProducts();
    }, [page, pageSize])

    const onChangeSearchText = (e) => {
        const searchText = e.target.value;
        setSearchText(searchText);
    };

    const getRequestParams = (searchText, page, pageSize) => {
        let params = {};
        if (searchText) {
            params["query"] = searchText;
        }
        if (page) {
            params["page"] = page - 1;
        }
        if (pageSize) {
            params["size"] = pageSize;
        }
        return params;
    };

    const getAllProducts = () => {
        const params = getRequestParams(searchText, page, pageSize);
        listProducts(params).then((response) => {
            const { content, totalPages } = response.data;
            setProducts(content);
            setCount(totalPages);
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

    function destroy(id, name) {
        if (window.confirm("Are you sure you want to delete this product: " + id + " - " + name + "?")) {
            removeProduct(id);
        }
    }

    const findByText = () => {
        setPage(0);
        getAllProducts();
    };

    const UsFormatter = new Intl.DateTimeFormat('en-US', { dateStyle: 'full', timeStyle: 'long' })

    const columns = useMemo(
        () => [ {
                Header: "Id",
                accessor: "id",
            }, {
                Header: "Name",
                accessor: "name",
            }, {
                Header: "Description",
                accessor: "description",
            }, {
                Header: "Price",
                accessor: "price",
            }, {
                Header: "Date Updated",
                accessor: "updatedAt",
                Cell: (props) => {
                    return UsFormatter.format(new Date(props.value));
                },
            }, {
                Header: "Actions",
                accessor: "actions",
                Cell: (props) => {
                    const { id, name } = props.row.values;
                    //console.log(props.row);
                    return (
                        <div>
                            <button className="btn btn-sm btn-info" onClick={() => updateProduct(id)}>
                                <Icon>edit</Icon>
                            </button>

                            <button className="btn btn-sm btn-danger" onClick={() => destroy(id, name)}>
                                <Icon>delete</Icon>
                            </button>
                        </div>
                    );
                },
            },
        ],
        []
    );

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = useTable({
        columns,
        data: products,
    });

    const Paginator = () => {
        return (
            <div className="col-md-8">
                <Box sx={{
                    margin: "auto",
                    width: "fit-content",
                    alignItems: "center",
                }}>
                    <Pagination count={count} page={page} onChange={handlePageChange} color="secondary" />
                </Box>
            </div>
        );
    }

    return (
        <div className = "container">
            <link
              rel="stylesheet"
              href="https://fonts.googleapis.com/icon?family=Material+Icons"
            />
            <h2 className = "text-center"> List Products </h2>
            <button className = "btn btn-primary mb-2" onClick={addNewProduct }>Add Product</button>
            <div className="col-md-8">
                <div className="input-group mb-3">
                    <input
                        type="text"
                        className="form-control"
                        placeholder="Search by title"
                        value={searchText}
                        onChange={onChangeSearchText}
                    />
                    <div className="input-group-append">
                        <button
                            className="btn btn-outline-secondary"
                            type="button"
                            onClick={findByText}
                        >
                            Search
                        </button>
                    </div>
                </div>
            </div>

            <Paginator />

            <div className="col-md-12 list">
                <table
                    className="table table-striped table-bordered"
                    {...getTableProps()}
                >
                    <thead>
                        {headerGroups.map((headerGroup) => (
                            <tr {...headerGroup.getHeaderGroupProps()}>
                                {headerGroup.headers.map((column) => (
                                    <th {...column.getHeaderProps()}>
                                        {column.render("Header")}
                                    </th>
                                ))}
                            </tr>
                        ))}
                    </thead>
                    <tbody {...getTableBodyProps()}>
                        {rows.map((row, i) => {
                            prepareRow(row);
                            return (
                                <tr {...row.getRowProps()}>
                                    {row.cells.map((cell) => {
                                        return (
                                            <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                                        );
                                    })}
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            </div>

            <Paginator />
        </div>
    )
}

export default ProductList