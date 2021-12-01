import React from 'react';
import { Link } from 'react-router-dom';
import NavProfile from '../elements/ui/NavProfile';
import "bootstrap-icons/font/bootstrap-icons.css";
import Img2 from '../../assets/images/logo-mini.svg';
import mask from '../../assets/images/mask.PNG';
import NotificationMenu from '../elements/ui/NotificationMenu';

export default function Header() {
  return (
    <nav className="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
      <div className="text-center navbar-brand-wrapper d-flex align-items-center justify-content-center">
        <Link className="navbar-brand brand-logo mr-5" to="/">Apick ME</Link>
        <Link className="navbar-brand brand-logo-mini" to="/"><img src={Img2} alt="logo" /></Link>
      </div>
      <div className="navbar-menu-wrapper d-flex align-items-center justify-content-end">
        <ul className="navbar-nav mr-lg-2">
          <li className="nav-item nav-search d-none d-lg-block">
            <div className="input-group">
              <div className="input-group-prepend hover-cursor" id="navbar-search-icon">
                {/* <span className="input-group-text" id="search">
                  <i className="icon-search"></i>
                </span> */}
              </div>
              {/* <input type="text" className="form-control" id="navbar-search-input" placeholder="Search now" aria-label="search" aria-describedby="search" /> */}
            </div>
          </li>
        </ul>
        <ul className="navbar-nav navbar-nav-right">
          {/* <NotificationMenu /> */}
          <NavProfile />
          {/* <SideMenu /> */}
        </ul>
      </div>
    </nav>
  );
}