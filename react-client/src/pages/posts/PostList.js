import React, { useState, useEffect, useMemo } from 'react'
import { useNavigate } from 'react-router-dom'
import { useTable } from "react-table";
import Box from "@mui/material/Box";
import { Pagination } from "@mui/material";
import Icon from '@mui/material/Icon';
import { listPosts, deletePost } from '../../services/PostService'

const PostList = () => {

    const [searchText, setSearchText] = useState("");
    const [posts, setPosts] = useState([])

    const navigate = useNavigate()
    const pageSize = 5;

    const [page, setPage] = useState(1);
    const [count, setCount] = useState(0);
    const handlePageChange = (event, value) => {
        setPage(value);
    };
    const [sort, setSort] = useState("id");
    const [dir, setDir] = useState("ASC");
    const handleSortChange = (accessor) => {
        const sortDir = accessor === sort && dir === "ASC" ? "DESC" : "ASC";
        setDir(sortDir);
        setSort(accessor);
    };

    useEffect(() => {
        getAllPosts();
    }, [page, pageSize, sort, dir])

    const onChangeSearchText = (e) => {
        const searchText = e.target.value;
        setSearchText(searchText);
    };

    const getRequestParams = (searchText, page, pageSize, sort, dir) => {
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
        if (sort) {
            params["sort"] = sort;
        }
        if (dir) {
            params["dir"] = dir;
        }
        return params;
    };

    const getAllPosts = () => {
        const params = getRequestParams(searchText, page, pageSize, sort, dir);
        listPosts(params).then((response) => {
            const { content, totalPages } = response.data;
            setPosts(content);
            setCount(totalPages);
            console.log(response.data);
        }).catch(error =>{
            console.log(error);
        })
    }

    const removePost = (postId) => {
        deletePost(postId).then((response) =>{
            getAllPosts();
        }).catch(error =>{
            console.log(error);
        })
    }

    function addNewPost() {
        navigate('/add-post')
    }

    const updatePost = (id) => {
        navigate(`/edit-post/${id}`)
    }

    function destroy(id, name) {
        if (window.confirm("Are you sure you want to delete this post: " + id + " - " + name + "?")) {
            removePost(id);
        }
    }

    const findByText = () => {
        setPage(0);
        getAllPosts();
    };

    const UsFormatter = new Intl.DateTimeFormat('en-US', { dateStyle: 'full', timeStyle: 'long' })

    const columns = useMemo(
        () => [ {
                Header: "Id",
                accessor: "id",
            }, {
                Header: "User",
                accessor: "user.name",
            }, {
                Header: "Title",
                accessor: "title",
                Cell: (props) => {
                    return (
                        <div dangerouslySetInnerHTML = {{ __html: props.value }} />
                    );
                },
            }, {
                Header: "Content",
                accessor: "content",
                Cell: (props) => {
                     return (
                         <div dangerouslySetInnerHTML = {{ __html: props.value }} />
                     );
                },
            }, {
                Header: "Published",
                accessor: "published",
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
                            <button className="btn btn-sm btn-info" onClick={() => updatePost(id)}>
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
        data: posts,
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
            <h2 className = "text-center"> List Posts </h2>
            <button className = "btn btn-primary mb-2" onClick={addNewPost }>Add Post</button>
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
                                        { column.id === "actions" || column.id === "description" ? (
                                            <span>{ column.Header }</span>
                                        ) : (
                                            <span className='text-nowrap' onClick={() => handleSortChange(column.id)}>
                                                { column.Header }
                                                <Icon className='text-secondary'>{ column.id !== sort ?
                                                    "swap_vert" : dir === "ASC" ? "arrow_drop_up" : "arrow_drop_down"
                                                    //"unfold_more" : dir === "ASC" ? "expand_more" : "expand_less"
                                                }</Icon>
                                            </span>
                                        )}
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

export default PostList