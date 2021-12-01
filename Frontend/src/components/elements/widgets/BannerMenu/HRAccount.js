import React from 'react';
import { Link } from 'react-router-dom';
import "bootstrap-icons/font/bootstrap-icons.css";

export default function HRAccount() {

  return (
    <li className="nav-item">
      <a className="nav-link" data-bs-toggle="collapse" href="#account-control" aria-expanded="false" aria-controls="account-control">
        <i className="icon-layout menu-icon"></i>
        <span className="menu-title">계정관리</span>
        <i className="menu-arrow"></i>
      </a>
      <div className="collapse" id="account-control">
        <ul className="nav flex-column sub-menu">
          <li className="nav-item"><Link className="nav-link" to="/hr/manage">인사 담당자 관리</Link></li>
          <li className="nav-item"><Link className="nav-link" to="/hr/create">인사 담당자 추가</Link></li>
        </ul>
      </div>
    </li>
  );
}