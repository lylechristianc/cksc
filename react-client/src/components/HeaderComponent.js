import React from 'react'
import { Link } from 'react-router-dom';

const HeaderComponent = (props) => {
    return (
        <div>
            <header>
                <nav className = "navbar navbar-expand-md navbar-dark bg-dark">
                    <div>
                        <a href = "https://jominguides.net" className = "navbar-brand ms-3">
                            Blog Application
                        </a>
                    </div>
        {props.user ? (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/profile"} className="nav-link">
                {props.user.username}
              </Link>
            </li>
            <li className="nav-item">
              <a href="/login" className="nav-link" onClick={ props.logOut }>
                Log Out
              </a>
            </li>
          </div>
        ) : (
          <div className="navbar-nav ml-auto">
            <li className="nav-item">
              <Link to={"/login"} className="nav-link">
                Login
              </Link>
            </li>

            <li className="nav-item">
              <Link to={"/register"} className="nav-link">
                Sign Up
              </Link>
            </li>
          </div>
        )}
                </nav>
            </header>
            <link
              rel="stylesheet"
              href="https://fonts.googleapis.com/icon?family=Material+Icons|Material+Icons+Two+Tone"
            />
        </div>
    )
}

export default HeaderComponent