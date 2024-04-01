import React, { useState, useEffect, useRef } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import { Editor } from '@tinymce/tinymce-react';
import { updatePost, createPost, getPostById } from '../../services/PostService';

const PostEntry = () => {

    const editorRef = useRef(null);
    const log = () => {
        if (editorRef.current) {
            console.log(editorRef.current.getContent());
        }
    };

    const [ title, setTitle ] = useState('')
    const [ content, setContent ] = useState('')
    const [ published, setPublished ] = useState('')

    const navigate = useNavigate();
    const { id } = useParams();

    const [ error, setError ] = useState({})
    const [ valErrors, setValErrors ] = useState([])

    const saveOrUpdatePost = (e) => {
        e.preventDefault();

        const post = { title, content, published }

        console.log(post);
        if (id) {
            updatePost(id, post).then((response) => {
                navigate('/posts')
            }).catch (error => {
                console.log(error)
                setError(error)
                let errors = error?.response?.data["validation-errors"]
                setValErrors(errors ?? [])
            })
        } else {
            createPost(post).then((response) => {
                console.log(response.data)
                navigate('/posts');
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
            getPostById(id).then((response) => {
                setTitle(response.data.title)
                setContent(response.data.content)
                setPublished(response.data.published)
            }).catch (error => {
                console.log(error)
            })
        }

    }, [id])

    const pageTitle = () => {
        if(id){
            return <h2 className = "text-center">Update Post</h2>
        }else{
            return <h2 className = "text-center">Add Post</h2>
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
                                    <label className = "form-label"> Title : <span className="text-danger">{ valErrors.title }</span></label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter name"
                                        name = "title"
                                        className = { "form-control" + (valErrors.title ? " is-invalid" : "") }
                                        value = { title }
                                        onChange = {(e) => setTitle(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Content : <span className="text-danger">{ valErrors.content }</span></label>
                                    <Editor
                                        tinymceScriptSrc = { '/tinymce/tinymce.min.js' }
                                        onInit = { (evt, editor) => editorRef.current = editor }
                                        name = "content"
                                        className = { "form-control" + (valErrors.content ? " is-invalid" : "") }
                                        value = { content }
                                        onEditorChange = { (content) => setContent(content) }
                                        init= {{
                                            height: 500,
                                            menubar: false,
                                            plugins: [
                                                'advlist', 'autolink', 'lists', 'link', 'image', 'charmap',
                                                'anchor', 'searchreplace', 'visualblocks', 'code', 'fullscreen',
                                                'insertdatetime', 'media', 'table', 'preview', 'help', 'wordcount',
                                                'emoticons'
                                            ],
                                            toolbar: 'undo redo | blocks | ' +
                                                'bold italic forecolor backcolor emoticons | alignleft aligncenter ' +
                                                'alignright alignjustify | bullist numlist outdent indent | ' +
                                                'removeformat | help',
                                            content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
                                        }}
                                    />
                                </div>

                                <div className = "form-group mb-2">
                                    <label className = "form-label"> Published : <span className="text-danger">{ valErrors.published }</span></label>
                                    <input
                                        type = "text"
                                        placeholder = "Enter published"
                                        name = "published"
                                        className = { "form-control" + (valErrors.published ? " is-invalid" : "") }
                                        value = { published }
                                        onChange = {(e) => setPublished(e.target.value)}
                                    >
                                    </input>
                                </div>

                                <button className = "btn btn-success" onClick = {(e) => saveOrUpdatePost(e)} >Submit </button>
                                {/* <Link to="/posts" className="btn btn-danger"> Cancel </Link> */}
                            </form>
                        </div>
                    </div>
                </div>
           </div>
        </div>
    )
}

export default PostEntry