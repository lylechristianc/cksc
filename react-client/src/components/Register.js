import React, { useState, useRef } from "react";
import Form from "react-validation/build/form";
import CheckButton from "react-validation/build/button";
import { isLoginName } from "validator";
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

const validLoginName = (value) => {
  if (!isLoginName(value)) {
    return (
      <div className="alert alert-danger" role="alert">
        This is not a valid loginName.
      </div>
    );
  }
};

const vusername = (value) => {
  if (value.length < 3 || value.length > 20) {
    return (
      <div className="alert alert-danger" role="alert">
        The username must be between 3 and 20 characters.
      </div>
    );
  }
};

const vpassword = (value) => {
  if (value.length < 6 || value.length > 40) {
    return (
      <div className="alert alert-danger" role="alert">
        The password must be between 6 and 40 characters.
      </div>
    );
  }
};

const Register = () => {
  const form = useRef();
  const checkBtn = useRef();

  const [username, setUsername] = useState("");
  const [loginName, setLoginName] = useState("");
  const [password, setPassword] = useState("");
  const [successful, setSuccessful] = useState(false);
  const [message, setMessage] = useState("");

  const [ error, setError ] = useState({})
  const [ valErrors, setValErrors ] = useState([])

  const onChangeUsername = (e) => {
    const username = e.target.value;
    setUsername(username);
  };

  const onChangeLoginName = (e) => {
    const loginName = e.target.value;
    setLoginName(loginName);
  };

  const onChangePassword = (e) => {
    const password = e.target.value;
    setPassword(password);
  };

  const handleRegister = (e) => {
    e.preventDefault();

    setMessage("");
    setSuccessful(false);

    form.current.validateAll();

    if (checkBtn.current.context._errors.length === 0) {
      AuthService.register(username, loginName, password).then(
        (response) => {
          setMessage(response.data.message);
          setSuccessful(true);
        },
        (error) => {
          setError(error)
          let errors = error?.response?.data["validation-errors"]
          setValErrors(errors ?? [])
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.errors) ||
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          setMessage(resMessage);
          setSuccessful(false);
        }
      );
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

        <Form onSubmit={handleRegister} ref={form}>
          {!successful && (
            <div>
              <div className="form-group">
                <label htmlFor="username">User Name <span className="text-danger">{ valErrors.username }</span></label>
                <input
                  type="text"
                  className = { "form-control" + (valErrors.username ? " is-invalid" : "") }
                  name="username"
                  value={username}
                  onChange={onChangeUsername}
                  validations={[required, vusername]}
                />
              </div>

              <div className="form-group">
                <label htmlFor="loginName">Login Name <span className="text-danger">{ valErrors.loginName }</span></label>
                <input
                  type="text"
                  className = { "form-control" + (valErrors.loginName ? " is-invalid" : "") }
                  name="loginName"
                  value={loginName}
                  onChange={onChangeLoginName}
                  validations={[required, validLoginName]}
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
                  validations={[required, vpassword]}
                />
              </div>

              <div className="form-group">
                <button className="btn btn-primary btn-block">Sign Up</button>
              </div>
            </div>
          )}

          {message && (
            <div className="form-group">
              <div
                className={ successful ? "alert alert-success" : "alert alert-danger" }
                role="alert"
              >
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

export default Register;