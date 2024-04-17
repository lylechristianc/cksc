import React, { useState, useRef } from "react";
import { useNavigate } from 'react-router-dom';
import Form from "react-validation/build/form";
import CheckButton from "react-validation/build/button";
import Icon from '@mui/material/Icon';

import AuthService from "../services/AuthService";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

const Login = () => {
  let navigate = useNavigate();

  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const [ error, setError ] = useState({})
  const [ valErrors, setValErrors ] = useState([])

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleLogin = (e) => {
    e.preventDefault();

    setMessage("");
    setLoading(true);

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      AuthService.login(username, password).then(
        () => {
          navigate("/profile");
          window.location.reload();
        },
        (error) => {
          setError(error)
          let errors = error?.response?.data["validation-errors"]
          setValErrors(errors ?? [])
          console.log(error);
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.errors) ||
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          setLoading(false);
          setMessage(resMessage);
        }
      );
    } else {
      setLoading(false);
    }
  };

  return (
    <div className="col-md-12">
      <div className="card card-container">
        <Icon className="profile-img-card text-black-50" style={{ fontSize: 120 }}>account_circle</Icon>
        { error.message && (
            <>
            { error?.response?.data?.errors?.map( item =>
                <p className="text-danger ms-3">{ item }</p>
            )}
            </>
        )}

        <Form onSubmit={handleLogin} ref={form}>
          <div className="form-group">
            <label htmlFor="username">User name or Login name <span className="text-danger">{ valErrors.username }</span></label>
            <input
              type="text"
              className = { "form-control" + (valErrors.username ? " is-invalid" : "") }
              name="username"
              value={username}
              onChange={onChangeUsername}
              validations={[required]}
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password <span className="text-danger">{ valErrors.password }</span></label>
            <input
              type="password"
              className = { "form-control" + (valErrors.password ? " is-invalid" : "") }
              name="password"
              value={password}
              onChange={onChangePassword}
              validations={[required]}
            />
          </div>

          <div className="form-group">
            <button className="btn btn-primary btn-block" disabled={loading}>
              {loading && (
                <span className="spinner-border spinner-border-sm"></span>
              )}
              <span>Login</span>
            </button>
          </div>

          {message && (
            <div className="form-group">
              <div className="alert alert-danger" role="alert">
                {message}
              </div>
            </div>
          )}
          <CheckButton style={{ display: "none" }} ref={checkBtn} />
        </Form>
      </div>
    </div>
  );
};

export default Login;