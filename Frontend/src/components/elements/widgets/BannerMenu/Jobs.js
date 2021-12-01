import React from 'react';
import { Link } from 'react-router-dom';
import "bootstrap-icons/font/bootstrap-icons.css";

export default function Jobs() {

  return (
    <li className="nav-item">
      <a className="nav-link" data-bs-toggle="collapse" href="#jobs-control" aria-expanded="false" aria-controls="jobs-control">
        <i className="icon-paper menu-icon"></i>
        <span className="menu-title">공고관리</span>
        <i className="menu-arrow"></i>
      </a>
      <div className="collapse" id="jobs-control">
        <ul className="nav flex-column sub-menu">
          <li className="nav-item"><Link className="nav-link" to="/hr/jobs">공고 목록 조회</Link></li>
          <li className="nav-item"><Link className="nav-link" to="/jobs/create">공고 생성</Link></li>
        </ul>
      </div>
    </li>
  );
}